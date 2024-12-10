import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.absoluteValue
import kotlin.system.measureTimeMillis

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun printlnMeasureTimeMillis(block: () -> Unit) {
    measureTimeMillis(block)
        .also { println("time: $it") }
}

data class Pos(val row: Int, val col: Int)
operator fun Pos.minus(pos: Pos): Pos = Pos(
    row - pos.row,
    col - pos.col,
)
operator fun Pos.plus(pos: Pos): Pos = Pos(
    row + pos.row,
    col + pos.col,
)
fun Pos.manhattanDistance(pos: Pos): Int {
    val x = this - pos
    return x.row.absoluteValue + x.col.absoluteValue
}
fun Pos.goNorth(): Pos = copy(row = row - 1)
fun Pos.goSouth(): Pos = copy(row = row + 1)
fun Pos.goWest(): Pos = copy(col = col + 1)
fun Pos.goEast(): Pos = copy(col = col - 1)

data class Size(val width: Int, val height: Int)

fun List<String>.toSize(): Size = Size(width = this[0].length, height = size)

operator fun Size.contains(pos: Pos): Boolean =
    pos.row >= 0 && pos.row < height &&
            pos.col >= 0 && pos.col < width

fun <T> List<T>.dropAt(index: Int): List<T> = filterIndexed { i, t -> index != i }

fun <T> Iterable<T>.countUnique(): Int = toSet().size

fun <T> List<T>.permutations(prefix: List<T> = emptyList()): List<List<T>> {
    if (isEmpty()) {
        return listOf(prefix)
    }
    return flatMapIndexed { index, t ->
        buildList(size - 1) {
            this@permutations.forEachIndexed { index2, t ->
                if (index != index2) {
                    add(t)
                }
            }
        }.permutations(
            buildList(prefix.size + 1) {
                addAll(prefix)
                add(t)
            },
        )
    }
}

fun <T> List<T>.combinations(size: Int): List<List<T>> {
    fun MutableList<List<T>>.recur(i: Int, c: List<T>) {
        if (c.size == size) {
            this@recur.add(c)
            return
        }

        (i..<this@combinations.size).forEach { t ->
            recur(t + 1, c + this@combinations[t])
        }
    }
    return buildList {
        recur(0, emptyList())
    }
}
