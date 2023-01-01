package day21

import java.io.File

fun main() {
    val input = File("data/day21/input.txt").readLines()
    breakfast(input)
}

data class Monkey(val name: String, var number: Long?, var rule: String) {
    val left = if (rule.isNotEmpty()) rule.substring(0, 4) else "null"
    val right = if (rule.isNotEmpty()) rule.substring(7, 11) else "null"
    val operation = if (rule.isNotEmpty()) rule[5] else ' '
    val solved = number != null

    companion object {
        fun from(s: String): Monkey {
            val name = s.substringBefore(": ")
            return if (s.length == 17) {
                val rule = s.substringAfter(": ")
                Monkey(name, null, rule)
            } else {
                val number = s.substringAfter(": ").toLong()
                Monkey(name, number, "")
            }
        }
    }
}

fun breakfast(input: List<String>): Long {
    val monkeys = input.map {
        Monkey.from(it)
    }
    val lookup = monkeys.associateBy { it.name }
    val solved = monkeys.filter { it.number != null }.toMutableSet()
    val unsolved = monkeys.filterNot { solved.contains(it) }.toMutableList()
    val root = lookup.getValue("root")
    while (!root.solved && unsolved.size > 0) {
        val solvable = unsolved.filter { solved.contains(lookup.getValue(it.left))
                && solved.contains(lookup.getValue(it.right)) }
        solvable.forEach {
            val l = lookup.getValue(it.left).number!!
            val r = lookup.getValue(it.right).number!!
            when (it.operation) {
                '+' -> it.number = l + r
                '-' -> it.number = l - r
                '*' -> it.number = l * r
                '/' -> it.number = l / r
                else -> error("error in monkey $it")
            }
            unsolved.remove(it)
            solved.add(it)
        }
    }
    val result = root.number ?: error("something went wrong")
    println("breakfast: $result")
    return result
}

fun lunch(input: List<String>): Long {
    TODO()
}