package day1

import java.io.File
import kotlin.time.measureTimedValue

fun main() {
    val input = File("data/day1/input.txt").readText()
    breakfast(input)
    lunch(input)
    testCalorieCounting()
    benchmarkCalorieCounting()
}

fun breakfast(input: String) : Int {
    val elves = input.split("\n\n")
    val max = elves.maxOf { calories ->
        calories.split("\n").sumOf { it.toInt() }
    }
    println("most Calories: $max")
    return max
}

fun lunch(input: String) : Int {
    val elves = input.split("\n\n")
    val max3 = elves.map { calories ->
        calories.split("\n").sumOf { it.toInt() }
    }.sorted().takeLast(3).sum()
    println("Calories of top 3 elves: $max3")
    return max3
}

fun testCalorieCounting() {
    val input = File("data/day1/input.txt").readText()
    check(breakfast(input) == 69836) { "breakfast failed" }
    check(lunch(input) == 207968) { "lunch failed" }
    println("2 tests ok")
}

fun benchmarkCalorieCounting() {
    println("running tests for day 1")
    val input = File("data/day1/input.txt").readText()
    val (_, durationBreakfast) = measureTimedValue { breakfast(input) }
    println("breakfast duration: $durationBreakfast")
    val (_, durationLunch) = measureTimedValue { lunch(input) }
    println("lunch duration: $durationLunch")
    println("---")
}