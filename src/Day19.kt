import kotlin.random.Random
import kotlin.random.nextInt

private data class Node(val left: Node?, val value: Int, val right: Node?, val size: Int)

fun <T> MutableList<T>.shuffle() {
    for (i in indices) {
        val r = Random.nextInt(i..<size)
        val x = this[r]
        this[r] = this[i]
        this[i] = x
    }
}

private operator fun Node.plus(v: Int): Node = when {
    v < value -> copy(left = left?.plus(v) ?: Node(null, v, null, 1), size = size + 1)
    v > value -> copy(right = right?.plus(v) ?: Node(null, v, null, 1), size = size + 1)
    else -> throw IllegalStateException()
}

private operator fun Node.get(index: Int): Int {
    val i = if (left != null) {
        if (index < left.size) {
            return left[index]
        }
        index - left.size
    } else {
        index
    }
    return if (i == 0) {
        value
    } else if (right != null && i - 1 < right.size) {
        right[i - 1]
    } else {
        throw IllegalStateException()
    }
}

private fun Node.removeAt(index: Int): Node? {
    val i = if (left != null) {
        if (index < left.size) {
            return copy(left = left.removeAt(index), size = size - 1)
        }
        index - left.size
    } else {
        index
    }
    return if (i == 0) {
        if (left != null) {
            val (l, v) = left.removeMax()
            copy(left = l, value = v, size = size - 1)
        } else {
            right
        }
    } else if (right != null && i - 1 < right.size) {
        copy(right = right.removeAt(i - 1), size = size - 1)
    } else {
        throw IllegalStateException()
    }
}

private fun Node.removeMax(): Pair<Node?, Int> = if (right != null) {
    val (r, v) = right.removeMax()
    Pair(copy(right = r, size = size - 1), v)
} else {
    Pair(left, value)
}

private fun Node.removeMin(): Pair<Node?, Int> = if (left != null) {
    val (l, v) = left.removeMin()
    Pair(copy(left = l, size = size - 1), v)
} else {
    Pair(right, value)
}

fun main() {
    fun Int.part1(): Int {
        val ba = BooleanArray(this) { true }
        var k = 1
        var i = 0
        while (true) {
            while (!ba[i]) {
                i = (i + 1) % this
            }
            if (k == this) {
                return i + 1
            }
            k++
            do {
                i = (i + 1) % this
            } while (!ba[i])
            ba[i] = false
        }
    }

    fun Int.part2(): Int {
        fun IntRange.recur(): Node? {
            val size = last - first + 1
            return if (size == 0) {
                null
            } else {
                val mid = (size / 2) + first
                Node(
                    left = (first..<mid).recur(),
                    value = mid,
                    right = (mid + 1..last).recur(),
                    size = size,
                )
            }
        }

        var node = (1..this).recur()!!
        var i = 0
        while (true) {
            if (node.size == 1) {
                return node.value
            }
            val k = (i + (node.size / 2)) % node.size
            if (k < i) {
                i--
            }
            node = node.removeAt(k)!!
            i = (i + 1) % node.size
        }
    }

    check(5.part1() == 3)
    printlnMeasureTimeMillis { 3017957.part1().println() }
    check(5.part2() == 2)
    printlnMeasureTimeMillis { 3017957.part2().println() }
}
