typealias Stacks = ArrayList<ArrayDeque<Char>>

fun main() {
    fun getStacks(input: List<String>): Stacks {
        val lines = input.subList(0, input.indexOf("")).dropLast(1).map { it.filterIndexed { idx, _ -> idx % 4 == 1 } }
        val stacks = Stacks()

        lines.last().forEach { _ -> stacks.add(ArrayDeque()) }
        lines.forEach { line -> line.forEachIndexed { idx, ch -> if (ch != ' ') stacks[idx].addLast(ch) } }
        return stacks
    }

    fun applyMovesToStacks(stacks: Stacks, input: List<String>, retainOrder: Boolean): Stacks {
        input.subList(input.indexOf("") + 1, input.size).forEach { line ->
            val parts = line.split(' ')
            val tempStack = ArrayDeque<Char>()
            val (amount, from, to) = Triple(parts[1].toInt(), parts[3].toInt() - 1, parts[5].toInt() - 1)

            for (i in 1..amount) {
                tempStack.addLast(stacks[from].removeFirst())
            }
            tempStack.apply { if (retainOrder) reverse() }.forEach { stacks[to].addFirst(it) }
        }
        return stacks
    }

    fun part1(input: List<String>) =
        applyMovesToStacks(getStacks(input), input, false).map { it.first() }.joinToString("")

    fun part2(input: List<String>) =
        applyMovesToStacks(getStacks(input), input, true).map { it.first() }.joinToString("")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
