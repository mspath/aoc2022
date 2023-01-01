package day19

import java.io.File

// incomplete
fun main() {
    val input = File("data/day19/sample.txt").readLines()
    breakfast(input)
    //lunch(input)
}

data class Blueprint(val id: Int,
                     val oreRobotCost: Int,
                     val clayRobotCost: Int,
                     val obsidianRobotCost: Int,
                     val obsidianRobotCostClay: Int,
                     val geodeRobotCost: Int,
                     val geodeRobotCostObsidian: Int) {

    // we can use the max val's to not do unnecessary work
    private val maxOre by lazy {
        val oreCosts = listOf(oreRobotCost, clayRobotCost, obsidianRobotCost, geodeRobotCost)
        oreCosts.max()
    }
    private val maxClay = obsidianRobotCostClay
    private val maxObsidian = geodeRobotCostObsidian

    fun nextState(state: State): List<State> {

        val next: MutableList<State> = mutableListOf(state.copy())

        if (state.oreRobots < maxOre && state.ore >= oreRobotCost) {
            next.add(state.copy(oreRobots = state.oreRobots + 1, ore = state.ore - oreRobotCost))
        }
        if (state.clayRobots < maxClay && state.ore >= clayRobotCost) {
            next.add(state.copy(clayRobots = state.clayRobots + 1, ore = state.ore - clayRobotCost))
        }
        if (state.obsidianRobots < maxObsidian && state.ore >= obsidianRobotCost && state.clay >= obsidianRobotCostClay) {
            next.add(state.copy(obsidianRobots = state.obsidianRobots + 1,
                ore = state.ore - obsidianRobotCost,
                clay = state.clay - obsidianRobotCostClay))
        }
        if (state.ore >= geodeRobotCost && state.obsidian >= geodeRobotCostObsidian) {
            next.add(state.copy(geodeRobots = state.geodeRobots + 1,
                ore = state.ore - geodeRobotCost,
                clay = state.obsidian - geodeRobotCostObsidian))
        }

        // we return the states updated with the produce of the robots
        return next.map {
            it.copy(ore = it.ore + state.oreRobots,
                clay = it.clay + state.clayRobots,
                obsidian = it.obsidian + state.obsidianRobots,
                geode = it.geode + state.geodeRobots)
        }
    }
}

data class State(val oreRobots: Int,
                 val clayRobots: Int,
                 val obsidianRobots: Int,
                 val geodeRobots: Int,
                 val ore: Int,
                 val clay: Int,
                 val obsidian: Int,
                 val geode: Int)

fun breakfast(input: List<String>): Int {
    val blueprints = input.map {
        val tokens = it.split(" ")
        val id = tokens[1].substringBefore(":").toInt()
        val oreRobotCost = tokens[6].toInt()
        val clayRobotCost = tokens[12].toInt()
        val obsidianRobotCost = tokens[18].toInt()
        val obsidianRobotCostClay = tokens[21].toInt()
        val geodeRobotCost = tokens[27].toInt()
        val geodeRobotCostObsidian = tokens[30].toInt()
        Blueprint(id, oreRobotCost, clayRobotCost, obsidianRobotCost, obsidianRobotCostClay, geodeRobotCost, geodeRobotCostObsidian)
    }
    println(blueprints)

    val s = State(1, 0, 0, 0, 0, 0, 0, 0)
    val b = blueprints.first()
    var allStates: List<State> = listOf(s)

    repeat(23) {
        println(it)
        println(allStates.size)
        allStates = allStates.flatMap { b.nextState(it) }.toSet().toList()
    }

    val maxB1 = allStates.maxOf { it.geode }
    println("geodes: $maxB1")
    TODO()
}

fun lunch(input: List<String>): Int {
    TODO()
}