fun main() {
    fun part1(input: List<String>): Int {
        val regex = """([a-z\-]+)-(\d+)\[([a-z]+)]""".toRegex()
        input
            .mapNotNull { line ->
                val (a, b, c) = regex.matchEntire(line)!!.destructured

                a
                    .split('-')
                    .flatMap { it.toList() }
                    .groupingBy { it }
                    .eachCount()
                    .toList()
                    .sortedWith(
                        compareByDescending<Pair<Char, Int>> { (_, count) -> count }
                            .then(compareBy { (c) -> c })
                    )
                    .take(5)
                    .map { (c) -> c }
                    .joinToString("") { it.toString() }
                    .let { if (it == c) b.toInt() else null }
            }
            .sum()
            .run { return this }
    }

    fun part2(input: List<String>): Int {
        val regex = """([a-z\-]+)-(\d+)\[([a-z]+)]""".toRegex()
        input
            .mapNotNull { line ->
                val (a, b, c) = regex.matchEntire(line)!!.destructured

                a
                    .split('-')
                    .flatMap { it.toList() }
                    .groupingBy { it }
                    .eachCount()
                    .toList()
                    .sortedWith(
                        compareByDescending<Pair<Char, Int>> { (_, count) -> count }
                            .then(compareBy { (c) -> c })
                    )
                    .take(5)
                    .map { (c) -> c }
                    .joinToString("") { it.toString() }
                    .let { if (it == c) Pair(b.toInt(), a) else null }
            }
            .map { (shift, name) ->
                name
                    .map { c ->
                        if (c.isLetter()) {
                            run { c.code - 'a'.code }
                                .let { it + shift }
                                .let { it % 26 }
                                .let { it + 'a'.code }
                                .toChar()
                        } else {
                            ' '
                        }
                    }
                    .joinToString("") { it.toString() }
                    .let { Pair(it, shift) }
            }
            .first { (name) -> name == "northpole object storage" }
            .let { (_, code) -> code }
            .run { return this }
    }

    val testInput1 = readInput("Day04_1_test")
    check(part1(testInput1) == 1514)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
