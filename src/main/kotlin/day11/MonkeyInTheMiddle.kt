package day11

import java.io.File

fun main() {
    val input = File("data/day11/input.txt").readText()
    // breakfast(input)
    lunch(input)
}

data class Monkey(val id: Int,
                  val items: MutableList<Int>,
                  val operation: (Int) -> Int,
                  val test: (Int) -> Int,
                  var inspections: Int = 0)

private fun parseDivisor(input: String): Int {
    val lines = input.split("\n")
        .filter { it.contains("divisible by ") }
        .map { it.substringAfter("divisible by ").toInt() }
    return lines.reduce { product, i -> product * i }
}

fun parseMonkeys(input: String): List<Monkey> {
    val divisor = parseDivisor(input)
    val monkeys = input.split("\n\n").map { m ->
        val lines = m.split("\n")
        val id = lines[0].substringAfter("Monkey ").substringBefore(":").toInt()
        val items = lines[1].substringAfter("Starting items: ").split(", ")
            .map { it.toInt() }
            .toMutableList()
        val l2 = lines[2].substringAfter("old ").split(" ")
        val value = l2.last()
        val operand = if (value == "old") "x" else l2.first()
        val divisible = lines[3].substringAfter("by ").toInt()
        val monkeyTrue = lines[4].substringAfter("monkey ").toInt()
        val monkeyFalse = lines[5].substringAfter("monkey ").toInt()

        val operation: (Int) -> Int = when (operand) {
            "*" -> { i -> i * value.toInt() }
            "+" -> { i -> i + value.toInt() }
            // we need to mod with divisor to avoid overflows
            "x" -> { i -> ((i.toLong() * i) % divisor).toInt() }
            else -> error("unknown operation")
        }

        val test: (Int) -> Int = { item ->
            if (item % divisible == 0) monkeyTrue else monkeyFalse
        }

        Monkey(id, items, operation, test)
    }
    return monkeys
}

fun breakfast(input: String): Int {

    val monkeys = parseMonkeys(input)

    repeat(20) {
        monkeys.forEach { monkey ->
            while (monkey.items.isNotEmpty()) {
                monkey.inspections++
                val item = monkey.items.removeAt(0)
                val newValue = monkey.operation(item) / 3
                val nextMonkey = monkey.test(newValue)
                monkeys[nextMonkey].items.add(newValue)
            }
        }
    }

    val inspections = monkeys.map { it.inspections }.sortedDescending().take(2)
    val result = inspections.first() * inspections.last()
    println("breakfast: $result")
    return result
}

fun lunch(input: String): Long {

    val monkeys = parseMonkeys(input)
    val divisor = parseDivisor(input)

    repeat(10000) {
        monkeys.forEach { monkey ->
            while (monkey.items.isNotEmpty()) {
                monkey.inspections++
                val item = monkey.items.removeAt(0)
                val newValue = monkey.operation(item)
                val nextMonkey = monkey.test(newValue)
                monkeys[nextMonkey].items.add(newValue % divisor)
            }
        }
    }

    val inspections = monkeys.map { it.inspections }.sortedDescending().take(2)
    val result = inspections.first().toLong() * inspections.last()
    println("lunch: $result")
    return result
}

fun testMonkeyInTheMiddle() {
    println("running tests for day 11")
    val input = File("data/day11/input.txt").readText()
    check(breakfast(input) == 67830) { "breakfast failed" }
    check(lunch(input) == 15305381442) { "lunch failed" }
    println("2 tests ok")
    println("---")
}