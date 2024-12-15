typealias Input14 = String

fun main() {
    fun String.parse(): String {
        return this
    }

    fun String.next(): String = ".$this.".windowed(3).joinToString("") { kernel ->
        val left = kernel[0] == '^'
        val center = kernel[1] == '^'
        val right = kernel[2] == '^'
        if ((left && center && !right) ||
            (!left && center && right) ||
            (left && !center && !right) ||
            (!left && !center && right)
        ) "^" else "."
    }

    fun String.part1(count: Int): Int {
//        Its left and center tiles are traps, but its right tile is not.
//        Its center and right tiles are traps, but its left tile is not.
//        Only its left tile is a trap.
//        Only its right tile is a trap.
        var x = this
        var c = count { it == '.' }
        for (i in 1..<count) {
            x = x.next()
            c += x.count { it == '.' }
        }
        return c
    }

    fun part2(input: List<String>): Int {
        TODO()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = ".^^.^.^^^^"
    check(testInput.part1(10) == 38)
    val input = "^.^^^.^..^....^^....^^^^.^^.^...^^.^.^^.^^.^^..^.^...^.^..^.^^.^..^.....^^^.^.^^^..^^...^^^...^...^."
    printlnMeasureTimeMillis { input.part1(40).println() }
    printlnMeasureTimeMillis { input.part1(400000).println() }
}
