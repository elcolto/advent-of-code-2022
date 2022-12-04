fun main() {

    fun part1(input: List<String>): Int = intRanges(input)
        .count { (firstSections, secondSections) ->
            firstSections.all { secondSections.contains(it) } || secondSections.all { firstSections.contains(it) }
        }

    fun part2(input: List<String>): Int {
        return intRanges(input)
            .count { (firstSections, secondSections) ->
                firstSections.any { secondSections.contains(it) }
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

fun intRanges(input: List<String>) = input
    .map { it.ranges() }
    .map { (first, second) -> first.intRange() to second.intRange() }

fun String.intRange(): IntRange {
    val delimiter = '-'
    return (substringBefore(delimiter).toInt()..substringAfter(delimiter).toInt())
}

fun String.ranges(): Pair<String, String> {
    val delimiter = ','
    return substringBefore(delimiter) to substringAfter(delimiter)
}

