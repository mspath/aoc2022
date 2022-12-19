package day14

import java.io.File

typealias Cave = HashSet<Point>

fun main() {
    val input = File("data/day14/sample.txt").readLines()
    breakfast(input)
    //lunch(input)
}

enum class Type {
    ROCK,
    SAND
}

data class Point(val x: Int, val y: Int)

fun parseCave(input: List<String>): Cave {
    val cave: Cave = hashSetOf()
    input.forEach {line ->
        val tokens = line.split(" -> ")
        tokens.windowed(2).forEach {
            val (x1, y1) = it.first().split(",").map { it.toInt() }
            val (x2, y2) = it.last().split(",").map { it.toInt() }
            if (x1 == x2) {
                (minOf(y1, y2)..maxOf(y1, y2)).forEach { y ->
                    cave.add(Point(x1, y))
                }
            }
            if (y1 == y2) {
                (minOf(x1, x2)..maxOf(x1, x2)).forEach { x ->
                    cave.add(Point(x, y1))
                }
            }
        }
    }
    return cave
}

fun Cave.addFloor() {
    val floorY = this.maxY() + 2
    (0..1000).forEach { x ->
        this.add(Point(x, floorY))
    }
}

fun Cave.minX() = this.minOf { it.x }
fun Cave.maxX() = this.maxOf { it.x }
fun Cave.maxY() = this.maxOf { it.y }
fun Cave.print() {
    val minX = this.minX()
    val maxX = this.maxX()
    val maxY = this.maxY()
    (0..maxY).forEach { y ->
        (minX..maxX).forEach { x ->
            if (x == 500 && y == 0) print('+')
            else if (this.contains(Point(x, y))) print('#')
            else print('.')
        }
        print('\n')
    }
}

// find the next Point starting at start
fun Cave.next(start: Point): Point {
    var spot = start
    while (!this.contains(Point(spot.x, spot.y + 1))) {
        spot = Point(spot.x, spot.y + 1)
    }
    if (!this.contains(Point(spot.x - 1, spot.y + 1))) {
        spot = this.next(Point(spot.x - 1, spot.y + 1))
    }
    else if (!this.contains(Point(spot.x + 1, spot.y + 1))) {
        spot = this.next(Point(spot.x + 1, spot.y + 1))
    }
    return spot
}

fun breakfast(input: List<String>) : Int {
    val cave = parseCave(input)
    val start = Point(500, 0)
    val abyss = cave.maxY()
    var last = Point(start.x, start.y + 1)
    cave.print()
    var counter = 0
    while (last.y > 0 && last.y <= abyss) {
        counter++
        last = cave.next(start)
        cave.add(last)
        cave.print()
        println(last)
        println(counter)
    }
    println("--")
    println(cave)
    return 0
}

fun lunch(input: List<String>) : Int {
    val cave = parseCave(input)
    println(cave.size)
    cave.addFloor()
    val start = Point(500, 0)
    val abyss = cave.maxY()
    var last = Point(start.x, start.y + 1)
    cave.print()
    var counter = 0
    while (last.y > 0 && last.y < abyss) {
        counter++
        last = cave.next(start)
        cave.add(last)
        //cave.print()
        println(last)
        println(counter)
    }
    println("--")
    cave.print()
    return 0
}