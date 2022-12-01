package day1

import java.io.File

fun main() {
    val input = File("data/day1/input.txt").readText()
    breakfast(input)
    lunch(input)
}

fun breakfast(input: String) {
    val elves = input.split("\n\n")
    val max = elves.maxOf { calories ->
        calories.split("\n").sumOf { it.toInt() }
    }
    println("most Calories: $max")
}

fun lunch(input: String) {
    val elves = input.split("\n\n")
    val max3 = elves.map { calories ->
        calories.split("\n").sumOf { it.toInt() }
    }.sorted().takeLast(3).sum()
    println("Calories of top 3 elves: $max3")
}