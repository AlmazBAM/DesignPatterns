package patterns.singleton

import kotlinx.serialization.json.Json
import java.io.File

class UserRepository private constructor() {

    private val file = File("profiles.json")

    private val _users = loadUsers().toMutableList()
    val users
        get() = _users.toList()

    private fun loadUsers() = Json.decodeFromString<List<User>>(file.readText().trim())

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