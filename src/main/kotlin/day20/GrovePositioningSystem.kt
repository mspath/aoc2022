package day20

import java.io.File

fun main() {
    val input = File("data/day20/input.txt").readLines()
    breakfast(input)
    lunch(input)
}

data class CircleValue(val index: Int, val value: Long)

fun breakfast(input: List<String>): Long {
    val circle = input.mapIndexed { index, s ->
        CircleValue(index, s.toLong())
    }.toMutableList()
    val size = circle.size
    val stepSize = size - 1

    circle.indices.forEach { index ->
        val currentIndex = circle.indexOfFirst { it.index == index }
        val element = circle.removeAt(currentIndex)
        circle.add((currentIndex + element.value).mod(stepSize), element)
    }

    val indexZero = circle.indexOfFirst { it.value == 0L }
    val result = circle[(indexZero + 1000) % size].value +
            circle[(indexZero + 2000) % size].value +
            circle[(indexZero + 3000) % size].value
    println("breakfast: $result")
    return result
}

fun lunch(input: List<String>): Long {
    val circle = input.mapIndexed { index, s ->
        CircleValue(index, s.toLong() * 811589153)
    }.toMutableList()
    val size = circle.size
    val stepSize = size - 1

    repeat(10) {
        circle.indices.forEach { index ->
            val currentIndex = circle.indexOfFirst { it.index == index }
            val element = circle.removeAt(currentIndex)
            circle.add((currentIndex + element.value).mod(stepSize), element)
        }
    }

    val indexZero = circle.indexOfFirst { it.value == 0L }
    val result = circle[(indexZero + 1000) % size].value +
            circle[(indexZero + 2000) % size].value +
            circle[(indexZero + 3000) % size].value
    println("lunch: $result")
    return result
}