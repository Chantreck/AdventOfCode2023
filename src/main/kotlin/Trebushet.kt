import kotlin.math.min

fun main() {
    val count = readln().toInt()
    val lines = (1..count).map { readln() }

    val digits = listOf(
        "0",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven",
        "eight",
        "nine",
    )

    val answer = lines.sumOf { line ->
        val matches = mutableListOf<String>()
        for (i in line.indices) {
            val possibles = listOf(
                line.substring(i..i),
                line.substring(i..min(i + 1, line.lastIndex)),
                line.substring(i..min(i + 2, line.lastIndex)),
                line.substring(i..min(i + 3, line.lastIndex)),
                line.substring(i..min(i + 4, line.lastIndex)),
            )
            matches.addAll(possibles.filter { it in digits })
        }
        val first = matches.first().toDigit()
        val second = matches.last().toDigit()
        "${first}${second}".toInt()
    }
    println(answer)
}

private fun String.toDigit(): String = when (this) {
    "one" -> "1"
    "two" -> "2"
    "three" -> "3"
    "four" -> "4"
    "five" -> "5"
    "six" -> "6"
    "seven" -> "7"
    "eight" -> "8"
    "nine" -> "9"
    else -> this
}