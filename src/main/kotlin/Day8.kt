import kotlin.math.max
import kotlin.math.min

private data class Node(
    val current: String,
    val left: String,
    val right: String,
)

fun main() {
    val count = readln().toInt()
    val lines = (1..count).map { readln() }

    val instructions = lines.first()
    val nodes = lines.drop(2).map {
        val startEnd = it.split(" = (")
        val currentNode = startEnd.first()
        val ends = startEnd.last().trim(')')
        val leftRight = ends.split(", ")
        val left = leftRight.first()
        val right = leftRight.last()

        Node(currentNode, left, right)
    }

    val moves = nodes.filter { it.current.endsWith('A') }.map {
        var currentNode = it
        var answer = 0

        while (!currentNode.current.endsWith('Z')) {
            val index = answer % instructions.length

            val currentMove = instructions[index]
            if (currentMove == 'L') {
                currentNode = nodes.find { it.current == currentNode.left }!!
            } else {
                currentNode = nodes.find { it.current == currentNode.right }!!
            }

            answer += 1
        }

        answer.toLong()
    }

    val answer = moves.reduce { acc, l -> findLCM(acc, l) }
    println(answer)
}

fun findLCM(a: Long, b: Long): Long {
    val greater = max(a, b)
    val smallest = min(a, b)
    var i = greater
    while (true) {
        if (i % smallest == 0L) return i
        i += greater
    }
}
