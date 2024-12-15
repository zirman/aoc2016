fun main() {
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
        var x = this
        var c = count { it == '.' }
        for (i in 1..<count) {
            x = x.next()
            c += x.count { it == '.' }
        }
        return c
    }

    val testInput = ".^^.^.^^^^"
    check(testInput.part1(10) == 38)
    val input = "^.^^^.^..^....^^....^^^^.^^.^...^^.^.^^.^^.^^..^.^...^.^..^.^^.^..^.....^^^.^.^^^..^^...^^^...^...^."
    printlnMeasureTimeMillis { input.part1(40).println() }
    printlnMeasureTimeMillis { input.part1(400000).println() }
}
