fun main() {
    fun findMarker(input: List<String>, markerSize: Int) = input
        .first()
        .let { line ->
            val packetMarker = line.windowed(markerSize)
                .first { marker -> marker.toCharArray().distinct().count() == markerSize }
            line.indexOf(packetMarker) + markerSize

        }


    fun part1(input: List<String>): Int = findMarker(input, 4)

    fun part2(input: List<String>): Int = findMarker(input, 14)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 7)
    check(part1(listOf("bvwbjplbgvbhsrlpgdmjqwftvncz")) == 5)
    check(part1(listOf("nppdvjthqldpwncqszvftbrmjlhg")) == 6)
    check(part1(listOf("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")) == 10)
    check(part1(listOf("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")) == 11)

    check(part2(testInput) == 19)
    check(part2(listOf("bvwbjplbgvbhsrlpgdmjqwftvncz")) == 23)
    check(part2(listOf("nppdvjthqldpwncqszvftbrmjlhg")) == 23)
    check(part2(listOf("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")) == 29)
    check(part2(listOf("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")) == 26)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}