package day07alt2

import readInput

fun main() {
    fun parseFiles(input: List<String>): List<Long> {
        val dirs = HashMap<String, Long>().also { it["/"] = 0L }
        var cwd = "/"

        input.forEach { line ->
            if (line.startsWith("$ cd ")) {
                cwd = when (line.substring(5)) {
                    "/" -> "/"
                    ".." -> "${cwd.split('/').dropLast(2).joinToString("/")}/"
                    else -> "${cwd}${line.substring(5)}/"
                }
            } else if (!line.startsWith("$")) {
                val parts = line.split(' ')
                if (parts[0] == "dir") {
                    dirs["${cwd}${parts[1]}/"] = 0L
                } else {
                    val cwdParts = cwd.split('/')
                    for (i in 1 until cwdParts.size) {
                        val path = "${cwdParts.take(i).joinToString("/")}/"
                        dirs[path] = (dirs[path] ?: 0L) + parts[0].toLong()
                    }
                }
            }
        }
        return dirs.map { it.value }
    }

    fun part1(input: List<String>) = parseFiles(input).filter { it <= 100000 }.sum()

    fun part2(input: List<String>): Long {
        val dirs = parseFiles(input)
        return dirs.fold(Long.MAX_VALUE) { cur, it -> if (it >= dirs.max() - 40000000 && it < cur) it else cur }
    }

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

