package users

import command.Command
import command.Invoker
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

object UsersInvoker: Invoker {

    private val commands = LinkedBlockingQueue<Command>()

    init {
        thread {
            while (true) {
                println("Waiting")
                val command = commands.take()
                println("Executing")
                command.execute()
                println("Executed")
            }
        }
    }

    override fun addCommand(command: Command) {
        println("New command: $command")
        commands.add(command)
    }
}