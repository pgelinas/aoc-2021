package day02

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var horizontal = 0
        var depth = 0
        input.forEach {
            val command = it.split(' ')
            val operand = command[1].toInt()
            when (command[0]) {
                "forward" -> horizontal += operand
                "up" -> depth -= operand
                "down" -> depth += operand
            }
        }
        return horizontal * depth
    }

    fun part2(input: List<String>): Int {
        var horizontal = 0
        var depth = 0
        var aim = 0
        input.forEach {
            val command = it.split(' ')
            val operand = command[1].toInt()
            when (command[0]) {
                "forward" -> {
                    horizontal += operand
                    depth += aim * operand
                }
                "up" -> aim -= operand
                "down" -> aim += operand
            }
        }
        return horizontal * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("input")
    println(part1(input))
    println(part2(input))
}

