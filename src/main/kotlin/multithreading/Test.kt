package multithreading

import kotlin.concurrent.thread

fun main() {
    thread {
        repeat(100_000) {
            print(" 0 ")
        }
    }

    Thread {
        repeat(100_000) {
            print(" $ ")
        }
    }.start()

    thread {
        repeat(100_000) {
            print(" * ")
        }
    }
}