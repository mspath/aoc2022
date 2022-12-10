package day3

import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

fun main() {
    val input = File("data/day3/input.txt").readLines()
    breakfast(input)
    lunch(input)
    testRucksackReorganization()
    benchmarkRucksackReorganization()
}

// calculates the priority value
fun getPriority(c: Char) : Int {
    return if (c in 'a'..'z') c.code - 'a'.code + 1
    else c.code - 'A'.code + 27
}

fun breakfast(input: List<String>) : Int {
    val rucksacks = input.map {
        val items = it.toCharArray().toList()
        val size = items.size
        Pair(items.subList(0, size / 2), items.subList(size / 2, size))
    }
    val errors = rucksacks.map {
        it.first.intersect(it.second).first()
    }
    val result = errors.sumOf { getPriority(it) }
    println("breakfast: $result")
    return result
}

fun lunch(input: List<String>) : Int {
    val rucksacks = input.map {
        it.toCharArray().toList()
    }
    val groups = rucksacks.chunked(3)
    val badges = groups.map {rucksack ->
        rucksack[0].intersect(rucksack[1]).intersect(rucksack[2]).first()
    }
    val result = badges.sumOf { getPriority(it) }
    println("lunch: $result")
    return result
}

fun testRucksackReorganization() {
    val input = File("data/day3/input.txt").readLines()
    check(breakfast(input) == 7863) { "breakfast failed" }
    check(lunch(input) == 2488) { "lunch failed" }
    println("2 tests ok")
}

@OptIn(ExperimentalTime::class)
fun benchmarkRucksackReorganization() {
    println("running tests for day 3")
    val input = File("data/day3/input.txt").readLines()
    val (_, durationBreakfast) = measureTimedValue { breakfast(input) }
    println("breakfast duration: $durationBreakfast")
    val (_, durationLunch) = measureTimedValue { lunch(input) }
    println("lunch duration: $durationLunch")
    println("---")
}