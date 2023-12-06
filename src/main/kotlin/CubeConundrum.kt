/* Part One
data class Game(
    val id: Int,
    val sets: List<Triple<Int, Int, Int>>,
)

fun main() {
    val count = readln().toInt()
    val games = (1..count).map { readln() }.map { line ->
        val parts = line.split(": ")
        val gameId = parts.first().drop(5).toInt()

        val sets = parts.last().split("; ").map { set ->
            val takes = set.split(", ")

            var red = 0
            var green = 0
            var blue = 0

            takes.forEach {
                val takeParts = it.split(" ")
                val number = takeParts.first().toInt()
                when (takeParts.last()) {
                    "red" -> red += number
                    "green" -> green += number
                    "blue" -> blue += number
                }
            }

            Triple(red, green, blue)
        }

        Game(id = gameId, sets = sets)
    }

    val answer = games.filter {
        val sets = it.sets
        sets.all { it.first <= 12 && it.second <= 13 && it.third <= 14 }
    }.sumOf { it.id }
    println(answer)
}
*/

data class Game(
    val id: Int,
    val sets: List<Triple<Int, Int, Int>>,
)

fun main() {
    val count = readln().toInt()
    val games = (1..count).map { readln() }.map { line ->
        val parts = line.split(": ")
        val gameId = parts.first().drop(5).toInt()

        val sets = parts.last().split("; ").map { set ->
            val takes = set.split(", ")

            var red = 0
            var green = 0
            var blue = 0

            takes.forEach {
                val takeParts = it.split(" ")
                val number = takeParts.first().toInt()
                when (takeParts.last()) {
                    "red" -> red += number
                    "green" -> green += number
                    "blue" -> blue += number
                }
            }

            Triple(red, green, blue)
        }

        Game(id = gameId, sets = sets)
    }

    val answer = games.map { game ->
        val sets = game.sets
        val reds = sets.maxOf { it.first }
        val greens = sets.maxOf { it.second }
        val blues = sets.maxOf { it.third }
        reds * greens * blues
    }.sum()
    println(answer)
}