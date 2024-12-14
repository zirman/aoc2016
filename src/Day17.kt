import kotlin.system.measureTimeMillis

fun main() {
    fun Char.isOpen(): Boolean = this in "bcdef"

    fun String.part1(): String {
        val passcode = this
        var current = listOf(Pair("", Pos(0, 0)))
        val size = Size(4, 4)
        val dest = Pos(3, 3)
        while (true) {
            current = current.flatMap { (path, pos) ->
                buildList {
                    val (up, down, left, right) = (passcode + path).md5().take(4).map { it.isOpen() }
                    if (up) {
                        val n = pos.goNorth()
                        if (n in size) {
                            val pathPrime = path + 'U'
                            if (n == dest) return@part1 pathPrime
                            add(Pair(pathPrime, n))
                        }
                    }
                    if (down) {
                        val s = pos.goSouth()
                        if (s in size) {
                            val pathPrime = path + 'D'
                            if (s == dest) return@part1 pathPrime
                            add(Pair(pathPrime, s))
                        }
                    }
                    if (left) {
                        val w = pos.goWest()
                        if (w in size) {
                            val pathPrime = path + 'L'
                            if (w == dest) return@part1 pathPrime
                            add(Pair(pathPrime, w))
                        }
                    }
                    if (right) {
                        val e = pos.goEast()
                        if (e in size) {
                            val pathPrime = path + 'R'
                            if (e == dest) return@part1 pathPrime
                            add(Pair(pathPrime, e))
                        }
                    }
                }
            }
        }
    }

    fun String.part2(): Int {
        val passcode = this
        var current = listOf(Pair("", Pos(0, 0)))
        val size = Size(4, 4)
        val dest = Pos(3, 3)
        var steps = 0
        while (current.isNotEmpty()) {

            current = current.flatMap { (path, pos) ->
                buildList {
                    val (up, down, left, right) = (passcode + path).md5().take(4).map { it.isOpen() }
                    if (up) {
                        val n = pos.goNorth()
                        if (n in size) {
                            if (n == dest) {
                                steps = steps.coerceAtLeast(path.length + 1)
                            } else {
                                add(Pair(path + 'U', n))
                            }
                        }
                    }
                    if (down) {
                        val s = pos.goSouth()
                        if (s in size) {
                            if (s == dest) {
                                steps = steps.coerceAtLeast(path.length + 1)
                            } else {
                                add(Pair(path + 'D', s))
                            }
                        }
                    }
                    if (left) {
                        val w = pos.goWest()
                        if (w in size) {
                            if (w == dest) {
                                steps = steps.coerceAtLeast(path.length + 1)
                            } else {
                                add(Pair(path + 'L', w))
                            }
                        }
                    }
                    if (right) {
                        val e = pos.goEast()
                        if (e in size) {
                            if (e == dest) {
                                steps = steps.coerceAtLeast(path.length + 1)
                            } else {
                                add(Pair(path + 'R', e))
                            }
                        }
                    }
                }
            }
        }
        return steps
    }
    check("ihgpwlah".part1() == "DDRRRD")
    check("kglvqrro".part1() == "DDUDRLRRUDRD")
    check("ulqzkmiv".part1() == "DRURDRUDDLLDLUURRDULRLDUUDDDRR")

    measureTimeMillis { "pgflpeqp".part1().println() }.also { println("time: $it") }

    check("ihgpwlah".part2() == 370)
    check("kglvqrro".part2() == 492)
    check("ulqzkmiv".part2() == 830)

    measureTimeMillis { "pgflpeqp".part2().println() }.also { println("time: $it") }
}
