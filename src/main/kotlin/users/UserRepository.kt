package users

import kotlinx.serialization.json.Json
import observer.MutableObservable
import observer.Observable
import java.io.File

class UserRepository private constructor() {

    private val file = File("profiles.json")

    private val usersList = loadUsers()
    private val _users = MutableObservable(usersList.toList())
    val users: Observable<List<User>>
        get() = _users

    private val _oldestUser = MutableObservable(usersList.maxBy { it.age })
    val oldestUser: Observable<User>
        get() = _oldestUser


    private fun loadUsers(): MutableList<User> = Json.decodeFromString(file.readText().trim())

    fun add(
        firstName: String,
        lastName: String,
        age: Int
    ) {
        val id = usersList.maxOf { it.id } + 1
        usersList.add(
            User(
                id = id,
                firstName = firstName,
                lastName = lastName,
                age = age
            )
        )
        _users.currentValue = usersList.toList()
        if (age > oldestUser.currentValue.age)
            _oldestUser.currentValue = usersList.maxBy { it.age }
    }

    fun delete(index: Int) {
        usersList.removeIf { it.id == index }
        _users.currentValue = usersList.toList()
        val newOldest = usersList.maxBy { it.age }
        if (newOldest != oldestUser.currentValue)
            _oldestUser.currentValue = newOldest
    }

    fun saveChanges() {
        val content = Json.encodeToString(usersList)
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