fun main() {
    fun part1(input: List<String>): Int {
        val list = input.map { it.toList() }
        val nodes = list.flatMapIndexed { row, line ->
            line.mapIndexedNotNull { col, c ->
                if (c.isDigit()) {
                    Pos(row, col)
                } else {
                    null
                }
            }
        }
        val comparator = Comparator<Pos> { a, b -> b.row - a.row }
            .then { a, b -> b.col - a.col }
        val size = Size(width = list[0].size, height = list.size)
        val q = nodes
            .combinations(2)
            .associate { l ->
                val (from, dest) = l.sortedWith(comparator)
                val visited = mutableSetOf(from)
                tailrec fun Set<Pos>.distance(steps: Int): Int = if (dest in this) {
                    steps
                } else {
                    val x = flatMap { pos ->
                        pos
                            .next(size)
                            .filter { list[it.row][it.col] != '#' && it !in visited }
                    }.toSet()
                    visited += x
                    x.distance(steps + 1)
                }
                Pair(from, dest) to setOf(from).distance(0)
            }
        return nodes.permutations().filter { list[it[0].row][it[0].col] == '0' }.minOf { order ->
            order.windowed(2).sumOf { l ->
                val (a, b) = l.sortedWith(comparator)
                checkNotNull(q[Pair(a, b)])
            }
        }
    }

    fun part2(input: List<String>): Int {
        val list = input.map { it.toList() }
        val nodes = list.flatMapIndexed { row, line ->
            line.mapIndexedNotNull { col, c ->
                if (c.isDigit()) {
                    Pos(row, col)
                } else {
                    null
                }
            }
        }
        val comparator = Comparator<Pos> { a, b -> b.row - a.row }
            .then { a, b -> b.col - a.col }
        val size = Size(width = list[0].size, height = list.size)
        val q = nodes
            .combinations(2)
            .associate { l ->
                val (from, dest) = l.sortedWith(comparator)
                val visited = mutableSetOf(from)
                tailrec fun Set<Pos>.distance(steps: Int): Int = if (dest in this) {
                    steps
                } else {
                    val x = flatMap { pos ->
                        pos
                            .next(size)
                            .filter { list[it.row][it.col] != '#' && it !in visited }
                    }.toSet()
                    visited += x
                    x.distance(steps + 1)
                }
                Pair(from, dest) to setOf(from).distance(0)
            }
        val start = checkNotNull(nodes.find { list[it.row][it.col] == '0' })
        return nodes
            .permutations()
            .filter { it[0] == start }
            .minOf { order ->
                order.windowed(2).sumOf { l ->
                    val (a, b) = l.sortedWith(comparator)
                    checkNotNull(q[Pair(a, b)])
                } + run {
                    val (a, b) = listOf(start, order.last())
                        .sortedWith(comparator)
                    val x = q[Pair(a, b)]
                    checkNotNull(x)
                }
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day24_1_test")
    check(part1(testInput1) == 14)

    val input = readInput("Day24")
    printlnMeasureTimeMillis { part1(input).println() }
    printlnMeasureTimeMillis { part2(input).println() }
}
