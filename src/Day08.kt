fun main() {
    fun part1(input: List<String>): Int {
        val rectRegex = """rect (\d+)x(\d+)""".toRegex()
        val rotateRowRegex = """rotate row y=(\d+) by (\d+)""".toRegex()
        val rotateColumnRegex = """rotate column x=(\d+) by (\d+)""".toRegex()
        val pixels = (1..6).map { (1..50).map { false }.toMutableList() }

        input.forEach { line ->
            when {
                line.startsWith("rect") -> {
                    val (a, b) = rectRegex.matchEntire(line)!!.destructured
                    for (rowIndex in 0..<b.toInt()) {
                        for (columnIndex in 0..<a.toInt()) {
                            pixels[rowIndex][columnIndex] = true
                        }
                    }
                }

                line.startsWith("rotate row") -> {
                    val (rowIndexString, byString) = rotateRowRegex.matchEntire(line)!!.destructured
                    val rowIndex = rowIndexString.toInt()
                    val row = pixels[rowIndex]
                    val shift = (-byString.toInt()).mod(50)

                    row.indices.map { columnIndex -> row[(columnIndex + shift).mod(50)] }
                        .forEachIndexed { columnIndex, pixel ->
                            row[columnIndex] = pixel
                        }
                }

                line.startsWith("rotate column") -> {
                    val (columnIndexString, byString) = rotateColumnRegex.matchEntire(line)!!.destructured
                    val columnIndex = columnIndexString.toInt()
                    val shift = (-byString.toInt()).mod(6)

                    pixels.indices.map { rowIndex -> pixels[(rowIndex + shift).mod(6)][columnIndex] }
                        .forEachIndexed { rowIndex, pixel ->
                            pixels[rowIndex][columnIndex] = pixel
                        }
                }
            }
        }

        pixels
            .joinToString("\n") { line ->
                line.joinToString("") { if (it) "#" else "." }
            }
            .also { println(it) }

        return pixels.sumOf { row -> row.count { it } }
    }

    check(
        part1(
            listOf(
                "rect 3x2",
                "rotate column x=1 by 1",
                "rect 3x2",
            )
        ) == 7
    )

    val input = readInput("Day08")
    part1(input).println()
}
