package day06

import readInput

class Timer() {
    var nbFish = 0L

    fun addFish(nb: Long) {
        nbFish += nb
    }
}

fun main() {
    fun initialState(input: String): Array<Timer> {
        val timers = Array(9) {
            Timer()
        }
        input.split(',').forEach {
            timers[it.toInt()].addFish(1)
        }
        return timers
    }

    fun processDay(input: String, dayRange: IntRange): Long {
        val timers = initialState(input)
        for (day in dayRange) {
            val currentZeroTimer = (day - 1) % 9 // zero-based index
            val babies = timers[currentZeroTimer].nbFish
            // Add the "babies" at day6. We don't track the age, so we don't care where we add new fish :)
            timers[(currentZeroTimer + 7) % 9].addFish(babies)
        }
        return timers.map { it.nbFish }.sumOf { it }
    }

    fun part1(input: String): Long {
        return processDay(input, 1..80)
    }

    fun part2(input: String): Long {
        return processDay(input, 1..256)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = "3,4,3,1,2"
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26984457539L)

    val input = readInput("day06/input")
    println(part1(input[0]))
    println(part2(input[0]))
}

