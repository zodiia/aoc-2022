package day01

import readInput

fun main() {
    fun parseElves(input: List<String>): List<Int> {
        val elves = ArrayList<Int>()
        var currentElf = 0

        input.forEach {
            if (it.isBlank()) {
                elves.add(currentElf)
                currentElf = 0
            } else {
                currentElf += it.toInt()
            }
        }
        elves.add(currentElf)
        return elves
    }

    fun part1(input: List<String>): Int {
        val elves = parseElves(input)

        // Find the elf with the most calories
        return elves.max()
    }

    fun part2(input: List<String>): Int {
        val elves = parseElves(input)

        // Find the top three elves
        return elves.sortedDescending().take(3).sum()
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
