fun main() {
    fun priority(char: Char) = char.code - (if (char.isUpperCase()) ('A'.code - 27) else 'a'.code - 1)

    fun part1(input: List<String>): Int = input
        .flatMap {
            it.chunked(it.length / 2).zipWithNext()
        }
        .map { (first, second) ->
            first.first { second.contains(it) }
        }
        .sumOf(::priority)

    fun part2(input: List<String>) = input
        .chunked(3)
        .map { Triple(it[0], it[1], it[2]) }
        .map { (first, second, third) ->
            first.first { second.contains(it) && third.contains(it) }
        }
        .sumOf(::priority)


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}