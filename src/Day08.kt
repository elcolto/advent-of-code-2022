fun main() {

    fun plantTrees(input: List<String>) = input
        .map { line -> line.map { it.digitToInt() } }
        .let { grid ->
            grid.flatMapIndexed { y: Int, row: List<Int> ->
                row.mapIndexed { x, height ->
                    Tree(
                        x = x,
                        y = y,
                        height = height,
                        allToTheLeft = (0 until x).map { grid[y][it] },
                        allToTheTop = (0 until y).map { grid[it][x] },
                        allToTheRight = (x + 1 until row.size).map { grid[y][it] },
                        allToTheBottom = (y + 1 until grid.size).map { grid[it][x] }
                    )
                }
            }
        }

    fun part1(input: List<String>): Int {
        val side = plantTrees(input)
            .filter { tree ->
                listOf(tree.allToTheLeft, tree.allToTheTop, tree.allToTheRight, tree.allToTheBottom)
                    .any { side -> side.all { it < tree.height } || side.isEmpty() }
            }
        return side.size

    }

    fun part2(input: List<String>): Int {

        val scores = plantTrees(input).map { tree ->

            listOf(tree.allToTheLeft.reversed(), tree.allToTheTop.reversed(), tree.allToTheRight, tree.allToTheBottom)
                .map { side ->
                    side.indexOfFirst { it >= tree.height }.takeIf { it != -1 }?.let { it + 1 } ?: side.size
                }
                .fold(1, Int::times)


        }
        return scores.max()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

data class Tree(
    val x: Int,
    val y: Int,
    val height: Int,
    val allToTheLeft: List<Int>,
    val allToTheTop: List<Int>,
    val allToTheRight: List<Int>,
    val allToTheBottom: List<Int>,
)