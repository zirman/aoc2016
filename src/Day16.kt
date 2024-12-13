fun main() {

    fun String.randomize(size: Int): String {
        var randomized = this
        while (randomized.length < size) {
            randomized = randomized + "0" + randomized.reversed().map { if (it == '0') '1' else '0' }.joinToString("")
        }
        return randomized.take(size)
    }

    fun String.checksum(): String {
        var checksum = this
        do {
            checksum = checksum.chunked(2).joinToString("") { if (it == "00" || it == "11") "1" else "0" }
        } while (checksum.length % 2 == 0)
        return checksum
    }

    fun String.part1(size: Int): String = randomize(size).checksum()

    check("10000".part1(20) == "01100")
    val input = "10010000000110000"
    printlnMeasureTimeMillis { input.part1(272).println() }
    printlnMeasureTimeMillis { input.part1(35651584).println() }
}
