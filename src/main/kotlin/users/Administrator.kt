package users

class Administrator {

    private val repository = UserRepository.getInstance("qwerty")

    fun work() {
        while (true) {
            print("Enter an operation: 0 - Exit, 1 - Add, 2 - Delete: ")
            val operationInput = readlnOrNull()
            if (operationInput == null) {
                println("Ввод больше недоступен, сохранение изменений и выход...")
                repository.saveChanges()
                break
            }
            val operationIndex = operationInput.toInt()
            val operation = Operation.entries[operationIndex]
            when (operation) {
                Operation.EXIT -> {
                    repository.saveChanges()
                    break
                }

                Operation.ADD -> add()
                Operation.DELETE -> delete()
            }
        }
    }

    private fun add() {
        print("Enter name: ")
        val name = readln()

        print("Enter lastName: ")
        val lastName = readln()

        print("Enter name: ")
        val age = readln().toInt()

        repository.add(
            firstName = name,
            lastName = lastName,
            age = age
        )
    }

    private fun delete() {
        print("Enter an id to delete: ")
        val userIndex = readln().toInt()
        repository.delete(userIndex)
    }
}