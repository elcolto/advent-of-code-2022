fun main() {
    fun caloriesPerElf(
        input: List<String>
    ): List<Int> {

        val indexesOfSeparators = input.mapIndexedNotNull { index, element -> index.takeIf { element.isEmpty() } }
        var indexToInsert1 = 0
        val list = mutableListOf<Int>()

        input.forEachIndexed { index, element ->
            if (indexesOfSeparators.contains(index)) {
                indexToInsert1++
            } else {
                if (list.size < indexToInsert1 + 1) {
                    list.apply { add(element.toInt()) }
                } else {
                    list[indexToInsert1] += element.toInt()
                }
            }
        }
        return list
    }

    fun part1(input: List<String>): Int {

        val list = caloriesPerElf(input)
        return list.max()
    }

    fun part2(input: List<String>): Int = caloriesPerElf(input)
        .sortedDescending()
        .subList(0, 3)
        .sum()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
