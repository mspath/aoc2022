package day9

import java.io.File

fun main() {
    val input = File("data/day9/input.txt").readLines()
    breakfast(input)
    lunch(input)
    // testRopeBridge()
}

data class Instruction(val direction: Char, val steps: Int)
data class Position(var x: Int, var y: Int)
data class Point(val x: Int, val y: Int)

fun distance(x: Int, y: Int) = kotlin.math.abs(y - x)

// this checks whether we have to move at all
fun Position.touching(other: Position) = distance(x, other.x) < 2 && distance(y, other.y) < 2

fun Position.follow(other: Position) {
    if (this.touching(other)) return
    if (other.x == x) {
        if (other.y < y) y -= 1
        if (other.y > y) y += 1
    }
    else if (other.y == y) {
        if (other.x < x) x -= 1
        if (other.x > x) x += 1
    }
    // it's diagonally distant, so we know that we have to get closer in both axis
    else {
        if (other.x > x) x += 1
        else x -= 1

        if (other.y > y) y += 1
        else y -= 1
    }
}

fun updateCaterpillar(caterpillar: List<Position>) {
    val segments = caterpillar.windowed(2)
    segments.forEach {
        it.last().follow(it.first())
    }
}

// debug
fun printVisited(visited: HashSet<Point>) {
    val minX = visited.minOf { it.x }
    val maxX = visited.maxOf { it.x }
    val minY = visited.minOf { it.y }
    val maxY = visited.maxOf { it.y }

    println("--------")
    (maxY downTo minY).forEach { y ->
        (minX..maxX).forEach { x ->
            if (visited.contains(Point(x, y))) print('x')
            else print('.')
        }
        println()
    }
}

fun HashSet<Point>.process(instruction: Instruction, head: Position, tail: Position) {
    repeat(instruction.steps) {
        when (instruction.direction) {
            'R' -> head.x += 1
            'L' -> head.x -= 1
            'U' -> head.y += 1
            'D' -> head.y -= 1
        }
        tail.follow(head)
        this.add(Point(tail.x, tail.y))
    }
}

fun HashSet<Point>.processCaterpillar(instruction: Instruction, caterpillar: List<Position>) {
    val head = caterpillar.first()
    repeat(instruction.steps) {
        when (instruction.direction) {
            'R' -> head.x += 1
            'L' -> head.x -= 1
            'U' -> head.y += 1
            'D' -> head.y -= 1
        }
        updateCaterpillar(caterpillar)
        val tail = caterpillar.last()
        this.add(Point(tail.x, tail.y))
    }
}

// debug
fun HashSet<Point>.processHead(instruction: Instruction, head: Position) {
    repeat(instruction.steps) {
        when (instruction.direction) {
            'R' -> head.x += 1
            'L' -> head.x -= 1
            'U' -> head.y += 1
            'D' -> head.y -= 1
        }
        this.add(Point(head.x, head.y))
    }
}

fun breakfast(input: List<String>): Int {
    val instructions = input.map { i ->
        Instruction(i.substringBefore(" ").first(), i.substringAfter(" ").toInt())
    }
    val visited: HashSet<Point> = hashSetOf()
    visited.add(Point(0, 0))
    val head = Position(0, 0)
    val tail = Position(0, 0)

    instructions.forEach {instruction ->
        visited.process(instruction, head, tail)
    }

    val result = visited.size
    println("breakfast: $result")
    return result
}

fun lunch(input: List<String>): Int {
    val instructions = input.map { i ->
        Instruction(i.substringBefore(" ").first(), i.substringAfter(" ").toInt())
    }
    val visited: HashSet<Point> = hashSetOf()
    visited.add(Point(0, 0))

    val caterpillar = listOf(
        Position(0, 0),
        Position(0,0),
        Position(0,0),
        Position(0,0),
        Position(0,0),
        Position(0,0),
        Position(0,0),
        Position(0,0),
        Position(0,0),
        Position(0,0))

    instructions.forEach {instruction ->
        visited.processCaterpillar(instruction, caterpillar)
    }

    val result = visited.size
    println("lunch: $result")
    return result
}

fun testRopeBridge() {
    println("running tests for day 9")
    val input = File("data/day9/input.txt").readLines()
    check(breakfast(input) == 6098) { "breakfast failed" }
    check(lunch(input) == 2597) { "lunch failed" }
    println("2 tests ok")
    println("---")
}