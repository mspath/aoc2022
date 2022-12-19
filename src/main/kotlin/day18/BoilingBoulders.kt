package day18

import java.io.File
import kotlin.collections.ArrayDeque

fun main() {
    val input = File("data/day18/input.txt").readLines()
    breakfast(input)
    lunch(input)
}

data class Cube(val x: Int, val y: Int, val z: Int)

fun Cube.getNeighbors(): List<Cube> {
    val n = Cube(x, y - 1, z)
    val e = Cube(x + 1, y, z)
    val s = Cube(x, y + 1, z)
    val w = Cube(x - 1, y, z)
    val u = Cube(x, y, z + 1)
    val d = Cube(x, y, z - 1)
    return listOf(n, e, s, w, u, d)
}

fun List<Cube>.getOpenNeighbors(cube: Cube): List<Cube> = cube.getNeighbors().filter { !contains(it) }

fun breakfast(input: List<String>): Int {
    val droplet = input.map {
        val (x, y, z) = it.split(",")
        Cube(x.toInt(), y.toInt(), z.toInt())
    }
    val surface = droplet.sumOf { cube ->
        val n = cube.getNeighbors()
        n.count { !droplet.contains(it) }
    }
    println("breakfast: $surface")
    return surface
}

fun lunch(input: List<String>): Int {
    val droplet = input.map {
        val (x, y, z) = it.split(",")
        Cube(x.toInt(), y.toInt(), z.toInt())
    }

    val neighbors = droplet.flatMap { it.getNeighbors() }.filter { !droplet.contains(it) }.toSet()
    val maxX = droplet.maxOf { it.x }
    val minX = droplet.minOf { it.x }
    val maxY = droplet.maxOf { it.y }
    val minY = droplet.minOf { it.y }
    val maxZ = droplet.maxOf { it.z }
    val minZ = droplet.minOf { it.z }

    // open is the set of neighbors which are guaranteed to be outside
    // all their neighbors therefore will be outside too
    val open = neighbors.filter { it.x >= maxX || it.x <= minX || it.y >= maxY || it.y <= minY
            || it.z >= maxZ || it.z <= minZ }
    val deque: ArrayDeque<Cube> = ArrayDeque()
    deque.addAll(open)
    val seen: MutableSet<Cube> = mutableSetOf()
    seen.addAll(droplet)
    while (deque.isNotEmpty()) {
        val next = deque.removeFirst()
        val nextNeighbors = next.getNeighbors().filter { !seen.contains(it) }
            .filter { it.x in minX..maxX && it.y in minY..maxY
                    && it.z in minZ..maxZ }
        seen.add(next)
        nextNeighbors.forEach {
            deque.add(it)
        }
    }

    val surface = droplet.sumOf { cube ->
        val n = cube.getNeighbors().filter { seen.contains(it) }
        n.count { !droplet.contains(it) }
    }
    println("lunch: $surface")
    return surface
}

fun testBoilingBoulders() {
    val input = File("data/day18/input.txt").readLines()
    check(breakfast(input) == 3550) { "breakfast failed" }
    check(lunch(input) == 2028) { "lunch failed" }
    println("2 tests ok")
}