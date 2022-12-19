package day17

import java.io.File
import java.lang.Integer.max

typealias Chamber = MutableList<Point>

fun main() {
    val input = File("data/day17/input.txt").readText()
    breakfast(input)
    //lunch(input)
}

fun Chamber.nextStart(): Point = if (this.isEmpty()) Point(2, 3) else Point(2, this.maxOf { it.y } + 4)

fun Chamber.print() {

    if (this.isEmpty()) return
    val maxY = this.maxOf { it.y }

    println("--")
    (maxY downTo 0).forEach { y ->
        (0..6).forEach { x ->
            if (this.contains(Point(x, y))) print('#') else print('.')
        }
        print('\n')
    }

}

fun Chamber.sig(): String {
    val maxY = this.maxOf { it.y }
    val sig = StringBuilder()
    (maxY - 30 ..maxY).forEach { y ->
        (0..6).forEach { x ->
            if (this.contains(Point(x, y))) sig.append('x') else sig.append('.')
        }
    }
    return sig.toString()
}


fun Chamber.printWith(s: List<Point>) {

    if (this.isEmpty()) return
    val maxY = max(this.maxOf { it.y }, s.maxOf { it.y })

    println("--")
    (maxY downTo 0).forEach { y ->
        (0..6).forEach { x ->
            if (s.contains(Point(x, y))) print ('$') else if (this.contains(Point(x, y))) print('#') else print('.')
        }
        print('\n')
    }

}

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
}

fun breakfast(input: String) : Int {

    val s1 = listOf(
        Point(0, 0),
        Point(1, 0),
        Point(2, 0),
        Point(3, 0))

    val s2 = listOf(
        Point(1, 0),
        Point(0, 1),
        Point(1, 1),
        Point(2, 1),
        Point(1, 2))

    val s3 = listOf(
        Point(0, 0),
        Point(1, 0),
        Point(2, 0),
        Point(2, 1),
        Point(2, 2))

    val s4 = listOf(
        Point(0, 0),
        Point(0, 1),
        Point(0, 2),
        Point(0, 3))

    val s5 = listOf(
        Point(0, 0),
        Point(0, 1),
        Point(1, 0),
        Point(1, 1))

    val series = listOf(s1, s2, s3, s4, s5)
    val lookup = mapOf('<' to Point(-1, 0), '>' to Point(1, 0))
    val chamber: Chamber = mutableListOf()
    //chamber.addAll(floor)

    // number of stone
    var iteration = 0
    // number of shift
    var counter = 0

    val seen: HashSet<String> = hashSetOf()

    chamber.print()
    repeat(2022) {
//        if (!chamber.isEmpty()) {
//            val sig = chamber.sig()
//            if (seen.contains(sig)) {
//                println("found sig: $sig")
//                println("iteration: $iteration")
//                println("clearing it and starting fresh")
//                seen.clear()
//            }
//            seen.add(sig)
//        }
        var position = chamber.nextStart()
        val shape = series.get(iteration % 5)
        var current = shape.map { it + position }
        while (true) {
            val nextShift = input[counter % input.length]
            counter++
            val nextPosShift = position + lookup.getValue(nextShift)
            val next = shape.map { it + nextPosShift }
            if (next.none { it in chamber } && next.none { it.x > 6 } && next.none { it.x < 0 }) {
                position += lookup.getValue(nextShift)
                current = shape.map { it + position }
            }
            val nextPosDown = position + Point(0, -1)
            val nextDown = shape.map { it + nextPosDown }
            if (nextDown.none { it in chamber } && nextDown.none { it.y < 0 }) {
                position += Point(0, -1)
                current = shape.map { it + position }
            } else {
                chamber.addAll(current)
                break
            }
        }
        if (iteration == 303) println("303: ${chamber.size}")
        if (iteration == 303 + 1427) println("303+1427: ${chamber.size}")
        if (iteration == 303 + 1730) println("303+1730: ${chamber.size}")
        iteration++
    }

    //chamber.print()
    println(chamber.maxOf { it.y } + 1)
    TODO()
}

fun lunch(input: String) : Int {
    val trillion = 1_000_000_000_000
    val start = 303L
    val cycle = 1715L
    val times = trillion / cycle - 1
    val rem = trillion % times
    TODO()
}