package day07alt1

import readInput

enum class FsNodeType { FILE, DIR }

data class FsNode(
    var size: Long,
    val type: FsNodeType,
    val childNodes: HashMap<String, FsNode> = HashMap(),
) {
    private val childDirs: Collection<FsNode> get() = childNodes.filter { it.value.type == FsNodeType.DIR }.map { it.value }
    val flatDirs: Collection<FsNode> get() = buildList {
        add(this@FsNode)
        addAll(childDirs.flatMap { it.flatDirs })
    }

    fun getNode(path: ArrayDeque<String>): FsNode {
        if (path.size == 0) {
            return this
        }
        return childNodes[path.first()]?.getNode(ArrayDeque(path).also { it.removeFirst() }) ?: throw IllegalStateException()
    }

    fun calculateSize(): Long {
        if (!(size == 0L && childNodes.size > 0)) {
            return size
        }
        size = childNodes.map { it.value.calculateSize() }.sum()
        return size
    }
}

fun main() {
    fun parseFiles(input: List<String>): FsNode {
        val rootNode = FsNode(0L, FsNodeType.DIR)
        var cwd = ArrayDeque<String>()

        input.forEach { line ->
            if (line.startsWith("$ cd ")) {
                when (line.substring(5)) {
                    "/" -> cwd = ArrayDeque()
                    ".." -> cwd.removeLast()
                    else -> cwd.addLast(line.substring(5))
                }
            } else if (!line.startsWith("$")) {
                val parts = line.split(' ')
                val type = if (parts[0] == "dir") FsNodeType.DIR else FsNodeType.FILE
                
                rootNode.getNode(cwd).childNodes[parts[1]] = FsNode(parts[0].toLongOrNull() ?: 0L, type)
            }
        }

        rootNode.calculateSize()
        return rootNode
    }

    fun part1(input: List<String>) = parseFiles(input).flatDirs.filter { it.size <= 100000 }.sumOf { it.size }

    fun part2(input: List<String>): Long {
        val root = parseFiles(input)
        return root.flatDirs.fold(Long.MAX_VALUE) { cur, it -> if (it.size >= root.size - 40000000 && it.size < cur) it.size else cur }
    }

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
