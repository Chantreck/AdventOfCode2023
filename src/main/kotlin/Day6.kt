fun main() {
    val count = 2
    val lines = (1..count).map { readln().drop(9) }

    val time = lines.first().split(' ').filterNot { it.isBlank() }.map { it.toInt() }.joinToString("").toLong()
    val distances = lines.last().split(' ').filterNot { it.isBlank() }.map { it.toInt() }.joinToString("").toLong()

    var timeAnswer = 0
    for (i in 0..time) {
        val left = time - i
        val distance = i * left
        if (distance > distances) {
            timeAnswer += 1
        }
    }
    println(timeAnswer)
}