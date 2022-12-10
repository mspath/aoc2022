package day4

import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

fun main() {
    val input = File("data/day4/input.txt").readLines()
    breakfast(input)
    lunch(input)
    // testCampCleanup()
    // benchmarkCampCleanup()
}

data class Group(val first: IntRange, val second: IntRange) {
    fun contain() = first.toSet().containsAll(second.toSet()) || second.toSet().containsAll(first.toSet())
    fun overlap() = first.toSet().intersect(second.toSet()).isNotEmpty()
}

fun breakfast(input: List<String>) : Int {
    val assignments = input.map {
        val (x, y) = it.split(",")
        val first = x.substringBefore('-').toInt()..x.substringAfter('-').toInt()
        val second = y.substringBefore('-').toInt()..y.substringAfter('-').toInt()
        Group(first, second)
    }
    val result = assignments.count { it.contain() }
    println("breakfast: $result")
    return result
}

fun lunch(input: List<String>) : Int {
    val assignments = input.map {
        val (x, y) = it.split(",")
        val first = x.substringBefore('-').toInt()..x.substringAfter('-').toInt()
        val second = y.substringBefore('-').toInt()..y.substringAfter('-').toInt()
        Group(first, second)
    }
    val result = assignments.count { it.overlap() }
    println("lunch: $result")
    return result
}

fun testCampCleanup() {
    val input = File("data/day4/input.txt").readLines()
    check(breakfast(input) == 584) { "breakfast failed" }
    check(lunch(input) == 933) { "lunch failed" }
    println("2 tests ok")
}

@OptIn(ExperimentalTime::class)
fun benchmarkCampCleanup() {
    println("running tests for day 4")
    val input = File("data/day4/input.txt").readLines()
    val (breakfast, durationBreakfast) = measureTimedValue { breakfast(input) }
    println("breakfast: $breakfast | duration: $durationBreakfast")
    val (lunch, durationLunch) = measureTimedValue { lunch(input) }
    println("lunch: $lunch | duration: $durationLunch")
    println("---")
}