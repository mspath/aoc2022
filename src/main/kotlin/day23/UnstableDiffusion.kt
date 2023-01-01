package day23

import java.io.File

fun main() {
    val input = File("data/day23/input.txt").readLines()
    breakfast(input)
    // this takes a while, but it will complete in less than 1000 cycles
    // lunch(input)
}

enum class Direction {
    NORTH,
    SOUTH,
    WEST,
    EAST
}

fun getDirections(cycle: Int): List<Direction> {
    return listOf(Direction.values()[(cycle).mod(4)],
        Direction.values()[(cycle + 1).mod(4)],
        Direction.values()[(cycle + 2).mod(4)],
        Direction.values()[(cycle + 3).mod(4)])
}

data class Point(val x: Int, val y: Int)

data class Elf(var position: Point, var proposed: Point? = null) {

    fun checkAll(): List<Point> {
        return listOf(
            Point(position.x - 1, position.y - 1),
            Point(position.x, position.y - 1),
            Point(position.x + 1, position.y - 1),
            Point(position.x - 1, position.y),
            Point(position.x + 1, position.y),
            Point(position.x - 1, position.y + 1),
            Point(position.x, position.y + 1),
            Point(position.x + 1, position.y + 1),
        )
    }

    fun checkDirection(direction: Direction): List<Point> {
        return when(direction) {
            Direction.NORTH -> listOf(Point(position.x - 1, position.y - 1),
                Point(position.x, position.y - 1),
                Point(position.x + 1, position.y - 1))
            Direction.SOUTH -> listOf(Point(position.x - 1, position.y + 1),
                Point(position.x, position.y + 1),
                Point(position.x + 1, position.y + 1))
            Direction.WEST -> listOf(Point(position.x - 1, position.y - 1),
                Point(position.x - 1, position.y),
                Point(position.x - 1, position.y + 1))
            Direction.EAST -> listOf(Point(position.x + 1, position.y - 1),
                Point(position.x + 1, position.y),
                Point(position.x + 1, position.y + 1))
        }
    }
}

fun List<Elf>.print() {

    val minX = this.minOf { it.position.x }
    val maxX = this.maxOf { it.position.x }
    val minY = this.minOf { it.position.y }
    val maxY = this.maxOf { it.position.y }

    (minY..maxY).forEach { y ->
        (minX..maxX).forEach { x ->
            if (this.find { it.position == Point(x, y) } == null) {
                print('.')
            }
            else print('#')
        }
        print('\n')
    }
    println("-------------------------")

}

fun List<Elf>.result(): Int {
    val minX = this.minOf { it.position.x }
    val maxX = this.maxOf { it.position.x }
    val minY = this.minOf { it.position.y }
    val maxY = this.maxOf { it.position.y }

    return (maxX - minX) * (maxY - minY) - this.size
}

fun List<Elf>.propose(directions: List<Direction>) {

    val lookup = this.map { it.position }.toSet()

    this.forEach {
        it.proposed = null
    }

    // jeez, refactor this
    this.forEach {
        val all = it.checkAll()
        if (lookup.intersect(all).isNotEmpty()) {
            val d1 = it.checkDirection(directions[0])
            if (lookup.intersect(d1).isEmpty()) {
                it.proposed = d1[1]
            } else {
                val d2 = it.checkDirection(directions[1])
                if (lookup.intersect(d2).isEmpty()) {
                    it.proposed = d2[1]
                } else {
                    val d3 = it.checkDirection(directions[2])
                    if (lookup.intersect(d3).isEmpty()) {
                        it.proposed = d3[1]
                    } else {
                        val d4 = it.checkDirection(directions[3])
                        if (lookup.intersect(d4).isEmpty()) {
                            it.proposed = d4[1]
                        }
                    }
                }
            }
        }
    }
}

fun List<Elf>.move() {
    val proposed = this.mapNotNull { it.proposed }

    this.forEach { elf ->
        if (proposed.count { it == elf.proposed } == 1) {
            elf.proposed?.let { elf.position = it }
        }
    }
}

fun breakfast(input: List<String>) : Int {
    val elves = input.flatMapIndexed { y, s ->
        s.mapIndexedNotNull { x, c ->
            if (c == '#') Elf(Point(x, y))
            else null
        }
    }
    elves.print()
    repeat(10) {
        elves.propose(getDirections(it))
        elves.move()
        elves.print()
    }
    val result = elves.result()
    println("breakfast: $result")
    return result
}

fun lunch(input: List<String>): Int {
    val elves = input.flatMapIndexed { y, s ->
        s.mapIndexedNotNull { x, c ->
            if (c == '#') Elf(Point(x, y))
            else null
        }
    }
    repeat(1000) {cycle ->
        elves.propose(getDirections(cycle))
        val propsed = elves.count { it.proposed != null }
        if (propsed == 0) {
            val result = cycle + 1
            println("lunch: $result")
            return result
        }
        elves.move()
    }
    error("could not complete lunch")
}