package day6

import java.io.File

fun main() {
    val input = File("data/day6/input.txt").readText()
    breakfast(input)
    lunch(input)
    testTuningTrouble()
}

fun markerPosition(message: String, length: Int) : Int {
    val tokens = message.windowed(length)
    tokens.forEachIndexed { index, s ->
        if (s.toSet().size == length) return index + length
    }
    error("not found")
}

fun breakfast(input: String): Int {
    val result = markerPosition(input, 4)
    println("breakfast: $result")
    return result
}

fun lunch(input: String): Int {
    val result = markerPosition(input, 14)
    println("lunch: $result")
    return result
}

fun testTuningTrouble() {
    val input = File("data/day6/input.txt").readText()
    check(breakfast(input) == 1723) { "breakfast failed" }
    check(lunch(input) == 3708) { "lunch failed" }
    println("2 tests ok")
}