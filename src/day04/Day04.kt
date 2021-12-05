package day04

import readInput

fun main() {

    class BingoBoard(board: Array<IntArray>) {
        // Keep a secondary table for easier/faster detection of bingo
        private val drawn = Array(5) {
            BooleanArray(5) { false }
        }
        private val boardNumbers = buildMap<Int, BingoCase> {
            board.forEachIndexed { row, ints ->
                ints.forEachIndexed { column, number ->
                    this[number] = BingoCase(row, column)
                }
            }
        }

        inner class BingoCase(val row: Int, val column: Int, var drawn: Boolean = false)

        /**
         * If the number is in this board, mark it as drawn and return if it makes a Bingo
         */
        fun draw(number: Int): Boolean {
            boardNumbers[number]?.let {
                it.drawn = true
                drawn[it.row][it.column] = true
                return isSolved(it.row, it.column)
            }
            return false
        }

        fun isSolved(row: Int, column: Int): Boolean {
            return drawn[row].all { it } or drawn.map { it[column] }.all { it }
        }

        fun score(): Int {
            return boardNumbers.filterValues { !it.drawn }.keys.sum()
        }
    }

    fun parseInput(input: List<String>): Pair<List<String>, MutableList<BingoBoard>> {
        val numbers = input[0].split(',')
        val inputIterator = input.listIterator(1)
        val boards = mutableListOf<BingoBoard>()
        for (line in inputIterator) {
            assert(line.isBlank())
            boards.add(BingoBoard(Array(5) {
                inputIterator.next().split(' ').filterNot(String::isBlank).map(String::toInt).toIntArray()
            }))
        }
        return Pair(numbers, boards)
    }

    fun part1(input: List<String>): Int {
        val (numbers, boards) = parseInput(input)
        numbers.map(String::toInt).forEach { number ->
            boards.forEach {
                val bingo = it.draw(number)
                if (bingo) return number * it.score()
            }
        }
        throw Exception("No Solution")
    }

    fun part2(input: List<String>): Int {
        val (numbers, boards) = parseInput(input)
        numbers.map(String::toInt).forEach { number ->
            var winner: BingoBoard? = null
            val iterator = boards.iterator()
            iterator.forEach {
                val bingo = it.draw(number)
                if (bingo) {
                    winner = it
                    iterator.remove()
                }
            }
            if (boards.isEmpty()) return number * winner!!.score()
        }

        throw IllegalStateException("No Solution")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day04/test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("day04/input")
    println(part1(input))
    println(part2(input))
}

