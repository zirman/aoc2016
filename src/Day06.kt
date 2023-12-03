fun main() {
    fun part1(input: List<String>): String {
        input[0]
            .indices.map { i ->
                input
                    .map { line -> line[i] }
                    .groupingBy { it }
            }
            .map { groupings ->
                groupings
                    .eachCount()
                    .maxBy { (_, v) -> v }
                    .key
            }
            .joinToString("") { it.toString() }
            .run { return this }
    }

    fun part2(input: List<String>): String {
        input[0]
            .indices.map { i ->
                input
                    .map { line -> line[i] }
                    .groupingBy { it }
            }
            .map { groupings ->
                groupings
                    .eachCount()
                    .minBy { (_, v) -> v }
                    .key
            }
            .joinToString("") { it.toString() }
            .run { return this }
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day06_1_test")
    check(part1(testInput1) == "easter")
    check(part2(testInput1) == "advent")

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
