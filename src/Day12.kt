fun main() {

    fun parse(input: List<String>): HeightMap {
        val grid = input.map {
            it.toCharArray()
        }

        fun find(characterToFind: Char): Pair<Int, Int> {
            val y = grid.indexOfFirst { line -> line.contains(characterToFind) }
            val x = grid.map { line -> line.indexOfFirst { it == characterToFind } }.first { it >= 0 }
            return y to x
        }

        val start = find('S')
        val end = find('E')
        return HeightMap(grid, start, end)
    }

    fun elevation(current: Char): Int = when (current) {
        'S' -> 0
        'E' -> 'z' - 'a'
        else -> current - 'a'
    }

    fun shortestPath(
        grid: List<CharArray>,
        start: Pair<Int, Int>,
        target: Char,
        ascending: Boolean = true
    ): Int {
        fun nextSteps(point: Point) = listOfNotNull(
            Point(point.y, point.x - 1).takeIf { it.x >= 0 },
            Point(point.y, point.x + 1).takeIf { it.x < grid.first().size },
            Point(point.y - 1, point.x).takeIf { it.y >= 0 },
            Point(point.y + 1, point.x).takeIf { it.y < grid.size }
        )

        val end = Point(
            grid.indexOfFirst { line -> line.contains(target) },
            grid.map { line -> line.indexOfFirst { it == target } }.first { it >= 0 }
        )
        val queue = mutableListOf(start)
        val distances = mutableMapOf<Point, Int>()
        val visited = mutableSetOf<Point>()
        distances[start] = 0

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            if (current !in visited) {
                visited.add(current)

                val currentElevation = elevation(grid[current.y][current.x])

                val nextSteps = nextSteps(current)
                println("current: ${current.first},${current.second}: nexts: ${nextSteps.joinToString(separator = ";") { "${it.first},${it.second}" }}")

                nextSteps.filter { (x, y) ->
                    val destElevation = elevation(grid[x][y])
                    if (ascending) {
                        currentElevation - destElevation >= -1
                    } else {
                        destElevation - currentElevation >= -1
                    }
                }
                    .filter { it !in visited }
                    .forEach {
                        if (grid[it.y][it.x] == grid[end.y][end.x]) {
                            return distances[current]!! + 1
                        }

                        if (it !in distances) {
                            distances[it] = distances[current]!! + 1
                            queue.add(it)
                        }
                    }
            }
        }


        return 0
    }

    fun part1(input: List<String>): Int {
        val (grid, start, end) = parse(input)


        return shortestPath(grid, start, 'E')
    }

    fun part2(input: List<String>): Int {
        val (grid, start, end) = parse(input)


        return shortestPath(grid, end, 'a', ascending = false)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}

private data class HeightMap(
    val grid: List<CharArray>,
    val start: Pair<Int, Int>,
    val end: Pair<Int, Int>
)

private typealias Point = Pair<Int, Int>

val Point.x
    get() = second

val Point.y
    get() = first
