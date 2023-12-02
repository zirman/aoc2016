fun main() {

    fun part1(input: List<String>): Int {
        val space = """\s+""".toRegex()

        input
            .map { line ->
                val (a, b, c) = line.trim().split(space)
                Triple(a.toInt(), b.toInt(), c.toInt())
            }
            .count { (a, b, c) -> a + b > c && b + c > a && c + a > b }
            .run { return this }
    }

    fun part2(input: List<String>): Int {
        val space = """\s+""".toRegex()

        input
            .chunked(3)
            .flatMap { lines ->
                val (l1, l2, l3) = lines
                val (a, d, g) = l1.trim().split(space)
                val (b, e, h) = l2.trim().split(space)
                val (c, f, i) = l3.trim().split(space)
                listOf(
                    Triple(a.toInt(), b.toInt(), c.toInt()),
                    Triple(d.toInt(), e.toInt(), f.toInt()),
                    Triple(g.toInt(), h.toInt(), i.toInt()),
                )
            }
            .count { (a, b, c) -> a + b > c && b + c > a && c + a > b }
            .run { return this }
    }

    check(part1(listOf("5 10 25")) == 0)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
