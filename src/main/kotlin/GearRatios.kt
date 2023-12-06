// Part One
/*
fun main() {
    val count = readln().toInt()
    val lines = (1..count).map { readln() }

    val escape = listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.')

    var sum = 0

    lines.forEachIndexed { index, line ->
        var i = 0
        while (i < line.lastIndex) {
            if (line[i].isDigit()) {
                var end = i
                while (end < line.lastIndex && line[end + 1].isDigit()) {
                    end += 1
                }

                val number = line.substring(i..end).toInt()

                println("Line $index: number - $number (from $i to $end)")

                var symbols = ""
                if (index > 0) {
                    symbols += lines[index - 1].substring(max(0, i - 1), min(line.lastIndex, end + 2))
                }
                if (i > 0) {
                    symbols += lines[index][i - 1]
                }
                if (end < line.lastIndex - 1) {
                    symbols += lines[index][end + 1]
                }
                if (index < count - 1) {
                    symbols += lines[index + 1].substring(max(0, i - 1), min(line.lastIndex, end + 2))
                }

                if (symbols.filterNot { it in escape }.isNotEmpty()) {
                    sum += number
                    println("I'm adding $number")
                }

                i = end + 1
                continue
            }

            i += 1
        }
    }

    println(sum)
}
*/

data class Number(
    val value: Int,
    val line: Int,
    val start: Int,
    val end: Int,
)

fun main() {
    val count = readln().toInt()
    val lines = (1..count).map { readln() }

    val numbers = mutableListOf<Number>()

    lines.forEachIndexed { index, line ->
        var i = 0
        while (i < line.lastIndex) {
            if (line[i].isDigit()) {
                var end = i
                while (end < line.lastIndex && line[end + 1].isDigit()) {
                    end += 1
                }
                val number = line.substring(i..end).toInt()

                println("Line $index: number - $number (from $i to $end)")

                numbers.add(Number(value = number, line = index, start = i, end = end))

                i = end + 1
                continue
            }
            i += 1
        }
    }

    var sum = 0

    lines.forEachIndexed { index, line ->
        for (i in line.indices) {
            val char = line[i]
            if (char != '*') continue

            val adjacentNumbers = numbers.filter {
                it.line in (index - 1)..(index + 1) && i in (it.start - 1)..(it.end + 1)
            }
            if (adjacentNumbers.count() == 2) sum += adjacentNumbers.first().value * adjacentNumbers.last().value
        }
    }

    println(sum)
}