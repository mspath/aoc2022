package day2

// breakfast implemented via a lookup map
fun snack(input: List<String>): Int {

    val breakfastTable: Map<String, Int> =
        mapOf(
            "A X" to 1 + 3,
            "A Y" to 2 + 6,
            "A Z" to 3 + 0,
            "B X" to 1 + 0,
            "B Y" to 2 + 3,
            "B Z" to 3 + 6,
            "C X" to 1 + 6,
            "C Y" to 2 + 0,
            "C Z" to 3 + 3,
        )

    return input.sumOf { breakfastTable[it] ?: 0 }
}

// lunch implemented via a lookup map
fun dinner(input: List<String>): Int {

    val lunchTable: Map<String, Int> =
        mapOf(
            "A X" to 3 + 0,
            "A Y" to 1 + 3,
            "A Z" to 2 + 6,
            "B X" to 1 + 0,
            "B Y" to 2 + 3,
            "B Z" to 3 + 6,
            "C X" to 2 + 0,
            "C Y" to 3 + 3,
            "C Z" to 1 + 6,
        )

    return input.sumOf { lunchTable[it] ?: 0 }
}
