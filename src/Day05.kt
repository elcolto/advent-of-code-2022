fun main() {

    fun part1(input: List<String>): String = topOfTheStacks(input) { move, stacks ->
        repeat(move.count) {

            val crateToMove = stacks[move.from].removeLast()
            stacks[move.to].add(crateToMove)
        }
    }

    fun part2(input: List<String>): String = topOfTheStacks(input) { move, stacks ->
        val cratesToMove = stacks[move.from].takeLast(move.count)
        repeat(move.count) {
            stacks[move.from].removeLast()
        }
        stacks[move.to].addAll(cratesToMove)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

fun topOfTheStacks(input: List<String>, operateMove: (Move, List<Stack>) -> Unit): String {
    val (movements, stacks) = stacksAndMovements(input)
    printStacks(stacks)
    movements.forEach { move ->
        println("move ${move.count} from ${move.from} to ${move.to}")
        operateMove(move, stacks)
        println("==================")
        printStacks(stacks)
    }

    println("==================")
    printStacks(stacks)

    return stacks.mapNotNull { it.lastOrNull() }
        .joinToString(separator = "")
}

fun stacksAndMovements(input: List<String>): Pair<List<Move>, List<Stack>> {
    val separatorLine = input.indexOf("")
    val stackCount = input[separatorLine - 1].replace(" ", "").last().digitToInt()
    val stackInput = input.subList(0, separatorLine - 1)
    val movements = input.subList(separatorLine + 1, input.size).map { Move.fromLine(it) }


    val stacks = (0 until stackCount)
        .map { stack ->
            stackInput.map { line ->
                val index = stack * 4 + 1
                line[index]
            }
        }
        .map {
            it.reversed()
        }
        .map { it.filterNot { char -> char == ' ' } }
        .map { it.toMutableList() }
    return Pair(movements, stacks)
}


fun printStacks(stacks: List<Stack>) {
    stacks.forEachIndexed { index, stack ->
        println("$index: $stack")
    }
}

data class Move(val count: Int, val from: Int, val to: Int) {
    companion object {
        fun fromLine(line: String): Move = line
            .split(" ")
            .mapNotNull { it.toIntOrNull() }
            .run { Move(count = get(0), from = get(1) - 1, to = get(2) - 1) }
    }
}

typealias Stack = MutableList<Char>
