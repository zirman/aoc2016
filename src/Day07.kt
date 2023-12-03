fun main() {
    val regex = """([a-z]+)(\[([a-z]+)])?""".toRegex()

    fun part1(input: List<String>): Int {
        fun String.isAbba(): Boolean {
            windowed(4, 1) { cs -> cs[0] != cs[1] && cs[2] == cs[1] && cs[3] == cs[0] }
                .any { it }
                .run { return this }
        }

        input
            .mapNotNull { line ->
                val matches = regex.findAll(line)

                if (
                    matches.any { match ->
                        val (part) = match.destructured
                        part.isAbba()
                    } && matches.all { match ->
                        val (_, _, hypernet) = match.destructured
                        hypernet.isAbba().not()
                    }
                ) {
                    line
                } else {
                    null
                }
            }
            .count()
            .run { return this }
    }

    fun part2(input: List<String>): Int {
        fun String.isAba(): List<CharSequence> {
            windowed(3, 1) { cs ->
                if (cs[0] != cs[1] && cs[0] == cs[2]) {
                    cs
                } else {
                    null
                }
            }
                .filterNotNull()
                .run { return this }
        }

        input
            .mapNotNull { line ->
                val matches = regex.findAll(line).toList()

                val abas = matches.flatMap { match ->
                    val (_, _, hypernet) = match.destructured
                    hypernet.isAba()
                }

                if (
                    matches.any { match ->
                        val (part) = match.destructured

                        part
                            .windowed(3, 1) { cs ->
                                abas.any { aba -> cs[0] == aba[1] && cs[1] == aba[0] && cs[2] == aba[1] }
                            }
                            .any { it }
                    }
                ) {
                    line
                } else {
                    null
                }
            }
            .count()
            .run { return this }
    }

    check(part1(listOf("abba[mnop]qrst")) == 1)
    check(part1(listOf("abcd[bddb]xyyx")) == 0)
    check(part1(listOf("aaaa[qwer]tyui")) == 0)
    check(part1(listOf("ioxxoj[asdfgh]zxcvbn")) == 1)

    check(part2(listOf("aba[bab]xyz")) == 1)
    check(part2(listOf("xyx[xyx]xyx")) == 0)
    check(part2(listOf("aaa[kek]eke")) == 1)
    check(part2(listOf("zazbz[bzb]cdb")) == 1)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
