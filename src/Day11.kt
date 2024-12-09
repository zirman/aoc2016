private data class RTG(val generatorFloor: Int, val microchipFloor: Int)

private data class RTGState(
    val rtgs: List<RTG>, // keep sorted to reduce search space
    val elevator: Int,
)

private val rtgComparator = compareBy<RTG> { t -> t.generatorFloor }.thenBy { it.microchipFloor }

private fun RTGState.isValid(): Boolean {
    for (i in rtgs.indices) {
        if (rtgs[i].microchipFloor != rtgs[i].generatorFloor) {
            for (k in rtgs.indices) {
                if (i != k && rtgs[i].microchipFloor == rtgs[k].generatorFloor) {
                    return false
                }
            }
        }
    }
    return true
}

private fun RTGState.isFinished(): Boolean {
    return elevator == 4 && rtgs.all { rtg -> rtg.generatorFloor == 4 && rtg.microchipFloor == 4 }
}

private fun RTGState.nextStates(): Set<RTGState> {
    val movableRtgs = rtgs.flatMapIndexed { i, rtg ->
        buildList {
            if (rtg.generatorFloor == elevator) {
                add(Pair<Int, RTG.(Int) -> RTG>(i) { copy(generatorFloor = it) })
            }
            if (rtg.microchipFloor == elevator) {
                add(Pair<Int, RTG.(Int) -> RTG>(i) { copy(microchipFloor = it) })
            }
        }
    }
    return (movableRtgs.combinations(1) + movableRtgs.combinations(2))
        .flatMap { movingRtgs ->
            buildList {
                var newFloor = elevator + 1
                if (newFloor <= 4) {
                    RTGState(
                        rtgs = movingRtgs.fold(rtgs) { rtgs, (isotope, update) ->
                            rtgs.mapIndexed { i, rtg ->
                                if (isotope == i) {
                                    rtg.update(newFloor)
                                } else {
                                    rtg
                                }
                            }
                        }.sortedWith(rtgComparator),
                        elevator = newFloor,
                    )
                        .takeIf { it.isValid() }
                        ?.let { add(it) }
                }
                newFloor = elevator - 1
                if (newFloor >= 1) {
                    RTGState(
                        rtgs = movingRtgs.fold(rtgs) { rtgs, (isotope, update) ->
                            rtgs.mapIndexed { i, rtg ->
                                if (isotope == i) {
                                    rtg.update(newFloor)
                                } else {
                                    rtg
                                }
                            }
                        }.sortedWith(rtgComparator),
                        elevator = newFloor,
                    )
                        .takeIf { it.isValid() }
                        ?.let { add(it) }
                }
            }
        }
        .toSet()
}

fun main() {
    fun part1(input: List<String>): Int {
        val floorRegex = """The [a-z]+ floor contains (.*)\.""".toRegex(RegexOption.IGNORE_CASE)
        val generatorRegex = """([a-z]+) generator""".toRegex(RegexOption.IGNORE_CASE)
        val microchipRegex = """([a-z]+)-compatible microchip""".toRegex(RegexOption.IGNORE_CASE)
        val (generators, microchips) = input
            .foldIndexed(Pair(emptyMap<String, Int>(), emptyMap<String, Int>())) { floor, (a, b), line ->
                val (itemsStr) = floorRegex.matchEntire(line)!!.destructured
                if (itemsStr != "nothing relevant") {
                    var generators = a
                    var microchips = b
                    var match = microchipRegex.find(itemsStr)
                    while (match != null) {
                        val (microchipType) = match.destructured
                        microchips = microchips.plus(microchipType to floor + 1)
                        match = match.next()
                    }
                    match = generatorRegex.find(itemsStr)
                    while (match != null) {
                        val (generatorType) = match.destructured
                        generators = generators.plus(generatorType to floor + 1)
                        match = match.next()
                    }
                    Pair(generators, microchips)
                } else {
                    Pair(a, b)
                }
            }
        var states = listOf(
            RTGState(
                rtgs = generators
                    .map { (name, generatorFloor) ->
                        RTG(
                            generatorFloor = generatorFloor,
                            microchipFloor = microchips[name]!!
                        )
                    }
                    .sortedWith(rtgComparator),
                elevator = 1,
            ),
        )
        var visited = states.toMutableSet()
        var moves = 0
        while (true) {
            if (states.any { it.isFinished() }) {
                return moves
            }
            states = states.flatMap { state ->
                state.nextStates()
                    .filter { it !in visited }
                    .also {
                        visited += it
                    }
            }
            moves++
        }
    }

    fun part2(input: List<String>): Int {
        val floorRegex = """The [a-z]+ floor contains (.*)\.""".toRegex(RegexOption.IGNORE_CASE)
        val generatorRegex = """([a-z]+) generator""".toRegex(RegexOption.IGNORE_CASE)
        val microchipRegex = """([a-z]+)-compatible microchip""".toRegex(RegexOption.IGNORE_CASE)
        val (generators, microchips) = input
            .foldIndexed(Pair(emptyMap<String, Int>(), emptyMap<String, Int>())) { floor, (a, b), line ->
                val (itemsStr) = floorRegex.matchEntire(line)!!.destructured
                if (itemsStr != "nothing relevant") {
                    var generators = a
                    var microchips = b
                    var match2 = microchipRegex.find(itemsStr)
                    while (match2 != null) {
                        val (microchipType) = match2.destructured
                        microchips = microchips.plus(microchipType to floor + 1)
                        match2 = match2.next()
                    }
                    match2 = generatorRegex.find(itemsStr)
                    while (match2 != null) {
                        val (generatorType) = match2.destructured
                        generators = generators.plus(generatorType to floor + 1)
                        match2 = match2.next()
                    }
                    Pair(generators, microchips)
                } else {
                    Pair(a, b)
                }
            }
        val initialState = RTGState(
            rtgs = generators
                .map { (name, generatorFloor) ->
                    RTG(
                        generatorFloor = generatorFloor,
                        microchipFloor = microchips[name]!!
                    )
                }
                .plus(RTG(1, 1))
                .plus(RTG(1, 1))
                .sortedWith(rtgComparator),
            elevator = 1,
        )
        val visited = mutableSetOf<RTGState>()
        var states = listOf(initialState)
        var moves = 0
        while (true) {
            if (states.any { it.isFinished() }) {
                return moves
            }
            states = states.flatMap { state ->
                state.nextStates()
                    .filter { it !in visited && it.isValid() }
                    .also {
                        visited += it
                    }
            }
            moves++
        }
    }

    val testInput1 = readInput("Day11_1_test")
    check(part1(testInput1) == 11)
    val input = readInput("Day11")
    printlnMeasureTimeMillis { part1(input).println() }
    printlnMeasureTimeMillis { part2(input).println() }
}
