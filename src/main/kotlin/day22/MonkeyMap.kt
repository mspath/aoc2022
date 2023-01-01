package day22

import java.io.File

typealias Board = Map<Point, Location>

fun main() {
    val input = File("data/day22/input.txt").readText()
    //breakfast(input)
    lunch(input)
}

data class Point(val c: Int, val r: Int)
data class Location(val point: Point, val wall: Boolean)
data class Position(var point: Point, var direction: Direction)
data class InputPoint(val x: Int, val y: Int, val c: Char)

enum class Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST,
}

fun Point.nextPoint(direction: Direction): Point {
    return when(direction) {
        Direction.NORTH -> this.copy(r = r - 1)
        Direction.EAST -> this.copy(c = c + 1)
        Direction.SOUTH -> this.copy(r = r + 1)
        Direction.WEST -> this.copy(c = c - 1)
    }
}

fun Board.nextPoint(position: Position, steps: Int): Point {
    var point = position.point
    val rMin = this.keys.filter { it.c == point.c }.minOf { it.r }
    val rMax = this.keys.filter { it.c == point.c }.maxOf { it.r }
    val cMin = this.keys.filter { it.r == point.r }.minOf { it.c }
    val cMax = this.keys.filter { it.r == point.r }.maxOf { it.c }
    repeat(steps) {
        //println(it)
        val next = point.nextPoint(position.direction)
        if (this.containsKey(next)) {
            // if we hit a wall return the previous point
            if (this.getValue(next).wall) return point
            // otherwise make the step
            point = next
        }
        // step is not on the map, so we have to jump to the other side
        else {
            val nextShifted = when (position.direction) {
                Direction.NORTH -> point.copy(r = rMax)
                Direction.EAST -> point.copy(c = cMin)
                Direction.SOUTH -> point.copy(r = rMin)
                Direction.WEST -> point.copy(c = cMax)
            }
            if (this.getValue(nextShifted).wall) return point
            point = nextShifted
        }
    }
    return point
    //TODO()
}

fun breakfast(input: String): Int {
    val inputMap = input.split("\n\n").first().split("\n")
    val inputInstructions = input.split("\n\n").last()
    val map = inputMap.flatMapIndexed { r, row ->
        row.mapIndexedNotNull { c, x ->
            when(x) {
                '#' -> Location(Point(c, r), true)
                '.' -> Location(Point(c, r), false)
                else -> null
            }
        }
    }
    val board = map.associateBy { it.point }
    val instructions = inputInstructions.replace("R", " R ")
        .replace("L", " L ").split(" ")
    val start = map.filter { it.point.r == 0}.minBy { it.point.c }

    var position = Position(start.point, Direction.EAST)

    instructions.forEach { instruction ->
        when (instruction) {
            "L" -> position.direction = Direction.values()[(position.direction.ordinal - 1).mod(4)]
            "R" -> position.direction = Direction.values()[(position.direction.ordinal + 1).mod(4)]
            else -> position.point = board.nextPoint(position, instruction.toInt())
        }
        println("$instruction - $position")
    }

    val result = (position.point.r + 1) * 1000 +
            (position.point.c + 1) * 4 +
            when(position.direction) {
                Direction.EAST -> 0
                Direction.SOUTH -> 1
                Direction.WEST -> 2
                Direction.NORTH -> 3
            }
    println("breakfast: $result")
    return result
}

fun lunch(input: String) {
    val inputLines = input.split("\n\n").first().split("\n")
    val inputPoints = inputLines.flatMapIndexed { y: Int, line: String ->
        line.mapIndexed { x: Int, c: Char ->
            InputPoint(x, y, c)
        }
    }
    val inputInstructions = input.split("\n\n").last()
    val block1 = inputPoints.filter { it.y in 0..49 && it.x in 50..99 }
    val block2 = inputPoints.filter { it.y in 0..49 && it.x in 100..149 }
    val block3 = inputPoints.filter { it.y in 50..99 && it.x in 50..99 }
    val block4 = inputPoints.filter { it.y in 100..149 && it.x in 0..49 }
    val block5 = inputPoints.filter { it.y in 100..149 && it.x in 50..99 }
    val block6 = inputPoints.filter { it.y in 150..199 && it.x in 0..49 }

    val sanitized: MutableList<Location> = mutableListOf()
    block1.forEach {
        when(it.c) {
            '#' -> sanitized.add(Location(Point(it.x - 50, it.y), true))
            '.' -> sanitized.add(Location(Point(it.x - 50, it.y), false))
            else -> {}
        }
    }
    block2.forEach {
        when(it.c) {
            '#' -> sanitized.add(Location(Point(it.x - 50, it.y), true))
            '.' -> sanitized.add(Location(Point(it.x - 50, it.y), false))
            else -> {}
        }
    }
    block5.reversed().forEach {
        when(it.c) {
            '#' -> sanitized.add(Location(Point(100 + (100 - it.x) + 50, 150 - it.y), true))
            '.' -> sanitized.add(Location(Point(100 + (100 - it.x) + 50, 150 - it.y), false))
            else -> {}
        }
    }
    block4.reversed().forEach {
        when(it.c) {
            '#' -> sanitized.add(Location(Point(150 + (50 - it.x) + 100, 150 - it.y), true))
            '.' -> sanitized.add(Location(Point(150 + (50 - it.x) + 100, 150 - it.y), false))
            else -> {}
        }
    }

    println(sanitized.size)
    val l0 = sanitized.filter { it.point.r == 0 }
    l0.forEach {
        println(it)
    }
}