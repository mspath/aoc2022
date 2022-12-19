package day15

import java.io.File

fun main() {
    val input = File("data/day15/sample.txt").readLines()
    //breakfast(input)
     lunch(input)
}

data class Point(val x: Int, val y: Int): Comparable<Point> {

    fun nextSE() = Point(x + 1, y + 1)
    fun nextSW() = Point(x - 1, y + 1)
    fun nextNW() = Point(x - 1, y - 1)
    fun nextNE() = Point(x + 1, y - 1)

    override fun compareTo(other: Point): Int {
        return x * 4000000 + y
    }
}

data class Sensor(val spot: Point, val beacon: Point) {

    fun getDistance(): Int = kotlin.math.abs(beacon.x - spot.x) + kotlin.math.abs(beacon.y - spot.y)

    fun containsRow(row: Int): Boolean {
        return kotlin.math.abs(row - spot.y) <= getDistance()
    }

    fun getRowrange(row: Int): IntRange {
        if (!this.containsRow(row)) return IntRange.EMPTY
        val offsetY = kotlin.math.abs(spot.y - row)
        return (spot.x - (getDistance() - offsetY) ..spot.x + (getDistance() - offsetY))
    }

    fun containsPoint(p: Point) = kotlin.math.abs(p.x - spot.x) + kotlin.math.abs(p.y - spot.y) <= getDistance()

    fun getOneOffs(): List<Point> {

        // beacon in same row
        val d = getDistance()
        val north = Point(spot.x, spot.y - d)
        val south = Point(spot.x, spot.y + d)
        val west = Point(spot.x - d, spot.y)
        val east = Point(spot.x + d, spot.y)

        val oneOffs: MutableList<Point> = mutableListOf()

        var current = Point(spot.x, spot.y - d - 1)
        val r = 0..4000000
        (0..d).forEach {
            current = current.nextSE()
            if (current.x in r && current.y in r) oneOffs.add(current.copy())
        }
        (0..d).forEach {
            current = current.nextSW()
            if (current.x in r && current.y in r) oneOffs.add(current.copy())
        }
        (0..d).forEach {
            current = current.nextNW()
            if (current.x in r && current.y in r) oneOffs.add(current.copy())
        }
        (0..d).forEach {
            current = current.nextNE()
            if (current.x in r && current.y in r) oneOffs.add(current.copy())
        }
        return oneOffs.toList()
    }
}

fun breakfast(input: List<String>): Int {
    val beacons = input.map {
        val (s, b) = it.split(": closest beacon is at x=")
        val sx = s.substringAfter("x=").substringBefore(", y").toInt()
        val sy = s.substringAfter("y=").toInt()
        val bx = b.substringBefore(", y").toInt()
        val by = b.substringAfter("y=").toInt()
        Sensor(Point(sx, sy), Point(bx, by))
    }
    val ranges = beacons.map { it.getRowrange(10) }
    val set = ranges.flatMap { it.toList() }.toSet()
    println(set.size - 1)
    return set.size - 1
    // 5240819 - 1 (we have to subtract the included beacon)
}

// incomplete. it works for the example but won't scale to the full set.
fun lunch(input: List<String>): Int {
    val beacons = input.map {
        val (s, b) = it.split(": closest beacon is at x=")
        val sx = s.substringAfter("x=").substringBefore(", y").toInt()
        val sy = s.substringAfter("y=").toInt()
        val bx = b.substringBefore(", y").toInt()
        val by = b.substringAfter("y=").toInt()
        Sensor(Point(sx, sy), Point(bx, by))
    }

    val oneOffs = beacons.map { it.getOneOffs() }
    val twoOffs = oneOffs.flatMap { it }
    val sorted = twoOffs.groupingBy { it }.eachCount().toList().sortedByDescending { it.second }
    val first = sorted.first()
    println(first.first.x * 4000000 + first.first.y)

    TODO()
}