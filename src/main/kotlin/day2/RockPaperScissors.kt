package day2

import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

fun main() {
    val input = File("data/day2/input.txt").readLines()
    breakfast(input)
    lunch(input)
    testRockPaperScissors()
    benchmarkRockPaperScissors()
}

enum class Symbol(val value: Int) {
    ROCK(1), PAPER(2), SCISSORS(3);
}

enum class Result(val points: Int) {
    LOSE(0), DRAW(3), WIN(6)
}

data class Game(val first: Char, val second: Char) {
    fun scoreByHands() : Int {
        if (first == 'A') {
            if (second == 'X') return Result.DRAW.points + Symbol.ROCK.value
            if (second == 'Y') return Result.WIN.points + Symbol.PAPER.value
            if (second == 'Z') return Result.LOSE.points + Symbol.SCISSORS.value
        }
        if (first == 'B') {
            if (second == 'X') return Result.LOSE.points + Symbol.ROCK.value
            if (second == 'Y') return Result.DRAW.points + Symbol.PAPER.value
            if (second == 'Z') return Result.WIN.points + Symbol.SCISSORS.value
        }
        if (first == 'C') {
            if (second == 'X') return Result.WIN.points + Symbol.ROCK.value
            if (second == 'Y') return Result.LOSE.points + Symbol.PAPER.value
            if (second == 'Z') return Result.DRAW.points + Symbol.SCISSORS.value
        }
        return 0
    }

    fun scoreByStrategy() : Int {
        if (first == 'A') {
            if (second == 'X') return Symbol.SCISSORS.value + Result.LOSE.points
            if (second == 'Y') return Symbol.ROCK.value + Result.DRAW.points
            if (second == 'Z') return Symbol.PAPER.value + Result.WIN.points
        }
        if (first == 'B') {
            if (second == 'X') return Symbol.ROCK.value + Result.LOSE.points
            if (second == 'Y') return Symbol.PAPER.value + Result.DRAW.points
            if (second == 'Z') return Symbol.SCISSORS.value + Result.WIN.points
        }
        if (first == 'C') {
            if (second == 'X') return Symbol.PAPER.value + Result.LOSE.points
            if (second == 'Y') return Symbol.SCISSORS.value + Result.DRAW.points
            if (second == 'Z') return Symbol.ROCK.value + Result.WIN.points
        }
        return 0
    }
}

fun breakfast(input: List<String>) : Int {
    val play = input.map {
        Game(it[0], it[2])
    }
    val result = play.map { it.scoreByHands() }.sum()
    println("breakfast: $result")
    return result
}

fun lunch(input: List<String>) : Int {
    val play = input.map {
        Game(it[0], it[2])
    }
    val result = play.map { it.scoreByStrategy() }.sum()
    println("lunch: $result")
    return result
}

fun testRockPaperScissors() {
    val input = File("data/day2/input.txt").readLines()
    check(breakfast(input) == 14264) { "breakfast failed" }
    check(lunch(input) == 12382) { "lunch failed" }
    println("2 tests ok")
}

@OptIn(ExperimentalTime::class)
fun benchmarkRockPaperScissors() {
    val input = File("data/day2/input.txt").readLines()
    val (_, durationBreakfast) = measureTimedValue { breakfast(input) }
    println("breakfast duration: $durationBreakfast")
    val (_, durationLunch) = measureTimedValue { lunch(input) }
    println("lunch duration: $durationLunch")
}