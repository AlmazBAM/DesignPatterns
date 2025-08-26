package users

import kotlinx.serialization.json.Json
import observer.Observer
import java.io.File

class UserRepository private constructor() {

    private val file = File("profiles.json")

    private val _users = loadUsers()
    val users
        get() = _users.toList()

    private val observers = mutableListOf<Observer<List<User>>>()

    private fun loadUsers(): MutableList<User> = Json.decodeFromString(file.readText().trim())

    private fun notifyObservers() {
        observers.forEach { it.onChanged(users) }
    }

    fun addOnUsersChangedListeners(observer: Observer<List<User>>) {
        observers.add(observer)
        observer.onChanged(users)
    }

    fun add(
        firstName: String,
        lastName: String,
        age: Int
    ) {
        val id = users.maxOf { it.id } + 1
        _users.add(
            User(
                id = id,
                firstName = firstName,
                lastName = lastName,
                age = age
            )
        )
        notifyObservers()
    }

    fun delete(index: Int) {
        _users.removeIf { it.id == index }
        notifyObservers()
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