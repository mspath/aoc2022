package day25

import java.io.File
import kotlin.math.pow

fun main() {
    val input = File("data/day25/input.txt").readLines()
    breakfast(input)
}

fun toSnafu(s: String): Long {
    val r = s.reversed()
    var result = 0L
    repeat(r.length) {e ->
        val base = 5.0.pow(e).toLong()
        when(r[e]) {
            '=' -> result += base * -2
            '-' -> result += base * -1
            '1' -> result += base
            '2' -> result += base * 2
            else -> {}
        }
    }
    return result
}

fun asSnafu(n: Long): String {
    var rest = n
    val snafu: MutableList<Char> = mutableListOf()
    while (rest > 0) {
        val (next, digit) =
            when (rest.mod(5)) {
                0 -> rest / 5 to '0'
                1 -> rest / 5 to '1'
                2 -> rest / 5 to '2'
                3 -> (rest + 5) / 5 to '='
                4 -> (rest + 5) / 5 to '-'
                else -> error("snafu error")
            }
        rest = next
        snafu.add(0, digit)
    }
    return snafu.joinToString("")
}

fun breakfast(input: List<String>): String {
    val sum = input.sumOf { toSnafu(it) }
    val result = asSnafu(sum)
    println("breakfast: $result")
    return result
}