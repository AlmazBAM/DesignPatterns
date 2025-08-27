package users

import kotlinx.serialization.json.Json
import observer.MutableObservable
import java.io.File

class UserRepository private constructor() {

    private val file = File("profiles.json")

    private val _users = loadUsers()
    val users = MutableObservable(_users.toList())
    val oldestUser = MutableObservable(_users.maxBy { it.age })


    private fun loadUsers(): MutableList<User> = Json.decodeFromString(file.readText().trim())

    fun add(
        firstName: String,
        lastName: String,
        age: Int
    ) {
        val id = _users.maxOf { it.id } + 1
        _users.add(
            User(
                id = id,
                firstName = firstName,
                lastName = lastName,
                age = age
            )
        )
        users.currentValue = _users.toList()
        if (age > oldestUser.currentValue.age)
            oldestUser.currentValue = _users.maxBy { it.age }
    }

    fun delete(index: Int) {
        _users.removeIf { it.id == index }
        users.currentValue = _users.toList()
        val newOldest = _users.maxBy { it.age }
        if (newOldest != oldestUser.currentValue)
            oldestUser.currentValue = newOldest
    }

    fun saveChanges() {
        val content = Json.encodeToString(_users)
        file.writeText(content)
    }


    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null
        private val LOCK = Any()
        fun getInstance(password: String): UserRepository {
            val correctPassword = File("password.txt").readText().trim()
            if (correctPassword != password) throw IllegalArgumentException("Wrong password")
            INSTANCE?.let { return it }
            synchronized(LOCK) {
                INSTANCE?.let { return it }
                return UserRepository().also { INSTANCE = it }
            }
        }
    }
}