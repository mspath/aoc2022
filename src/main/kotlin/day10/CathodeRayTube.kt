package day10

import java.io.File

fun main() {
    val input = File("data/day10/input.txt").readLines()
    // breakfast(input)
    lunch(input)
}

data class Instruction(val name: String, val time: Int, val value: Int = 0)

data class Pixel(val register: Int, val value: Int)

fun parsePixels(input: List<String>): List<Pixel> {
    val registers: MutableList<Pixel> = mutableListOf()
    var register = 1
    var clock = 1
    val instructions = input.map {
        val name = it.substringBefore(" ")
        if (name == "noop") Instruction(name, 1)
        else {
            val value = it.substringAfter(" ").toInt()
            Instruction(name, 2, value)
        }
    }
    instructions.forEach {
        repeat(it.time) { t ->
            registers.add(Pixel(clock, register))
            clock++
        }
        register += it.value
    }
    return registers.toList()
}

fun breakfast(input: List<String>): Int {

    val registers = parsePixels(input)
    val checkpoints = listOf(20, 60, 100, 140, 180, 220)

    val result = registers.filter {
        it.register in checkpoints
    }.sumOf { it.value * it.register }

    println("breakfast: $result")
    return result
}

fun lunch(input: List<String>): String {
    val registers = parsePixels(input)

    repeat(6) {row ->
        repeat(40) {
            val p = registers[it + row * 40]
            if (it in p.value - 1..p.value + 1) print('#')
            else print('.')
        }
        println()
    }
    // we now can read the message in the terminal

    // alternatively we could add it up to a string
    val result = buildString {
        repeat(6) {row ->
            repeat(40) {
                val p = registers[it + row * 40]
                if (it in p.value - 1..p.value + 1) append('#')
                else append('.')
            }
            append('\n')
        }
    }
    return result
}

fun testCathodeRayTube() {
    println("running tests for day 10")
    val input = File("data/day10/input.txt").readLines()
    check(breakfast(input) == 13180) { "breakfast failed" }
    check(lunch(input) == """####.####.####..##..#..#...##..##..###..
#.......#.#....#..#.#..#....#.#..#.#..#.
###....#..###..#....####....#.#..#.###..
#.....#...#....#....#..#....#.####.#..#.
#....#....#....#..#.#..#.#..#.#..#.#..#.
####.####.#.....##..#..#..##..#..#.###..
""") { "lunch failed" }
    println("2 tests ok")
    println("---")
}