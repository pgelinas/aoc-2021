package day01

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        var previous = Int.MAX_VALUE // ensure no previous measurement
        input.forEach { depth: String ->
            val current = depth.toInt()
            if (current > previous) sum++
            previous = current
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        val window = ArrayDeque(input.slice(0..2).map(String::toInt))
        var previous = window.sum()
        val it = input.listIterator(3)
        it.forEachRemaining {
            window.removeFirst()
            window.add(it.toInt())
            val current = window.sum()
            if (current > previous) sum++
            previous = current
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = listOf("199", "200", "208", "210", "200", "207", "240", "269", "260", "263")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("input")
    println(part1(input))
    println(part2(input))
}

