package day7

import java.io.File

fun main() {
    val input = File("data/day7/input.txt").readLines()
    breakfast(input)
    lunch(input)
    testNoSpaceLeftOnTheDevice()
}

// this will build all paths which will be affected
fun String.getSubpaths(path: String): List<String> {
    val subpaths = mutableListOf(path)
    var rest = path
    while (rest.count { it == '/' } > 1) {
        rest = rest.substringBeforeLast('/')
        subpaths.add(rest)
    }
    return subpaths
}

// adds a value to the path and all subpaths
fun HashMap<String, Int>.add(path: String, value: Int) {
    val paths = path.getSubpaths(path)
    paths.forEach {
        if (this.computeIfPresent(it) { _, v -> v + value } == null) {
            this[it] = value
        }
    }
}

fun parseInput(input: List<String>): HashMap<String, Int> {
    val collector: HashMap<String, Int> = hashMapOf()
    var path = ""

    input.drop(1).forEach {
        if (it == "$ cd ..") {
            path = path.substringBeforeLast("/")
        }
        else if (it.startsWith("$ cd ")) {
            path = path + "/" + it.substringAfter("cd ")
        }
        if (it[0] in '1'..'9') {
            val i = it.substringBefore(" ").toInt()
            collector.add(path, i)
        }
    }
    return collector
}

fun getTotal(input: List<String>) = input.sumOf {
        if (it[0] in '1'..'9') {
            it.substringBefore(" ").toInt()
        } else 0
    }

fun breakfast(input: List<String>) : Int {
    val collector = parseInput(input)
    val dirs100kMax = collector.filter { it.value < 100000 }.toList()
    val result = dirs100kMax.sumOf { it.second }
    println("breakfast: $result")
    return result
}

fun lunch(input: List<String>) : Int {
    val collector = parseInput(input)
    val systemRequired = 70000000 - 30000000
    val used = getTotal(input)
    val overlap = used - systemRequired
    val bigDirs = collector.filter { it.value > overlap }.toList().sortedBy { it.second }
    val result = bigDirs.first().second
    println("lunch: $result")
    return result
}

fun testNoSpaceLeftOnTheDevice() {
    println("running tests for day 7")
    val input = File("data/day7/input.txt").readLines()
    check(breakfast(input) == 1770595) { "breakfast failed" }
    check(lunch(input) == 2195372) { "lunch failed" }
    println("2 tests ok")
    println("---")
}