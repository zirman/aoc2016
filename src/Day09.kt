fun main() {
    fun part1(input: String): Int {
        val regex = """([A-Z]*)\((\d+)x(\d+)\)""".toRegex()
        var i = 0
        var s = input
        while (true) {
            s = buildString {
                val match = regex.matchAt(s, i) ?: return s.length
                val (text, lengthStr, repeatStr) = match.destructured
                append(s.substring(0, i))
                append(text)
                val data = s.substring(match.range.last + 1, match.range.last + 1 + lengthStr.toInt())
                (1..repeatStr.toInt())
                    .forEach { append(data) }
                i += text.length + (data.length * repeatStr.toInt())
                append(s.substring(match.range.last + 1 + lengthStr.toInt(), s.length))
            }
        }
    }

    fun part2(input: String): Long {
        val repeatRegex = """([A-Z]*)\((\d+)x(\d+)\)([A-Z\dx()]+)""".toRegex()

        fun recur(s: String): Long {
            val match = repeatRegex.matchEntire(s)

            return if (match != null) {
                val (prefix, subLength, repeatCount, rest) = match.destructured
                val subString = rest.substring(0, subLength.toInt())
                val rest2 = rest.substring(subLength.toInt())
                prefix.length + (repeatCount.toLong() * recur(subString)) + recur(rest2)
            } else {
                s.length.toLong()
            }
        }

        return recur(input)
    }

    check(part1("ADVENT") == 6)
    check(part1("A(1x5)BC") == 7)
    check(part1("(3x3)XYZ") == 9)
    check(part1("A(2x2)BCD(2x2)EFG") == 11)
    check(part1("(6x1)(1x3)A") == 6)
    check(part1("X(8x2)(3x3)ABCY") == 18)

    check(part2("(3x3)XYZ") == 9L)
    check(part2("X(8x2)(3x3)ABCY") == 20L)
    check(part2("(27x12)(20x12)(13x14)(7x10)(1x12)A") == 241920L)
    check(part2("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN") == 445L)

    val input = readInput("Day09")
    part1(input[0]).println()
    part2(input[0]).println()
}
