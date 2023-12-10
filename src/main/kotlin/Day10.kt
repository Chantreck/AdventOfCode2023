fun main() {
    val count = readln().toInt()
    val lines = (1..count).map { readln() }

    var answer = 0

    val startLine = lines.find { it.contains('S') }!!
    val startRowIndex = lines.indexOf(startLine)
    val startColumnIndex = startLine.indexOf('S')

    val checked = MutableList(count) { MutableList(count) { false } }

    var row = startRowIndex
    var column = startColumnIndex

    while (!checked[row][column]) {
        checked[row][column] = true

        val char = lines[row][column]
        when (char) {
            'S' -> {
                row -= 1
            }
            '|' -> {
                if (row == 0 || row == count - 1) {
                    break
                }
                if (checked[row - 1][column]) {
                    row += 1
                    continue
                }
                if (checked[row + 1][column]) {
                    row -= 1
                    continue
                }
            }

            '-' -> {
                if (column == 0 || column == count - 1) {
                    break
                }
                if (checked[row][column - 1]) {
                    column += 1
                    continue
                }
                if (checked[row][column + 1]) {
                    column -= 1
                    continue
                }
            }

            'L' -> {
                if (row == 0 || column == count - 1) {
                    break
                }
                if (checked[row - 1][column]) {
                    column += 1
                    continue
                }
                if (checked[row][column + 1]) {
                    row -= 1
                    continue
                }
            }

            'J' -> {
                if (row == 0 || column == 0) {
                    break
                }
                if (checked[row - 1][column]) {
                    column -= 1
                    continue
                }
                if (checked[row][column - 1]) {
                    row -= 1
                    continue
                }
            }

            '7' -> {
                if (row == count - 1 || column == 0) {
                    break
                }
                if (checked[row + 1][column]) {
                    column -= 1
                    continue
                }
                if (checked[row][column - 1]) {
                    row += 1
                    continue
                }
            }

            'F' -> {
                if (row == count - 1 || column == count - 1) {
                    break
                }
                if (checked[row + 1][column]) {
                    column += 1
                    continue
                }
                if (checked[row][column + 1]) {
                    row += 1
                    continue
                }
            }
        }
    }

    println(checked.joinToString("\n"))

    answer = checked.flatten().count { it } / 2

    val cycle = lines.mapIndexed { row, line ->
        line.mapIndexed { column, char ->
            if (checked[row][column]) {
                char
            }
            else "."
        }
    }

    println(cycle.map { it.joinToString("?") }.joinToString("\n" + "?".repeat(count * 2 - 1) + "\n"))

}


