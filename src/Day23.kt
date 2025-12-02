typealias Input23 = List<MutableList<String>>

fun main() {
    fun List<String>.parse(): Input23 {
        return map { line -> line.split(' ').toMutableList() }
    }

    fun Input23.part1(eggs: Int): Int {
        var a = eggs
        var b = 0
        var c = 0
        var d = 0

        var i = 0
        while (i in indices) {
            val inst = this[i]
            when (inst[0]) {
                "cpy" -> {
                    val x = inst[1].toIntOrNull() ?: when (inst[1]) {
                        "a" -> a
                        "b" -> b
                        "c" -> c
                        "d" -> d
                        else -> error("")
                    }
                    when (inst[2]) {
                        "a" -> a = x
                        "b" -> b = x
                        "c" -> c = x
                        "d" -> d = x
                        else -> {}
                    }
                    i++
                }

                "inc" -> {
                    inst[1].toIntOrNull() ?: when (inst[1]) {
                        "a" -> a++
                        "b" -> b++
                        "c" -> c++
                        "d" -> d++
                        else -> {}
                    }
                    i++
                }

                "dec" -> {
                    inst[1].toIntOrNull() ?: when (inst[1]) {
                        "a" -> a--
                        "b" -> b--
                        "c" -> c--
                        "d" -> d--
                        else -> {}
                    }
                    i++
                }

                "jnz" -> {
                    val x = inst[1].toIntOrNull() ?: when (inst[1]) {
                        "a" -> a
                        "b" -> b
                        "c" -> c
                        "d" -> d
                        else -> {}
                    }
                    if (x != 0) {
                        val q = inst[2].toIntOrNull() ?: when (inst[2]) {
                            "a" -> a
                            "b" -> b
                            "c" -> c
                            "d" -> d
                            else -> error("")
                        }
                        i += q
                    } else {
                        i++
                    }
                }

                "tgl" -> {
                    val k = i + (
                        inst[1].toIntOrNull() ?: when (inst[1]) {
                            "a" -> a
                            "b" -> b
                            "c" -> c
                            "d" -> d
                            else -> error("")
                        }
                        )
                    if (k in this.indices) {
                        val inst2 = this[k]
                        inst2[0] = when (inst2[0]) {
                            "cpy" -> "jnz"
                            "inc" -> "dec"
                            "dec" -> "inc"
                            "jnz" -> "cpy"
                            "tgl" -> "inc"
                            else -> error("")
                        }
                    }
                    i++
                }
            }
        }
        return a
    }

    val testInput = """
        cpy 2 a
        tgl a
        tgl a
        tgl a
        cpy 1 a
        dec a
        dec a
    """.trimIndent().split('\n').parse()
    check(testInput.part1(7) == 3)
    printlnMeasureTimeMillis { readInput("Day23").parse().part1(7).println() }
    printlnMeasureTimeMillis { readInput("Day23").parse().part1(12).println() }
}
