enum class Status {
    EMPTY,
    ROUNDED,
    CUBE,
}

fun main() {
    val count = readln().toInt()
    val lines = (1..count).map { readln() }

    var rocks = lines.map { line ->
        line.map {
            when (it) {
                'O' -> Status.ROUNDED
                '#' -> Status.CUBE
                else -> Status.EMPTY
            }
        }.toMutableList()
    }.toMutableList()

//    println(rocks)

    val map = mutableMapOf<String, Int>()

    var step = 0
    while (step < 1_000_000_000) {
        println("STEP $step")

        north(rocks, count)
        rocks = rotate(rocks, count)
        north(rocks, count)
        rocks = rotate(rocks, count)
        north(rocks, count)
        rocks = rotate(rocks, count)
        north(rocks, count)
        rocks = rotate(rocks, count)

        val str = rocks.toString()
        if (map.contains(str)) {
            val modulo = step - map[str]!!
            step += ((1_000_000_000 - step) / modulo) * modulo
            break
        } else {
            map[str] = step
            step += 1
        }
//        println(rocks)
    }

    for (i in step + 1..<1_000_000_000) {
        println("STEP $i")

        north(rocks, count)
        rocks = rotate(rocks, count)
        north(rocks, count)
        rocks = rotate(rocks, count)
        north(rocks, count)
        rocks = rotate(rocks, count)
        north(rocks, count)
        rocks = rotate(rocks, count)
    }

//    println(rocks)

    val result = rocks.mapIndexed { index, line ->
        line.sumOf {
            if (it == Status.ROUNDED) {
                count - index
            } else 0
        }
    }.sum()
    println(result)
}

fun north(rocks: MutableList<MutableList<Status>>, count: Int) {
    for (i in 1..<count) {
        for (j in i downTo 1 step 1) {
            for (k in 0..<count) {
                if (rocks[j][k] == Status.ROUNDED && rocks[j - 1][k] == Status.EMPTY) {
                    rocks[j][k] = Status.EMPTY
                    rocks[j - 1][k] = Status.ROUNDED
                }
            }
        }
    }
}

fun rotate(rocks: MutableList<MutableList<Status>>, count: Int): MutableList<MutableList<Status>> {
    val newRocks = MutableList(count) { MutableList(count) { Status.EMPTY } }

    for (i in 0..<count) {
        for (j in 0..<count) {
            val newRow = j
            val newColumn = count - i - 1

            newRocks[newRow][newColumn] = rocks[i][j]
        }
    }

    return newRocks
}

fun MutableList<MutableList<Status>>.toString(): String {
    return this.map {
        it.map { status ->
            when (status) {
                Status.EMPTY -> '.'
                Status.ROUNDED -> 'O'
                Status.CUBE -> '#'
            }
        }.joinToString("")
    }.joinToString("")
}