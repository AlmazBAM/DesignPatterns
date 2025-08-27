package users

import kotlinx.serialization.json.Json
import observer.Observable
import observer.Observer
import java.io.File

class UserRepository private constructor() : Observable<List<User>> {

    private val file = File("profiles.json")

    private val _users = loadUsers()


    private val _observers = mutableListOf<Observer<List<User>>>()
    override val observers
        get() = _observers.toList()

    override val currentValue: List<User>
        get() = _users.toList()

    override fun addObserver(observer: Observer<List<User>>) {
        _observers.add(observer)
        observer.onChanged(currentValue)
    }

    override fun removeObserver(observer: Observer<List<User>>) {
        _observers.remove(observer)
    }

    private fun loadUsers(): MutableList<User> = Json.decodeFromString(file.readText().trim())

    fun addOnUsersChangedListeners(observer: Observer<List<User>>) {
        addObserver(observer)
    }

    fun add(
        firstName: String,
        lastName: String,
        age: Int
    ) {
        val id = currentValue.maxOf { it.id } + 1
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