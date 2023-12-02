import kotlin.math.abs

enum class Direction {
    North,
    East,
    South,
    West;

    fun right(): Direction {
        return when (this) {
            North -> East
            East -> South
            South -> West
            West -> North
        }
    }

    fun left(): Direction {
        return when (this) {
            North -> West
            East -> North
            South -> East
            West -> South
        }
    }
}

fun main() {
    fun part1(input: String): Int {
        var direction = Direction.North
        var x = 0
        var y = 0
        input.split(", ").forEach { move ->
            direction = when (move[0]) {
                'R' -> direction.right()
                'L' -> direction.left()
                else -> TODO()
            }

            when (direction) {
                Direction.North -> y += move.drop(1).toInt()
                Direction.East -> x += move.drop(1).toInt()
                Direction.South -> y -= move.drop(1).toInt()
                Direction.West -> x -= move.drop(1).toInt()
            }
        }
        return abs(x) + abs(y)
    }

    fun part2(input: String): Int {
        val visited = mutableSetOf<Pair<Int, Int>>(Pair(0, 0))
        var direction = Direction.North
        var x = 0
        var y = 0
        input.split(", ").forEach { move ->
            direction = when (move[0]) {
                'R' -> direction.right()
                'L' -> direction.left()
                else -> TODO()
            }

            when (direction) {
                Direction.North -> {
                    for (q in 1..move.drop(1).toInt()) {
                        y += 1
                        if (visited.contains(Pair(x, y))) {
                            return abs(x) + abs(y)
                        }
                        visited.add(Pair(x, y))
                    }
                }
                Direction.East -> {
                    for (q in 1..move.drop(1).toInt()) {
                        x += 1
                        if (visited.contains(Pair(x, y))) {
                            return abs(x) + abs(y)
                        }
                        visited.add(Pair(x, y))
                    }
                }
                Direction.South -> {
                    for (q in 1..move.drop(1).toInt()) {
                        y -= 1
                        if (visited.contains(Pair(x, y))) {
                            return abs(x) + abs(y)
                        }
                        visited.add(Pair(x, y))
                    }
                }
                Direction.West -> {
                    for (q in 1..move.drop(1).toInt()) {
                        x -= 1
                        if (visited.contains(Pair(x, y))) {
                            return abs(x) + abs(y)
                        }
                        visited.add(Pair(x, y))
                    }
                }
            }
        }
        return abs(x) + abs(y)
    }

    val testInput1 = readInput("Day01_1_test")
    check(part1("R2, L3") == 5)
    check(part1("R2, R2, R2") == 2)
    check(part1("R5, L5, R5, R3") == 12)

    val testInput2 = readInput("Day01_2_test")
    check(part2("R8, R4, R4, R8") == 4)

    val input = readInput("Day01")
    part1(input.first()).println()
    part2(input.first()).println()
}
