fun main() {
    val priorities = ('a'..'z') + ('A'..'Z')

    fun part1(input: List<String>) = input.sumOf {
        priorities.indexOf(
            it.substring(0, it.length / 2).toSet().intersect(it.substring(it.length / 2).toSet()).first()
        ) + 1
    }

    fun part2(input: List<String>) = input.chunked(3).sumOf {
        priorities.indexOf(it[0].toSet().intersect(it[1].toSet()).intersect(it[2].toSet()).first()) + 1
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
