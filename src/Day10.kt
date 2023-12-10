fun main() {
    fun part1(input: List<String>): String {
        val valueRegex = """value (\d+) goes to bot (\d+)""".toRegex()
        val botRegex = """bot (\d+) gives low to (bot|output) (\d+) and high to (bot|output) (\d+)""".toRegex()
        val (initialValueLines, botsInstructionLines) = input.partition { valueRegex.matches(it) }

        val bots = initialValueLines
            .map { line -> valueRegex.matchEntire(line)!!.destructured }
            .groupingBy { (_, botId) -> botId }
            .aggregate { _, accumulator: MutableList<Int>?, (element), _ ->
                (accumulator ?: mutableListOf()).apply { add(element.toInt()) }
            }
            .toMutableMap()

        val insts = botsInstructionLines.associate { line ->
            val (botId, low, lowId, high, highId) = botRegex.matchEntire(line)!!.destructured
            Pair(botId, listOf(low, lowId, high, highId))
        }

        val outputs = mutableMapOf<String, MutableList<Int>>()
        println(insts)

        var didStuff = true
        while (didStuff) {
            didStuff = false
            insts.forEach { (botId, inst) ->
                if ((bots[botId]?.size ?: 0) > 2) throw IllegalStateException()

                if (bots[botId]?.size == 2) {
                    val (lowType, lowId, highType, highId) = inst

                    if (
                        (lowType != "bot" || bots[lowId].orEmpty().size < 2) &&
                        (highType != "bot" || bots[highId].orEmpty().size < 2)
                    ) {
                        didStuff = true
                        val low = bots[botId]!!.min()
                        val high = bots[botId]!!.max()
                        bots[botId]!!.clear()

                        if (low == 17 && high == 61) {
                            return botId
                        }

                        when (lowType) {
                            "output" -> {
                                outputs.getOrPut(lowId) { mutableListOf() }.add(low)
                            }

                            "bot" -> {
                                bots.getOrPut(lowId) { mutableListOf() }.add(low)
                            }
                        }
                        when (highType) {
                            "output" -> {
                                outputs.getOrPut(highId) { mutableListOf() }.add(high)
                            }

                            "bot" -> {
                                bots.getOrPut(highId) { mutableListOf() }.add(high)
                            }
                        }
                    }
                }
            }
        }
        throw IllegalStateException()
    }

    fun part2(input: List<String>): Int {
        val valueRegex = """value (\d+) goes to bot (\d+)""".toRegex()
        val botRegex = """bot (\d+) gives low to (bot|output) (\d+) and high to (bot|output) (\d+)""".toRegex()
        val (initialValueLines, botsInstructionLines) = input.partition { valueRegex.matches(it) }

        val bots = initialValueLines
            .map { line -> valueRegex.matchEntire(line)!!.destructured }
            .groupingBy { (_, botId) -> botId }
            .aggregate { _, accumulator: MutableList<Int>?, (element), _ ->
                (accumulator ?: mutableListOf()).apply { add(element.toInt()) }
            }
            .toMutableMap()

        val insts = botsInstructionLines.associate { line ->
            val (botId, low, lowId, high, highId) = botRegex.matchEntire(line)!!.destructured
            Pair(botId, listOf(low, lowId, high, highId))
        }

        val outputs = mutableMapOf<String, MutableList<Int>>()
        println(insts)

        var didStuff = true
        while (didStuff) {
            didStuff = false
            insts.forEach { (botId, inst) ->
                if ((bots[botId]?.size ?: 0) > 2) throw IllegalStateException()

                if (bots[botId]?.size == 2) {
                    val (lowType, lowId, highType, highId) = inst

                    if (
                        (lowType != "bot" || bots[lowId].orEmpty().size < 2) &&
                        (highType != "bot" || bots[highId].orEmpty().size < 2)
                    ) {
                        didStuff = true
                        val low = bots[botId]!!.min()
                        val high = bots[botId]!!.max()
                        bots[botId]!!.clear()

                        when (lowType) {
                            "output" -> {
                                outputs.getOrPut(lowId) { mutableListOf() }.add(low)
                            }

                            "bot" -> {
                                bots.getOrPut(lowId) { mutableListOf() }.add(low)
                            }
                        }
                        when (highType) {
                            "output" -> {
                                outputs.getOrPut(highId) { mutableListOf() }.add(high)
                            }

                            "bot" -> {
                                bots.getOrPut(highId) { mutableListOf() }.add(high)
                            }
                        }
                    }
                }
            }
        }
        return outputs["0"]!![0] * outputs["1"]!![0] * outputs["2"]!![0]
    }

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
