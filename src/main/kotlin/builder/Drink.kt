package builder

class Drink private constructor(
    val type: String,
    val additives: List<String>,
    val diningOption: String,
    val temperature: String
) {
    class Builder {
        private var type: String = "Coffee"
        private var additives: List<String> = listOf()
        private var diningOption: String = "To go"
        private var temperature: String = "Hot"

        fun type(type: String) = apply { this.type = type }

        fun additives(additives: List<String>) = apply { this.additives = additives }

        fun diningOption(diningOption: String) = apply { this.diningOption = diningOption }

        fun temperature(temperature: String) = apply { this.temperature = temperature }

        fun build(): Drink {
            return Drink(type, additives, diningOption, temperature)
        }
    }
}
