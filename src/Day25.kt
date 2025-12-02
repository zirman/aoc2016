typealias Input25 = List<List<String>>
typealias Result25 = Int

data class Data25(val a: Int, val b: Int, val c: Int, val d: Int, val i: Int)

fun main() {
    fun List<String>.parse(): Input25 {
        return map { it.split(Regex("""\s+""")) }
    }

    fun Input25.part1(): Result25 {
        fun Input25.exec(start: Int): Boolean {
            var a = start
            var b = 0
            var c = 0
            var d = 0
            var i = 0
            val visited = mutableSetOf<Data25>()
            var output = 0
            while (i in indices) {
                val data = Data25(a, b, c, d, i)
                if (data in visited) {
                    return true
                }
                visited += data
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
                            else -> error("")
                        }
                        i++
                    }

                    "inc" -> {
                        inst[1].toIntOrNull() ?: when (inst[1]) {
                            "a" -> a++
                            "b" -> b++
                            "c" -> c++
                            "d" -> d++
                            else -> error("")
                        }
                        i++
                    }

                    "dec" -> {
                        inst[1].toIntOrNull() ?: when (inst[1]) {
                            "a" -> a--
                            "b" -> b--
                            "c" -> c--
                            "d" -> d--
                            else -> error("")
                        }
                        i++
                    }

                    "jnz" -> {
                        val x = inst[1].toIntOrNull() ?: when (inst[1]) {
                            "a" -> a
                            "b" -> b
                            "c" -> c
                            "d" -> d
                            else -> error("")
                        }
                        if (x != 0) {
                            i += inst[2].toInt()
                        } else {
                            i++
                        }
                    }

                    "out" -> {
                        val x = inst[1].toIntOrNull() ?: when (inst[1]) {
                            "a" -> a
                            "b" -> b
                            "c" -> c
                            "d" -> d
                            else -> error("")
                        }
                        if (x != output) {
                            return false
                        }
                        output = (output + 1) % 2
                        i++
                    }
                }
            }
            return false
        }
        for (i in 0..Int.MAX_VALUE) {
            if (exec(i)) {
                return i
            }
        }
        error("")
    }

    val input = readInput("Day25")
    printlnMeasureTimeMillis { input.parse().part1().println() }
}
