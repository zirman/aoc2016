import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentSetOf
import kotlin.math.max
import kotlin.math.min

sealed interface RadioisotopeItem {
    val string: String

    @JvmInline
    value class Microchip(override val string: String) : RadioisotopeItem

    @JvmInline
    value class Generator(override val string: String) : RadioisotopeItem
}

typealias Floors = List<Set<RadioisotopeItem>>

data class ElevatorState(
    val elevator: Int,
    val floors: Floors,
)

fun <T> List<T>.permute(): List<Pair<T, T>> {
    return flatMapIndexed { index: Int, t1: T ->
        this@permute.subList(index, this@permute.size).map { t2 -> Pair(t1, t2) }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val floorRegex = """The (\w+) floor contains (.*)\.""".toRegex()
        val generatorRegex = """a (\w+) generator""".toRegex()
        val microchipRegex = """a (\w+)-compatible microchip""".toRegex()
        val initialFloors = input.map { line ->
            val (_, itemsStr) = floorRegex.matchEntire(line)!!.destructured

            buildSet {
                if (itemsStr != "nothing relevant") {
                    itemsStr
                        .split(", ", ", and ", " and ")
                        .forEach { itemStr ->
                            microchipRegex.matchEntire(itemStr)
                                ?.destructured
                                ?.let { (microchipType) -> add(RadioisotopeItem.Microchip(microchipType)) }

                            generatorRegex.matchEntire(itemStr)
                                ?.destructured
                                ?.let { (generatorType) -> add(RadioisotopeItem.Generator(generatorType)) }
                        }
                }
            }
        }

        val sortedItems = initialFloors
            .reduce { a, b -> a + b }
            .sortedWith(compareBy<RadioisotopeItem> {
                it.string
            }.thenBy {
                when (it) {
                    is RadioisotopeItem.Microchip -> 1
                    is RadioisotopeItem.Generator -> 0
                }
            })
            .toSet()

        fun ElevatorState.asString(): String {
            floors
                .mapIndexed { index, radioisotopeItems -> Pair(index, radioisotopeItems) }
                .reversed()
                .joinToString("\n") { (floor, items) ->
                    "F${floor + 1} ${if (elevator == floor) 'E' else '.'}  ${
                        sortedItems.joinToString(" ") {
                            if (items.contains(it)) {
                                "${it.string[0].uppercase()}${
                                    when (it) {
                                        is RadioisotopeItem.Generator -> "G"
                                        is RadioisotopeItem.Microchip -> "M"
                                    }
                                }"
                            } else {
                                ". "
                            }
                        }
                    } "
                }
                .run { return this }
        }

        val finalState = ElevatorState(
            elevator = 3,
            floors = listOf(
                emptySet(),
                emptySet(),
                emptySet(),
                sortedItems,
            ),
        )

        finalState.asString().also { println(it) }

        fun ElevatorState.isValid(): Boolean {
            return floors
                .any { items ->
                    items.filterIsInstance<RadioisotopeItem.Microchip>()
                        .any { microchip -> items.contains(RadioisotopeItem.Generator(microchip.string)).not() } &&
                            items.any { it is RadioisotopeItem.Generator }
                }
                .not()
        }

        fun ElevatorState.nextStates(visited: PersistentSet<ElevatorState>): List<ElevatorState> {
            val (currentFloor, items) = this

            run { max(currentFloor - 1, 0)..min(currentFloor + 1, 3) }
                .filter { it != currentFloor }
                .flatMap { nextFloor ->

                    items[currentFloor].toList().permute()
                        .map { movedItems ->
//                            println("$nextFloor ${movedItems}")
                            val (item1, item2) = movedItems
                            ElevatorState(
                                nextFloor,
                                items.mapIndexed { index, radioisotopeItems ->
                                    when (index) {
                                        currentFloor -> radioisotopeItems - listOf(item1, item2).toSet()
                                        nextFloor -> radioisotopeItems + listOf(item1, item2)
                                        else -> radioisotopeItems
                                    }
                                },
                            )
                        }
                }
//                .also {
//                    it.forEach {
//                        it.asString().also { println(it) }
//                        kotlin.io.println()
//                    }
//                    println("wat")
//                }
                .filter { visited.contains(it).not() && it.isValid() }
//                .also {
//                    it.forEach {
//                        it.asString().also { println(it) }
//                        kotlin.io.println()
//                    }
//                    println("wut")
//                }
                .run { return this }
        }


        tailrec fun Set<ElevatorState>.breathFirstSearch(depth: Int, visited: PersistentSet<ElevatorState>): Int {
//            this.forEach {
//                it.asString().also { println(it) }
//                kotlin.io.println()
//            }
//            if (depth >= 3) return depth

//            if (this.contains(finalState)) return depth
            return run {
                flatMap {
//                    it.asString().also { println(it) }
//                    println("from $depth")
                    it.nextStates(visited)
//                        .also {
//                            it.forEach {
//                                it.asString().also { println(it) }
//                                kotlin.io.println()
//                            }
//                            println("wat")
//                        }
                }
//                    .filter { visited.contains(it).not() }
                    .toSet()
                    .takeIf { it.contains(finalState).not() }
                    ?: return depth
            }.breathFirstSearch(
                depth = depth + 1,
                visited = visited.addAll(this),
            )
        }
//        listOf(
//            emptySet(),
//            emptySet(),
//            setOf(
//                RadioisotopeItem.Microchip("hydrogen"),
//                RadioisotopeItem.Microchip("lithium"),
//            ),
//            setOf(
//                RadioisotopeItem.Generator("hydrogen"),
//                RadioisotopeItem.Generator("lithium"),
//            ),
//        )
//        )

        return setOf(ElevatorState(elevator = 0, floors = initialFloors))
            .breathFirstSearch(depth = 0, visited = persistentSetOf()) + 1
    }

    fun part2(input: List<String>): Int {
        TODO()
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day11_1_test")
    check(part1(testInput1).also { println(it) } == 11)

//    val testInput2 = readInput("Day11_2_test")
//    check(part2(testInput2) == 1)

    val input = readInput("Day11")
    part1(input).println()
//    part2(input).println()
}
