import kotlin.math.pow

fun main() {
    val count = readln().toInt()
    val lines = (1..count).map { readln() }

    val copies = MutableList(count) { 1 }

    lines.forEachIndexed { index, line ->
        val parts = line.split(": ").last().split(" | ")
        val winningNumbers = parts.first().split(" ").filterNot { it.isBlank() }
        val numbers = parts.last().split(" ").filterNot { it.isBlank() }
        val won = numbers.count { it in winningNumbers }
        (index + 1 .. index + won).forEach {
            copies[it] += copies[index]
        }
    }

    val answer = copies.sum()
    println(answer)
}