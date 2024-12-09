typealias Input12 = List<List<String>>

fun main() {
    fun List<String>.parse(): Input12 {
        return map { line -> line.split(' ') }
    }

    fun Input12.part1(): Int {
        var a = 0
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
                        else -> throw IllegalStateException()
                    }
                    when (inst[2]) {
                        "a" -> a = x
                        "b" -> b = x
                        "c" -> c = x
                        "d" -> d = x
                        else -> throw IllegalStateException()
                    }
                    i++
                }

                "inc" -> {
                    inst[1].toIntOrNull() ?: when (inst[1]) {
                        "a" -> a++
                        "b" -> b++
                        "c" -> c++
                        "d" -> d++
                        else -> throw IllegalStateException()
                    }
                    i++
                }

                "dec" -> {
                    inst[1].toIntOrNull() ?: when (inst[1]) {
                        "a" -> a--
                        "b" -> b--
                        "c" -> c--
                        "d" -> d--
                        else -> throw IllegalStateException()
                    }
                    i++
                }

                "jnz" -> {
                    val x = inst[1].toIntOrNull() ?: when (inst[1]) {
                        "a" -> a
                        "b" -> b
                        "c" -> c
                        "d" -> d
                        else -> throw IllegalStateException()
                    }
                    if (x != 0) {
                        i += inst[2].toInt()
                    } else {
                        i++
                    }
                }
            }
        }
        return a
    }

    fun Input12.part2(): Int {
        var a = 0
        var b = 0
        var c = 1
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
                        else -> throw IllegalStateException()
                    }
                    when (inst[2]) {
                        "a" -> a = x
                        "b" -> b = x
                        "c" -> c = x
                        "d" -> d = x
                        else -> throw IllegalStateException()
                    }
                    i++
                }

                "inc" -> {
                    inst[1].toIntOrNull() ?: when (inst[1]) {
                        "a" -> a++
                        "b" -> b++
                        "c" -> c++
                        "d" -> d++
                        else -> throw IllegalStateException()
                    }
                    i++
                }

                "dec" -> {
                    inst[1].toIntOrNull() ?: when (inst[1]) {
                        "a" -> a--
                        "b" -> b--
                        "c" -> c--
                        "d" -> d--
                        else -> throw IllegalStateException()
                    }
                    i++
                }

                "jnz" -> {
                    val x = inst[1].toIntOrNull() ?: when (inst[1]) {
                        "a" -> a
                        "b" -> b
                        "c" -> c
                        "d" -> d
                        else -> throw IllegalStateException()
                    }
                    if (x != 0) {
                        i += inst[2].toInt()
                    } else {
                        i++
                    }
                }
            }
        }
        return a
    }

    val testInput = """
        cpy 41 a
        inc a
        inc a
        dec a
        jnz a 2
        dec a
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == 42)
    val input = readInput("Day12").parse()
    printlnMeasureTimeMillis { input.part1().println() }
    printlnMeasureTimeMillis { input.part2().println() }
}
