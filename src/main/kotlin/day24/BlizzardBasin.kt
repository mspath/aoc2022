package day24

import java.io.File

fun main() {
    val input = File("data/day24/sample.txt").readLines()
    breakfast(input)
    //lunch(input)
}

data class Blizzard(val x: Int, val y: Int, val direction: Char)

fun List<Blizzard>.nextPositions(expedition: Position, time: Int): List<Position> {

    val ex = expedition.x
    val ey = expedition.y
    val width = this.maxOf { it.x }
    val height = this.maxOf { it.y }
    val start = Position(0, -1)

    fun check(p: Position): Boolean {
        val lefties = this.filter { it.direction == '<' }.map { Position((it.x - time - 1).mod(width), it.y) }
        val righties = this.filter { it.direction == '>' }.map { Position((it.x + time + 1).mod(width), it.y) }
        val uppies = this.filter { it.direction == '^' }.map { Position(it.x, (it.y - time - 1).mod(height)) }
        val downies = this.filter { it.direction == 'v' }.map { Position(it.x, (it.y + time + 1).mod(height)) }
        return !lefties.contains(p) && !righties.contains(p) && !uppies.contains(p) && !downies.contains(p)
    }

    val potentials = listOf(Position(ex - 1, ey),
        Position(ex + 1, ey),
        Position(ex, ey),
        Position(ex, ey - 1),
        Position(ex, ey + 1))
        .filter { (it.x in (0 until width) && it.y in (0 until height)) || it == start }
        .filter { check(it) }

    return potentials
}

fun List<Blizzard>.print(expedition: Position = Position(0, -1), time: Int = 0) {

    val width = this.maxOf { it.x } + 1
    val height = this.maxOf { it.y } + 1

    val shift = this.mapNotNull {
        when(it.direction) {
            '<' -> it.copy(x = (it.x - time).mod(width))
            '>' -> it.copy(x = (it.x + time).mod(width))
            '^' -> it.copy(y = (it.y - time).mod(height))
            'v' -> it.copy(y = (it.y + time).mod(height))
            else -> null
        }
    }
    val lookup = shift.associateBy { Position(it.x, it.y) }

//    println("time: $time")
//    this.zip(shift).forEach {
//        println(it)
//    }



    // first row
    print('#')
    if (expedition == Position(0, -1)) print('E')
    else print('.')
    repeat(width) { print('#') }
    print('\n')
    (0 until height).forEach { y ->
        print('#')
        (0 until width).forEach {x ->
            if (lookup.containsKey(Position(x, y))) print(lookup.getValue(Position(x, y)).direction)
            else if (Position(x, y) == expedition) print('E')
            else print('.')
        }
        print('#')
        print('\n')
    }
    // last row
    repeat(width) { print('#') }
    print('.')
    print('#')
    println("\n------------------")
}

data class Position(val x: Int, val y: Int)

data class State(val position: Position, val time: Int)

fun breakfast(input: List<String>): Int {
    val blizzards = input.drop(1).dropLast(1).flatMapIndexed { y, row ->
        row.drop(1).dropLast(1).mapIndexedNotNull { x, c ->
            if (c != '.') Blizzard(x, y, c)
            else null
        }
    }
    val width = input.first().length - 2
    val height = input.size - 2
    // only time we are there at start
    val expedition = Position(0, -1)
    // our goal. if we are there safe in the next minute we can step down to our exit.
    // (I checked, no blizzard can surprise us in the exit column from below)
    val goal = Position(width, height)

    repeat(3) {
        blizzards.print(time = it)
    }

    val states: MutableList<State> = mutableListOf(State(Position(0, 1), 0))
    println(states)

    repeat(30) {
        val nextStates = states.flatMap {
            blizzards.nextPositions(it.position, it.time).map {position ->
                if (position == goal) {
                    println("goal found")
                    println(State(position, it.time + 1 ))
                }
                State(position, it.time + 1 )
            }
        }
        //println(it)
        states.clear()
        states.addAll(nextStates)
        blizzards.print(nextStates.last().position, it + 1)
        //println(states)
    }


    TODO()
}


fun lunch(input: List<String>): Int {
    TODO()
}