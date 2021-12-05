package day05

import com.google.common.collect.HashMultiset
import readInput

data class Point(val x: Int, val y: Int) {
    companion object {
        operator fun invoke(definition: String): Point {
            val coordinates = definition.split(',').map(String::toInt)
            return Point(coordinates[0], coordinates[1])
        }
    }

    fun next(distanceX: Int, distanceY: Int): Point {
        val newX = if(distanceX == 0) x else if(distanceX < 0) x.inc() else x.dec()
        val newY = if(distanceY == 0) y else if(distanceY < 0) y.inc() else y.dec()
        return Point(newX, newY)
    }
}

data class Line(val start: Point, val end: Point) : Iterable<Point> {
    companion object {
        operator fun invoke(definition: String): Line {
            val coordinates = definition.split("->").map(String::trim)
            return Line(Point(coordinates[0]), Point(coordinates[1]))
        }
    }

    fun isHorizontal() = start.y == end.y
    fun isVertical() = start.x == end.x

    override operator fun iterator() = object : Iterator<Point> {
        var current = start
        val distanceX = start.x - end.x
        val distanceY = start.y - end.y
        var hasNext = true

        override fun hasNext() = hasNext

        override fun next(): Point {
            require(hasNext())
            if (current == end){
                hasNext = false
                return end
            }
            val next = current
            current = current.next(distanceX, distanceY)
            return next
        }
    }
}

fun main() {

    fun findOverlappingPoints(lines: List<Line>): Int {
        val points = HashMultiset.create<Point>()
        lines.forEach { line ->
            line.forEach {
                points.add(it)
            }
        }
        return points.entrySet().filter { it.count >= 2 }.size
    }

    fun part1(input: List<String>): Int {
        val lines = input.map { Line(it) }.filter { it.isHorizontal() or it.isVertical() }
        return findOverlappingPoints(lines)
    }

    fun part2(input: List<String>): Int {
        val lines = input.map { Line(it) }
        return findOverlappingPoints(lines)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day05/test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("day05/input")
//    println(part1(input))
    println(part2(input))
}

