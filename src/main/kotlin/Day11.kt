import kotlin.math.max
import kotlin.math.min

data class Galaxy(
    val id: Int,
    var row: Int,
    var column: Int,
)

fun main() {
    val count = readln().toInt()
    val lines = (1..count).map { readln() }

    val galaxies = mutableListOf<Galaxy>()

    lines.forEachIndexed { row, line ->
        line.forEachIndexed { column, char ->
            if (char == '#') {
                galaxies.add(Galaxy(galaxies.count(), row, column))
            }
        }
    }

//    println(galaxies)

    val emptyRows = (0..<count).toSet() - galaxies.map { it.row }.toSet()
    val emptyColumns = (0..<count).toSet() - galaxies.map { it.column }.toSet()

    /*galaxies.forEach { galaxy ->
        val rowBefore = emptyRows.count { it < galaxy.row }
        val columnBefore = emptyColumns.count { it < galaxy.column }
        println("$galaxy $rowBefore $columnBefore")
        galaxy.row += rowBefore * 1000000
        galaxy.column += columnBefore *1000000
    }*/
//
//    println(emptyRows)
//    println(emptyColumns)

    println(galaxies)

    var paths = mutableListOf<Long>()

    for (i in 0..<galaxies.size) {
        for (j in i + 1..<galaxies.size) {
            val first = galaxies[i]
            val second = galaxies[j]

            var path = 0L

            var currentRow = min(first.row, second.row)
            var currentColumn = min(first.column, second.column)

            val endRow = max(first.row, second.row)
            val endColumn = max(first.column, second.column)

            val emptyRowsBetween = emptyRows.filter { it in currentRow..endRow }
            val emptyColumnsBetween = emptyColumns.filter { it in currentColumn..endColumn }

            while (currentRow != endRow && currentColumn != endColumn) {
                currentRow += 1
                currentColumn += 1
                path += 2L
            }

            while (currentRow != endRow) {
                currentRow += 1
                path += 1L
            }

            while (currentColumn != endColumn) {
                currentColumn += 1
                path += 1L
            }

            path += (emptyRowsBetween.size + emptyColumnsBetween.size) * 10

            paths.add(path)
//            println("$first $second — $path")
        }
    }

    println(paths.sum())
}