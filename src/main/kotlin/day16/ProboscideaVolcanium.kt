package day16

import java.io.File

typealias Cave = Map<String, Valve>

fun main() {
    val input = File("data/day16/sample.txt").readLines()
    breakfast(input)
    //lunch(input)
}

fun Cave.getPaths(previous: List<List<Valve>>, time: Int): List<List<Valve>> {

    println("in get paths $time ${previous.size}")
    // we are done if the lists are as long as the time or one below
    val cycleNr = previous.first().size
    if (time - cycleNr <= 2) return previous.toList()

    // here we store the next list of paths
    val next: MutableList<List<Valve>> = mutableListOf()

    //
    previous.forEach { current ->
        val last = current.last()
        val turnOn = last.flowRate > 0 && !current.turnedOn(last)
        val children = if(turnOn) last.tunnels.map { this.getValue(it) } + last.copy() else last.tunnels.map { this.getValue(it) }
        children.forEach { next.add(current + it) }
    }

    return this.getPaths(next.toList(), time - 1)
}

fun Cave.getPathsStrings(depth: Int): List<List<String>> {
    val paths: MutableList<List<String>> = mutableListOf()
    val nextPaths: MutableList<List<String>> = mutableListOf()
    val start: List<String> = listOf("AA")
    paths.add(start)
    repeat(depth) {
        paths.forEach { path ->
            val last = path.last()
            val children = this.getValue(last).tunnels
            //println(children)
            children.forEach { child ->
                val new = path + child
                nextPaths.add(new)
            }
        }
        paths.clear()
        paths.addAll(nextPaths.toSet())
        nextPaths.clear()
    }
    return paths.toList()
}

fun Cave.getAllPaths(): List<List<String>> {
    TODO()
}

fun List<Valve>.turnedOn(v: Valve): Boolean {
    val on = this.windowed(2).any { it.first() == v && it.last() == v }
    return on
}

data class Valve (val name: String, val flowRate: Int, val tunnels: List<String>) {

    companion object {
        fun from(s: String): Valve {
            val name = s.substringAfter("Valve ").substringBefore(" has ")
            val flowRate = s.substringAfter("rate=").substringBefore("; tunnel").toInt()
            val tunnels = if (s.contains("tunnels")) {
                s.substringAfter("valves ").split(", ")
            }
            else listOf(s.substringAfter(" to valve "))
            return Valve(name, flowRate, tunnels)
        }
    }
}

fun breakfast(input: List<String>): Int {
    val valves = input.map { Valve.from(it) }
    val cave = valves.associateBy {
        it.name
    }
    cave.forEach {
        println(it)
    }
    val paths = cave.getPathsStrings(5)
    paths.forEach {
        println(it)
    }
    println("---")
    val l = cave.getPaths(listOf(listOf(cave.getValue("AA"))), 30)
    TODO()
}

fun lunch(input: List<String>): Int {

    TODO()
}