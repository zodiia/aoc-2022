package day07

import readInput

enum class FsNodeType { FILE, DIR }

data class FsNode(
    var size: Long,
    val type: FsNodeType,
    val childNodes: HashMap<String, FsNode> = HashMap(),
) {
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

    fun getSumOfDirSizes(node: FsNode, maxSize: Long): Long {
        var res = node.childNodes
            .filter { it.value.type == FsNodeType.DIR }
            .map { getSumOfDirSizes(it.value, maxSize) }
            .sum()
        if (node.size <= maxSize) {
            res += node.size
        }
        return res
    }

    fun getSmallestDirOfAtLeast(node: FsNode, minSize: Long): Long {
        val res = node.childNodes
            .filter { it.value.type == FsNodeType.DIR }
            .map { getSmallestDirOfAtLeast(it.value, minSize) }
            .minOrNull() ?: Long.MAX_VALUE
        if (node.size in minSize until res) {
            return node.size
        }
        return res
    }

    fun part1(input: List<String>): Long {
        return getSumOfDirSizes(parseFiles(input), 100000)
    }

    fun part2(input: List<String>): Long {
        val root = parseFiles(input)
        return getSmallestDirOfAtLeast(root, root.size - 40000000)
    }

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
