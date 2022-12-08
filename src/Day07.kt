fun main() {

    fun filetree(input: List<String>): Directory {
        var directories = mutableListOf<Directory>()
        directories.add(Directory("/"))
        input.forEach { line ->
            when {
                line == "$ ls" -> Unit
                line.startsWith("dir") -> Unit
                line == "$ cd /" -> directories.removeIf { it.name != "/" }
                line == "$ cd .." -> directories.removeFirst()
                line.startsWith("$ cd") -> {
                    val name = line.substringAfterLast(" ")
                    val newList = listOf(directories.first().scan(name)) + directories
                    directories = newList.toMutableList()
                }

                else -> {
                    val size = line.substringBefore(" ").toInt()
                    directories.first().sizeOfFiles += size
                }
            }
        }
        val root = directories.last()
        return root
    }

    fun part1(input: List<String>): Int {

        val root = filetree(input)
        return root.find { it.size <= 100000 }.sumOf { it.size }
    }


    fun part2(input: List<String>): Int {
        val root = filetree(input)
        val unused = 70000000 - root.size
        val targetSpace = 30000000 - unused
        return root.find { it.size >= targetSpace }.minBy { it.size }.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)


    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}


class Directory(val name: String) {
    private val subDirectories: MutableMap<String, Directory> = mutableMapOf()
    var sizeOfFiles: Int = 0

    val size: Int
        get() = sizeOfFiles + subDirectories.values.sumOf { it.size }


    fun scan(dir: String): Directory = subDirectories.getOrPut(dir) { Directory(dir) }

    fun find(predicate: (Directory) -> Boolean): List<Directory> =
        subDirectories.values.filter(predicate) + subDirectories.values.flatMap { it.find(predicate) }
}