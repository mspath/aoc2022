package day12

import java.io.File
import java.util.Deque

typealias Lookup = Map<Pair<Int, Int>, Point>

data class Point(val x: Int, val y: Int, val char: Char, var steps: Int = -1, var visited: Boolean = false) {

    val height: Int = if (char == 'S') 'a'.code else if (char == 'E') 'z'.code else char.code

    fun getNeighbors(lookup: Lookup): List<Point> {
        val points = listOf(Pair(x - 1, y), Pair(x + 1, y), Pair(x, y - 1), Pair(x, y + 1))
        return points.mapNotNull { lookup[it] }
    }

    fun getStepables(lookup: Lookup): List<Point> {
        return getNeighbors(lookup)
            .filter { !it.visited }
            .filter { this.height + 1 >= it.height}
    }
}

fun main() {
    val input = File("data/day12/input.txt").readLines()
    breakfast(input)
    lunch(input)
    brunch(input)
}

fun breakfast(input: List<String>) : Int {
    val grid = input.flatMapIndexed { y: Int, s: String ->
        s.mapIndexed { x, c ->
            Point(x, y, c)
        }
    }
    val lookup = grid.associateBy { Pair(it.x, it.y) }
    val start = grid.first { it.char == 'S' }
    start.steps = 0
    start.visited = true
    val end = grid.first { it.char == 'E' }
    val que: Deque<Point> = java.util.ArrayDeque()
    que.add(start)
    var result = Int.MAX_VALUE
    while (que.isNotEmpty()) {
        val next = que.pop()
        if (next == end) {
            result = next.steps
            break
        }
        val stepables = next.getStepables(lookup)
        stepables.forEach {
            it.steps = next.steps + 1
            it.visited = true
            que.addLast(it)
        }
    }
    println("result breakfast: $result")
    return result
}

fun lunch(input: List<String>) : Int {
    val grid = input.flatMapIndexed { y: Int, s: String ->
        s.mapIndexed { x, c ->
            Point(x, y, c)
        }
    }
    val lookup = grid.associateBy { Pair(it.x, it.y) }
    val end = grid.first { it.char == 'E' }
    var result = Int.MAX_VALUE
    val starts = grid.filter { it.height == 'a'.code }

    starts.forEach { start ->

        // resetting the grid
        grid.forEach {
            it.visited = false
            it.steps = -1
        }

        start.steps = 0
        start.visited = true

        val que: Deque<Point> = java.util.ArrayDeque()
        que.add(start)

        while (que.isNotEmpty()) {
            val next = que.pop()
            if (next == end) {
                if (next.steps < result) result = next.steps
                println("result for start $start: $result")
                break
            }
            val stepables = next.getStepables(lookup)
            stepables.forEach {
                it.steps = next.steps + 1
                it.visited = true
                que.addLast(it)
            }
        }
    }
    println("result lunch: $result")
    return result
}

// refactoring of lunch, but adding all possible starts to the deque right away
fun brunch(input: List<String>) : Int {
    val grid = input.flatMapIndexed { y: Int, s: String ->
        s.mapIndexed { x, c ->
            Point(x, y, c)
        }
    }
    val lookup = grid.associateBy { Pair(it.x, it.y) }
    val end = grid.first { it.char == 'E' }
    var result = Int.MAX_VALUE
    val que: Deque<Point> = java.util.ArrayDeque()
    val starts = grid.filter { it.height == 'a'.code }
    starts.forEach {
        it.visited = true
        it.steps = 0
        que.add(it)
    }
    while (que.isNotEmpty()) {
        val next = que.pop()
        if (next == end) {
            result = next.steps
            break
        }
        val stepables = next.getStepables(lookup)
        stepables.forEach {
            it.steps = next.steps + 1
            it.visited = true
            que.addLast(it)
        }
    }
    println("result brunch: $result")
    return result
}

fun testHillClimbingAlgorithm() {
    println("running tests for day 12")
    val input = File("data/day12/input.txt").readLines()
    check(breakfast(input) == 361) { "breakfast failed" }
    check(lunch(input) == 354) { "lunch failed" }
    check(brunch(input) == 354) { "brunch failed" }
    println("3 tests ok")
    println("---")
}