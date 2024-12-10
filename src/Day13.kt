import java.util.PriorityQueue
import kotlin.system.measureTimeMillis

private typealias Input13 = Maze

private data class Maze(val favoriteNumber: Int, val destination: Pos)


fun main() {
    fun Pos.isWall(favoriteNumber: Int): Boolean =
        ((col * col + 3 * col + 2 * col * row + row + row * row) + favoriteNumber)
            .countOneBits() % 2 == 1

    fun Pos.next(favoriteNumber: Int): List<Pos> = listOf(
        goNorth(),
        goSouth(),
        goEast(),
        goWest(),
    ).filter {
        it.row >= 0 && it.col >= 0 &&
                it.isWall(favoriteNumber).not()
    }

    fun Input13.part1(): Int {
        val startingLocation = Pos(1, 1)
        val visited = mutableMapOf<Pos, Int>(startingLocation to 0)
        val queue = PriorityQueue<Pair<Pos, Int>>(compareBy { (location, steps) ->
            steps + location.manhattanDistance(destination)
        }).apply { add(Pair(startingLocation, 0)) }
        while (true) {
            var (location, steps) = queue.remove()
            if (location == destination) return steps
            val nextSteps = steps + 1
            location
                .next(favoriteNumber)
                .forEach { nextLocation ->
                    val visitedSteps = visited[nextLocation]
                    if (visitedSteps == null) {
                        queue.add(Pair(nextLocation, nextSteps))
                        visited[nextLocation] = nextSteps
                    } else if (nextSteps < visitedSteps) {
                        queue.remove(Pair(nextLocation, visitedSteps))
                        queue.add(Pair(nextLocation, nextSteps))
                        visited[nextLocation] = nextSteps
                    }
                }
        }
    }

    fun Int.part2(): Int {
        val favoriteNumber = this
        val startingLocation = Pos(1, 1)
        val visited = mutableSetOf<Pos>(startingLocation)
        var states = setOf(startingLocation)
        var steps = 0
        while (true) {
            if (steps == 50) return visited.size
            states = states.flatMap { it.next(favoriteNumber) }.toSet() - visited
            visited += states
            steps++
        }
    }
    check(Maze(10, Pos(4, 7)).part1() == 11)
    printlnMeasureTimeMillis { Maze(favoriteNumber = 1352, destination = Pos(39, 31)).part1().println() }
    printlnMeasureTimeMillis { 1352.part2().println() }
}
