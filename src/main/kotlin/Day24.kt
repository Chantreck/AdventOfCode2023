data class Line(
    val start: Point,
    val dx: Long,
    val dy: Long,
    val dz: Long,

    var k: Double = Double.NaN,
    var b: Double = Double.NaN,
)

data class Point(
    val x: Long,
    val y: Long,
    val z: Long,
)

/*val min = 0.0
val max = 200000000000000.0*/

fun main() {
    val count = readln().toInt()
    val lines = (1..count).map { readln() }.map { string ->
        val parts = string.split(" @ ")
        val coords = parts.first().split(", ").map { it.toLong() }
        val deltas = parts.last().split(", ").map { it.toLong() }
        val line = Line(Point(coords[0], coords[1], coords[2]), deltas[0], deltas[1], deltas[2])
//        line.calculateCoeffs()
        line
    }

    var answer = 0

    val equations = StringBuilder()
    for (i in 0..2) {
        val t = "t$i"
        val x = "${lines[i].start.x} + ${lines[i].dx}$t == x + vx*$t, "
        val y = "${lines[i].start.y} + ${lines[i].dy}$t == y + vy*$t, "
        val z = "${lines[i].start.z} + ${lines[i].dz}$t == z + vz*$t, "
        equations.append(x).append(y).append(z)
    }
    val system = "Solve[{" + equations.dropLast(2) + "}, {x,y,z,vx,vy,vz,t0,t1,t2}]"
    println(system)

    /*for (i in 0..<count) {
        for (j in i + 1..<count) {
            val first = lines[i]
            val second = lines[j]
            val intersection = first.intersect(second) ?: continue
            if (intersection.x !in min..max || intersection.y !in min..max) continue

            val isFirstInFuture = first.isInFuture(intersection)
            val isSecondInFuture = second.isInFuture(intersection)
            println("Lines: first - $first, second - $second. Intersection: $intersection (first: $isFirstInFuture, second: $isSecondInFuture)")

            if (isFirstInFuture && isSecondInFuture) {
                answer += 1
            }
        }
    }*/

    println(answer)
}

/*private fun Line.calculateCoeffs() {
    this.k = this.dy / this.dx
    this.b = this.start.y - this.k * this.start.x
}

private fun Line.intersect(other: Line): Point? {
    if (this.k == other.k) return null

    val x = (other.b - this.b) / (this.k - other.k)
    val y = this.k * x + this.b
    return Point(x, y, Double.NaN)
}*/

private fun Line.isInFuture(point: Point): Boolean {
    val t = (point.x - this.start.x) / this.dx
    return t >= 0
}
