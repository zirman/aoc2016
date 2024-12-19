fun main() {
    val swapPositionRegex = """^swap position (\d+) with position (\d+)$""".toRegex()
    val swapLetterRegex = """^swap letter ([a-z]) with letter ([a-z])$""".toRegex()
    val rotateLeftRegex = """^rotate left (\d+) steps?$""".toRegex()
    val rotateRightRegex = """^rotate right (\d+) steps?$""".toRegex()
    val rotatePositionRegex = """^rotate based on position of letter ([a-z])$""".toRegex()
    val reverseRegex = """^reverse positions (\d+) through (\d+)$""".toRegex()
    val moveRegex = """^move position (\d+) to position (\d+)$""".toRegex()

    fun List<String>.part1(password: String): String {
        var password = password.toMutableList()
        for (line in this) {
            var match = swapPositionRegex.matchEntire(line)
            if (match != null) {
                val (p1Str, p2Str) = match.destructured
                val p1 = p1Str.toInt()
                val p2 = p2Str.toInt()
                val c = password[p2]
                password[p2] = password[p1]
                password[p1] = c
                continue
            }
            match = swapLetterRegex.matchEntire(line)
            if (match != null) {
                val (l1Str, l2Str) = match.destructured
                for (i in password.indices) {
                    if (password[i] == l1Str[0]) {
                        password[i] = l2Str[0]
                    } else if (password[i] == l2Str[0]) {
                        password[i] = l1Str[0]
                    }
                }
                continue
            }
            match = rotateLeftRegex.matchEntire(line)
            if (match != null) {
                val (pStr) = match.destructured
                password = sequence {
                    while (true) {
                        yieldAll(password)
                    }
                }.drop(pStr.toInt()).take(password.size).toMutableList()
                continue
            }
            match = rotateRightRegex.matchEntire(line)
            if (match != null) {
                val (pStr) = match.destructured
                password = sequence {
                    val p = password
                    while (true) {
                        yieldAll(p)
                    }
                }.drop(pStr.toInt().unaryMinus().mod(password.size)).take(password.size).toMutableList()
                continue
            }
            match = rotatePositionRegex.matchEntire(line)
            if (match != null) {
                val (lStr) = match.destructured
                var r = password.indexOf(lStr[0])
                r += if (r >= 4) 2 else 1
                password = sequence {
                    val p = password
                    while (true) {
                        yieldAll(p)
                    }
                }.drop(r.unaryMinus().mod(password.size)).take(password.size).toMutableList()
                continue
            }
            match = reverseRegex.matchEntire(line)
            if (match != null) {
                val (p1Str, p2Str) = match.destructured
                val p1 = p1Str.toInt()
                val p2 = p2Str.toInt()
                password = buildList {
                    addAll(password.subList(0, p1))
                    addAll(password.subList(p1, p2 + 1).asReversed())
                    addAll(password.subList(p2 + 1, password.size))
                }.toMutableList()
                continue
            }
            match = moveRegex.matchEntire(line)
            if (match != null) {
                val (p1Str, p2Str) = match.destructured
                val p1 = p1Str.toInt()
                val p2 = p2Str.toInt()
                val c = password.removeAt(p1)
                password.add(p2, c)
                continue
            }
            throw IllegalStateException()
        }
        return password.joinToString("")
    }

    fun List<String>.part2(password: String): String {
        fun List<Char>.foo(): Sequence<Pair<Int, List<Char>>> = sequence {
            for (i in 1..indices.last + 4) {
                yield(
                    Pair(
                        i,
                        sequence {
                            while (true) {
                                yieldAll(this@foo)
                            }
                        }.drop(i.mod(size)).take(size).toList(),
                    )
                )
            }
        }
        var password = password.toMutableList()
        for (line in asReversed()) {
            var match2 = swapPositionRegex.matchEntire(line)
            if (match2 != null) {
                val (p1Str, p2Str) = match2.destructured
                val p1 = p1Str.toInt()
                val p2 = p2Str.toInt()
                val c = password[p1]
                password[p1] = password[p2]
                password[p2] = c
                continue
            }
            match2 = swapLetterRegex.matchEntire(line)
            if (match2 != null) {
                val (l1Str, l2Str) = match2.destructured
                for (i in password.indices) {
                    if (password[i] == l1Str[0]) {
                        password[i] = l2Str[0]
                    } else if (password[i] == l2Str[0]) {
                        password[i] = l1Str[0]
                    }
                }
                continue
            }
            match2 = rotateLeftRegex.matchEntire(line)
            if (match2 != null) {
                val (pStr) = match2.destructured
                password = sequence {
                    val p = password
                    while (true) {
                        yieldAll(p)
                    }
                }.drop(pStr.toInt().unaryMinus().mod(password.size)).take(password.size).toMutableList()
                continue
            }
            match2 = rotateRightRegex.matchEntire(line)
            if (match2 != null) {
                val (pStr) = match2.destructured
                password = sequence {
                    while (true) {
                        yieldAll(password)
                    }
                }.drop(pStr.toInt()).take(password.size).toMutableList()
                continue
            }
            match2 = rotatePositionRegex.matchEntire(line)
            if (match2 != null) {
                val (lStr) = match2.destructured
                password = password.foo().first { (index, it) ->
                    var r = it.indexOf(lStr[0])
                    r += if (r >= 4) 2 else 1
                    r == index
                }.second.toMutableList()
                continue
            }
            match2 = reverseRegex.matchEntire(line)
            if (match2 != null) {
                val (p1Str, p2Str) = match2.destructured
                val p1 = p1Str.toInt()
                val p2 = p2Str.toInt()
                password = buildList {
                    addAll(password.subList(0, p1))
                    addAll(password.subList(p1, p2 + 1).asReversed())
                    addAll(password.subList(p2 + 1, password.size))
                }.toMutableList()
                continue
            }
            match2 = moveRegex.matchEntire(line)
            if (match2 != null) {
                val (p1Str, p2Str) = match2.destructured
                val p1 = p1Str.toInt()
                val p2 = p2Str.toInt()
                val c = password.removeAt(p2)
                password.add(p1, c)
                continue
            }
            throw IllegalStateException()
        }
        return password.joinToString("")
    }

    val testInput = """
        swap position 4 with position 0
        swap letter d with letter b
        reverse positions 0 through 4
        rotate left 1 step
        move position 1 to position 4
        move position 3 to position 0
        rotate based on position of letter b
        rotate based on position of letter d
    """.trimIndent().split('\n')
    check(testInput.part1("abcde") == "decab")
    val input = readInput("Day21")
    printlnMeasureTimeMillis { println("part1: ${input.part1("abcdefgh")}") }
    check(input.part1(input.part2("fbgdceah")) == "fbgdceah")
    printlnMeasureTimeMillis { println("part2: ${input.part2("fbgdceah")}") }
}
