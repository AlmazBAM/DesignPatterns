package multithreading

import kotlin.concurrent.thread
import kotlin.random.Random

fun main() {
    print("Enter number from 0 to 1_000_000_000: ")
    val value = readln().toInt()
    var isWin = false
//    thread {
//        var time = 1
//        while (!isWin) {
//            println(time++)
//            Thread.sleep(1000)
//        }
//    }
//
//    thread {
//        while (true) {
//            val number = Random.nextInt(0, 1_000_000_001)
//            if (value == number) {
//                isWin = true
//                println("I win")
//                break
//            }
//        }
//    }

}