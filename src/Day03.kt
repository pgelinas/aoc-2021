fun main() {

    fun part1(input: List<String>): UInt {
        val nbBits = input[0].length
        val bits = IntArray(nbBits)
        // Loop over the input once, grabbing the number of 1 at each bit positions
        input.forEach {
            val data = it.toInt(2)
            bits.forEachIndexed { index, bit ->
                // Check the bit at the index position
                if (data and (1 shl index) != 0) {
                    bits[index] = bit.inc()
                }
            }
        }

        var gamma = 0u
        var epsilon = 0u
        bits.forEachIndexed { index, bit ->
            val mask = 1u shl index
            if (bit > input.size / 2) {
                gamma += mask
            } else {
                // There's surely a better bit operation to calculate epsilon, but this works
                epsilon += mask
            }
        }
        return epsilon * gamma
    }

    fun keepCommonBitsAt(
        bitIndex: Int,
        input: List<UInt>,
        mostCommon: Boolean
    ): MutableList<UInt> {
        val ones = mutableListOf<UInt>()
        val zeroes = mutableListOf<UInt>()
        // Go through the input, separating values based on the bitIndex
        input.forEach {
            if (it and (1u shl bitIndex) != 0u) {
                ones.add(it)
            } else {
                zeroes.add(it)
            }
        }
        // Return the collection with the most common or least common.
        return if (mostCommon) {
            if (ones.size >= zeroes.size) ones else zeroes
        } else
            if (ones.size < zeroes.size) ones else zeroes
    }

    fun part2(input: List<String>): UInt {
        val nbBits = input[0].length - 1
        val initial = input.map { it.toUInt(2) }
        var oxygen = initial; var co2Scrubber = initial

        for (bitIndex in nbBits downTo 0) {
            if (oxygen.size > 1) {
                oxygen = keepCommonBitsAt(bitIndex, oxygen, true)
            }
            if (co2Scrubber.size > 1) {
                co2Scrubber = keepCommonBitsAt(bitIndex, co2Scrubber, false)
            }
        }

        val oxygenRating = oxygen.first()
        val co2Rating = co2Scrubber.first()

        return oxygenRating * co2Rating
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    println(part1(testInput))
    println(part2(testInput))
    part2(testInput)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

