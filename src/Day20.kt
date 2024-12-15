fun main() {
    fun List<String>.part1(): UInt {
        var i = 0.toUInt()
        map {
            val (a, b) = it.split('-')
            a.toUInt()..b.toUInt()
        }
            .sortedBy { it.first }
            .forEach { range ->
                if (i < range.first) {
                    return@part1 i
                } else if (range.last + 1.toUInt() > i) {
                    i = range.last + 1.toUInt()
                }
            }
        throw IllegalStateException()
    }

    fun List<String>.part2(): Long {
        var i = 0.toLong()
        var count = 0.toLong()
        map {
            val (a, b) = it.split('-')
            a.toLong()..b.toLong()
        }
            .sortedBy { it.first }
            .forEach { range ->
                if (i < range.first) {
                    count += range.first - i
                    i = range.last + 1.toLong()
                } else if (i <= range.last) {
                    i = range.last + 1.toLong()
                }
            }
        if (i <= UInt.MAX_VALUE.toLong()) {
            count += UInt.MAX_VALUE.toLong() - i + 1.toLong()
        }
        return count
    }

    val testInput = """
        5-8
        0-2
        4-7
    """.trimIndent().split('\n')
    check(testInput.part1() == 3.toUInt())
    val input = readInput("Day20")
    printlnMeasureTimeMillis { input.part1().println() }
    printlnMeasureTimeMillis { input.part2().println() }
}
