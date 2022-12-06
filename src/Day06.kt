fun main() {
    fun getIndexOfMarker(input: String, length: Int): Int =
        (length..input.length).find { input.substring(it - length, it).toSet().size == length } ?: -1

    fun part1(input: List<String>) = getIndexOfMarker(input.first(), 4)

    fun part2(input: List<String>) = getIndexOfMarker(input.first(), 14)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
