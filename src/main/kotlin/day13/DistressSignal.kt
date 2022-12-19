package day13

import java.io.File

// make the use of String as data structure a bit more intentional
typealias Packet = String

fun main() {
    val input = File("data/day13/sample.txt").readText().split("\n\n")
    breakfast(input)
    //lunch(input)
}

fun Packet.isInteger(): Boolean {
    return this.isNotEmpty() && this.first().isDigit()
}

fun Packet.isList(): Boolean {
    return this.isEmptyList() || this.first() == '['
}

fun Packet.isEmptyList(): Boolean {
    return this.isEmpty()
}

fun Packet.enlist(): Packet {
    return "[$this]"
}

fun comparePackets(left: Packet, right: Packet): Boolean {
    // at this point we get passed two packets including brackets.
    // if we zip up their tokens, we can compare them one by one
    // the tokens might be an integer, a packet, or an empty string.
    // if all of them are ordered, the packet is ordered.
    // if an integer needs to be compared to a list, it needs to be wrapped as a list first
    // if lists are compared it is ok for the left side to run empty, it's not for the right side
    // attention: an empty string is the token of an empty list.

    if (left.isEmptyList()) return true

    val pairs = left.tokens().zip(right.tokens())

    pairs.forEach {
        val l = it.first
        val r = it.second

        if (l.isInteger() && r.isInteger()) {
            if (r.toInt() < l.toInt()) {
                return false
            }
        }

        else if (l.isList() && r.isList()) {
            if (l.isEmptyList() && !r.isEmptyList()) return false
            val ordered = comparePackets(l, r)
            if (!ordered) return false
        }

        // we know one is an integer
        else if (l.isInteger()) {
            if (!r.isEmptyList()) {
                val ordered = comparePackets(l.enlist(), r)
                if (!ordered) return false
            }
        }
        // r must be an integer
        else {
            if (l.isEmptyList()) {
                return false
            }
            val ordered = comparePackets(l, r.enlist())
            if (!ordered) return false
        }
    }

    // at this point we only have to deal with different sized list
    if (left.tokens().size > right.tokens().size) return false

    return true
}

// splits at those commas which separate a floor-level list
fun Packet.tokens(): List<Packet> {
    // remove [ and ]
    val rest = this.drop(1).dropLast(1).toCharArray()
    var nested = 0
    rest.forEachIndexed { index, c ->
        if (c == '[') nested++
        if (c == ']') nested--
        if (nested == 0 && c == ',') rest[index] = '|'
    }
    val tokens = String(rest).split("|")
    return tokens
}

fun breakfast(input: List<String>): Int {

    fun debugPacketpair(packets: Pair<Packet, Packet>) {
        println("first: ${packets.first}")
        println("second: ${packets.second}")
        println("tokens first: ${packets.first.tokens().joinToString("|")}")
        println("tokens second: ${packets.second.tokens().joinToString("|")}")
        println("compared: ${comparePackets(packets.first, packets.second)}")
        println("---")
    }

    val pairs = input.map { it.split("\n") }.map { it.first() to it.last() }
    pairs.forEach {
        debugPacketpair(it)
    }
    val result = pairs.mapIndexed { index, pair ->
        val good = comparePackets(pair.first, pair.second)
        if (good) index + 1 else 0
    }.sum()
    println("result breakfast: $result")
    return result
}

fun lunch(input: List<String>): Int {
    return 0
}