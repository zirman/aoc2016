import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): String {
        val keyPad = listOf(
            "123",
            "456",
            "789",
        )

        var x = 1
        var y = 1
        return input
            .map { line ->
                line.forEach { move ->
                    when (move) {
                        'U' -> {
                            y = max(y - 1, 0)
                        }

                        'D' -> {
                            y = min(y + 1, 2)
                        }

                        'L' -> {
                            x = max(x - 1, 0)
                        }

                        'R' -> {
                            x = min(x + 1, 2)
                        }
                    }
                }
                keyPad[y][x]
            }
            .joinToString("") { it.toString() }
    }

    fun part2(input: List<String>): String {
        val keyPad = listOf(
            "  1",
            " 234",
            "56789",
            " ABC",
            "  D",
        )

        var x = 0
        var y = 2
        return input
            .map { line ->
                line.forEach { move ->
                    when (move) {
                        'U' -> {
                            if (keyPad.getOrNull(y - 1)?.getOrNull(x)?.isLetterOrDigit() == true) {
                                y -= 1
                            }
                        }

                        'D' -> {
                            if (keyPad.getOrNull(y + 1)?.getOrNull(x)?.isLetterOrDigit() == true) {
                                y += 1
                            }
                        }

                        'L' -> {
                            if (keyPad[y].getOrNull(x - 1)?.isLetterOrDigit() == true) {
                                x -= 1
                            }
                        }

                        'R' -> {
                            if (keyPad[y].getOrNull(x + 1)?.isLetterOrDigit() == true) {
                                x += 1
                            }
                        }
                    }
                }
                keyPad[y][x]
            }
            .joinToString("") { it.toString() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day02_1_test")
    check(part1(testInput1) == "1985")

    check(part2(testInput1) == "5DB3")

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
