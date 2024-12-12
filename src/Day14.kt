fun main() {
    fun part1(input: String): Int {
        return sequence {
            for (i in 0..Int.MAX_VALUE) {
                yield("$input$i".md5())
            }
        }
            .windowed(1000)
            .withIndex()
            .filter { (_, hashes) ->
                hashes.first()
                    .windowed(3)
                    .find { it.all { char -> char == it[0] } }
                    ?.let { triple ->
                        val pent = (1..5).joinToString("") { "${triple[0]}" }
                        hashes.drop(1).any { it.contains(pent) }
                    } == true
            }
            .take(64)
            .last()
            .index
    }

    fun part2(input: String): Int {
        return sequence {
            for (i in 0..Int.MAX_VALUE) {
                var x = "$input$i"
                repeat(2017) {
                    x = x.md5()
                }
                yield(x)
            }
        }
            .windowed(1000)
            .withIndex()
            .filter { (_, hashes) ->
                hashes.first()
                    .windowed(3)
                    .find { it.all { char -> char == it[0] } }
                    ?.let { triple ->
                        val pent = (1..5).joinToString("") { "${triple[0]}" }
                        hashes.drop(1).any { it.contains(pent) }
                    } == true
            }
            .take(64)
            .last()
            .index
    }
    check(part1("abc") == 22728)
    printlnMeasureTimeMillis { part1("ngcjuoqr").println() }
    check(part2("abc") == 22551)
    printlnMeasureTimeMillis { part2("ngcjuoqr").println() }
}
