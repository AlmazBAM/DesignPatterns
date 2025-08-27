package builder

fun main() {

    val drink = Drink.Builder()
        .type("Tea")
        .build()
    println(drink)
}