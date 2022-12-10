package day8

import java.io.File

fun main() {
    val input = File("data/day8/input.txt").readLines()
    breakfast(input)
    lunch(input)
    testTreetopTreeHouse()
}

data class Point(val x: Int, val y: Int)
data class Tree(val point: Point, val height: Int)

fun Map<Point, Tree>.visible(tree: Tree): Boolean {

    val hiddenWest = this.filter {
        it.value.point.y == tree.point.y && it.value.point.x < tree.point.x
    }.all {
        it.value.height < tree.height
    }
    val hiddenEast = this.filter {
        it.value.point.y == tree.point.y && it.value.point.x > tree.point.x
    }.all {
        it.value.height < tree.height
    }
    val hiddenNorth = this.filter {
        it.value.point.x == tree.point.x && it.value.point.y > tree.point.y
    }.all {
        it.value.height < tree.height
    }
    val hiddenSouth = this.filter {
        it.value.point.x == tree.point.x && it.value.point.y < tree.point.y
    }.all {
        it.value.height < tree.height
    }

    return hiddenWest || hiddenEast || hiddenNorth || hiddenSouth
}

fun Map<Point, Tree>.score(tree: Tree): Int {

    fun count(heights: List<Int>, height: Int): Int {
        if (heights.isEmpty()) return 0
        if (height > heights.max()) return heights.size
        return heights.indexOfFirst { it >= height } + 1
    }

    val scoreWest = count(this.filter {
        it.value.point.y == tree.point.y && it.value.point.x < tree.point.x
    }.map { it.value.height }.reversed(), tree.height)
    val scoreEast = count(this.filter {
        it.value.point.y == tree.point.y && it.value.point.x > tree.point.x
    }.map { it.value.height }, tree.height)
    val scoreNorth = count(this.filter {
        it.value.point.x == tree.point.x && it.value.point.y < tree.point.y
    }.map { it.value.height }.reversed(), tree.height)
    val scoreSouth = count(this.filter {
        it.value.point.x == tree.point.x && it.value.point.y > tree.point.y
    }.map { it.value.height }, tree.height)

    return scoreWest * scoreEast * scoreNorth * scoreSouth
}

fun breakfast(input: List<String>) : Int {
    val garden = input.flatMapIndexed { y, s ->
        s.mapIndexed() { x, h ->
            Tree(Point(x, y), h.toString().toInt())
        }
    }
    val map = garden.map {
        it.point to it
    }.toMap()
    val result = map.filter { map.visible(it.value) }.size
    println("breakfast: $result")
    return result
}

fun lunch(input: List<String>) : Int {
    val garden = input.flatMapIndexed { y, s ->
        s.mapIndexed() { x, h ->
            Tree(Point(x, y), h.toString().toInt())
        }
    }
    val map = garden.map {
        it.point to it
    }.toMap()
    val result = map.maxOf { map.score(it.value) }
    println("lunch: $result")
    return result
}

fun testTreetopTreeHouse() {
    println("running tests for day 8")
    val input = File("data/day8/input.txt").readLines()
    check(breakfast(input) == 1662) { "breakfast failed" }
    check(lunch(input) == 537600) { "lunch failed" }
    println("2 tests ok")
    println("---")
}