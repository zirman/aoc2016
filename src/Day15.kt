private data class Disk(val positions: Int, val startPosition: Int)

fun main() {
    val diskRegex = """^Disc #(\d+) has (\d+) positions; at time=0, it is at position (\d+)\.$""".toRegex()
    fun List<String>.parse(): List<Disk> = map {
        val (_, positions, startPosition) = diskRegex.matchEntire(it)!!.destructured
        Disk(positions.toInt(), startPosition.toInt())
    }

    fun List<Disk>.part1(): Int = (0..Int.MAX_VALUE)
        .first { startTime ->
            indices.all { d ->
                val disk = this[d]
                (disk.startPosition + startTime + d + 1) % disk.positions == 0
            }
        }

    fun List<Disk>.part2(): Int = (this + Disk(positions = 11, startPosition = 0)).part1()

    val testInput = """
        Disc #1 has 5 positions; at time=0, it is at position 4.
        Disc #2 has 2 positions; at time=0, it is at position 1.
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == 5)
    val input = """
        Disc #1 has 17 positions; at time=0, it is at position 15.
        Disc #2 has 3 positions; at time=0, it is at position 2.
        Disc #3 has 19 positions; at time=0, it is at position 4.
        Disc #4 has 13 positions; at time=0, it is at position 2.
        Disc #5 has 7 positions; at time=0, it is at position 2.
        Disc #6 has 5 positions; at time=0, it is at position 0.
    """.trimIndent().split('\n').parse()
    printlnMeasureTimeMillis { input.part1().println() }
    printlnMeasureTimeMillis { input.part2().println() }
}
