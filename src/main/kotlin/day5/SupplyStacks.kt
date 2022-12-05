package day5

import java.io.File
import java.util.*

typealias Stacks = List<Stack<Char>>

fun main() {
    val input = File("data/day5/input.txt").readText()
    val (inputSetup, inputInstructions) = input.split("\n\n")
    breakfast(inputSetup, inputInstructions.lines())
    lunch(inputSetup, inputInstructions.lines())
    testSupplyStacks()
}

fun parseSetup(input: String): Stacks {
    // last line contains the numbers of the lanes, so we drop it
    // we also reverse it to fill up the stack from the bottom
    val lines = input.lines().dropLast(1).reversed()
    // hardcoded, but there is no reliable heuristics for larger sets
    val count = 9
    val height = lines.size
    val stacks = (0 until count).map {
        Stack<Char>()
    }
    repeat(count) { crate ->
        val i = 1 + crate * 4
        repeat(height) { row ->
            val c = lines[row][i]
            if (c in 'A'..'Z') stacks[crate].push(c)
        }
    }
    return stacks
}

fun parseLazy(input: String): Stacks {
    val s1 = Stack<Char>()
    "BGSC".asIterable().toList().forEach { s1.push(it) }
    val s2 = Stack<Char>()
    "TMWHJNVG".asIterable().toList().forEach { s2.push(it) }
    val s3 = Stack<Char>()
    "MQS".asIterable().toList().forEach { s3.push(it) }
    val s4 = Stack<Char>()
    "BSLTWNM".asIterable().toList().forEach { s4.push(it) }
    val s5 = Stack<Char>()
    "JZFTVGWP".asIterable().toList().forEach { s5.push(it) }
    val s6 = Stack<Char>()
    "CTBGQHS".asIterable().toList().forEach { s6.push(it) }
    val s7 = Stack<Char>()
    "TJPBW".asIterable().toList().forEach { s7.push(it) }
    val s8 = Stack<Char>()
    "GDCZFTQM".asIterable().toList().forEach { s8.push(it) }
    val s9 = Stack<Char>()
    "NSHBPF".asIterable().toList().forEach { s9.push(it) }
    return listOf(s1, s2, s3, s4, s5, s6, s7, s8, s9)
}

data class Instruction(val count: Int, val from: Int, val to: Int)

fun breakfast(inputSetup: String, inputInstructions: List<String>) : String {
    val stacks = parseSetup(inputSetup)
    val instructions = inputInstructions.map {
        val tokens = it.split(" ")
        Instruction(tokens[1].toInt(), tokens[3].toInt(), tokens[5].toInt())
    }

    instructions.forEach {instruction ->
        repeat(instruction.count) {
            val name = stacks[instruction.from - 1].pop()
            stacks[instruction.to - 1].push(name)
        }
    }

    val result = stacks.map { it.pop() }.joinToString("")
    println("breakfast: $result")
    return result
}

fun lunch(inputSetup: String, inputInstructions: List<String>) : String {
    val stacks = parseSetup(inputSetup)
    val instructions = inputInstructions.map {
        val tokens = it.split(" ")
        Instruction(tokens[1].toInt(), tokens[3].toInt(), tokens[5].toInt())
    }

    instructions.forEach {instruction ->
        // still unstacking and restacking one by one...
        val tmp = Stack<Char>()
        repeat(instruction.count) {
            val name = stacks[instruction.from - 1].pop()
            tmp.push(name)
        }
        repeat(instruction.count) {
            val name = tmp.pop()
            stacks[instruction.to - 1].push(name)
        }
    }

    val result = stacks.map { it.pop() }.joinToString("")
    println("lunch: $result")
    return result
}

fun testSupplyStacks() {
    val input = File("data/day5/input.txt").readText()
    val (inputSetup, inputInstructions) = input.split("\n\n")
    check(breakfast(inputSetup, inputInstructions.lines()) == "CFFHVVHNC") { "breakfast failed" }
    check(lunch(inputSetup, inputInstructions.lines()) == "FSZWBPTBG") { "lunch failed" }
    println("2 tests ok")
}