fun main() {
    fun part1(input: String): String {
        run { 0L..Long.MAX_VALUE }
            .asSequence()
            .mapNotNull { code ->
                run { input + code }
                    .md5()
                    .takeIf { it.startsWith("00000") }
                    ?.let { it[5] }
            }
            .take(8)
            .joinToString("") { it.toString() }
            .run { return this }
    }

    fun part2(input: String): String {
        run { 0L..Long.MAX_VALUE }
            .asSequence()
            .scan(listOf<Char?>(null, null, null, null, null, null, null, null)) { password, code ->
                run { input + code }
                    .md5()
                    .takeIf { it.startsWith("00000") }
                    ?.let { hash ->
                        hash[5]
                            .digitToIntOrNull()
                            ?.takeIf { it in 0..7 }
                            ?.takeIf { password[it] == null }
                            ?.let { index -> password.mapIndexed { i, c -> if (i == index) hash[6] else c } }
                    }
                    ?: password
            }
            .first { password -> password.all { it != null } }
            .joinToString("") { it.toString() }
            .run { return this }
    }

    val testInput = "abc"
    check(part1(testInput) == "18f47a30")
    check(part2(testInput) == "05ace8e3")

    val input = "ojvtpuvg"
    part1(input).println()
    part2(input).println()
}
