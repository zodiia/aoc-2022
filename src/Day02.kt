fun main() {
    val pairings1 = listOf("B X", "C Y", "A Z", "A X", "B Y", "C Z", "C X", "A Y", "B Z")
    val pairings2 = listOf("B X", "C X", "A X", "A Y", "B Y", "C Y", "C Z", "A Z", "B Z")

    fun part1(input: List<String>) = input.sumOf { pairings1.indexOf(it) + 1 }

    fun part2(input: List<String>) = input.sumOf { pairings2.indexOf(it) + 1 }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
