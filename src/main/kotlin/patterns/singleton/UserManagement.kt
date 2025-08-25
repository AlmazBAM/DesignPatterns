package patterns.singleton

fun main() {
    UserRepository.getInstance("qwerty").users.forEach(::println)
}