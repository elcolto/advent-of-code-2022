fun main() {

    fun draws(input: List<String>) = input.flatMap { line ->
        line.split(" ")
            .map { it.first() }
            .zipWithNext()
    }

    fun resultCalculation(
        input: List<String>,
        drawsByStrategicGuide: (Pair<Char, Char>) -> Pair<Shape, Shape>
    ) = draws(input)
        .map(drawsByStrategicGuide)
        .fold(0) { points, (opponent, own) ->
            val result = own.result(opponent)
            points + result + own.points
        }

    fun part1(input: List<String>): Int = resultCalculation(input) { (opponent, own) ->
        Shape.from(opponent) to Shape.from(own)
    }

    fun part2(input: List<String>): Int = resultCalculation(input) { (opponentDraw, result) ->
        val opponent = Shape.from(opponentDraw)
        val own = when (result) {
            'X' -> opponent.superior()
            'Y' -> opponent
            'Z' -> opponent.inferior()
            else -> error("not applicable")
        }
        opponent to own
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

enum class Shape(val points: Int) {

    Rock(1), Paper(2), Scissor(3);

    fun superior(): Shape = when (this) {
        Rock -> Scissor
        Paper -> Rock
        Scissor -> Paper
    }

    fun inferior(): Shape = when (this) {
        Rock -> Paper
        Paper -> Scissor
        Scissor -> Rock
    }

    fun result(other: Shape) = when {
        this.superior() == other -> 6
        other.superior() == this -> 0
        other == this -> 3
        else -> error("not possible")
    }

    companion object {
        fun from(letter: Char) = when (letter) {
            'A', 'X' -> Rock
            'B', 'Y' -> Paper
            'C', 'Z' -> Scissor
            else -> error("not applicable")
        }
    }
}
