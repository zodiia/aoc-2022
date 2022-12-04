fun main() {
    fun getRanges(input: List<String>) = input.map { line ->
        line.split(',').map { range ->
            range.split('-').map(String::toInt).let { it[0]..it[1] }
        }
    }

    fun IntRange.containedIn(other: IntRange) = first >= other.first && last <= other.last

    fun part1(input: List<String>) = getRanges(input).count { it[0].containedIn(it[1]) || it[1].containedIn(it[0]) }

    fun part2(input: List<String>) = getRanges(input).count { it[0].intersect(it[1]).isNotEmpty() }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
