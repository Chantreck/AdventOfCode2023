enum class Wins {
    FIVE,
    FOUR,
    FULL_HOUSE,
    THREE,
    TWO_PAIR,
    PAIR,
    HIGH_CARD,
}

fun main() {
    val count = readln().toInt()
    val lines = (1..count).map { readln() }.map { it.split(" ") }

    var answer = 0

    val order = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')

    val orderedLines = lines.toMutableList()

    for (i in 0..<count) {
        for (j in 0..<count) {
            if (i == j) continue

            val o1 = orderedLines[i]
            val o2 = orderedLines[j]

            val firstHand = o1.first()
            val secondHand = o2.first()

            val win1 = firstHand.toWin()
            val win2 = secondHand.toWin()

            if (win1 < win2) {
                continue
            }
            if (win1 > win2) {
                val temp = orderedLines[i]
                orderedLines[i] = orderedLines[j]
                orderedLines[j] = temp
                continue
            }

            val swap = when {
                firstHand[0] != secondHand[0] -> order.indexOf(secondHand[0]) - order.indexOf(firstHand[0])
                firstHand[1] != secondHand[1] -> order.indexOf(secondHand[1]) - order.indexOf(firstHand[1])
                firstHand[2] != secondHand[2] -> order.indexOf(secondHand[2]) - order.indexOf(firstHand[2])
                firstHand[3] != secondHand[3] -> order.indexOf(secondHand[3]) - order.indexOf(firstHand[3])
                firstHand[4] != secondHand[4] -> order.indexOf(secondHand[4]) - order.indexOf(firstHand[4])
                else -> 0
            }

            if (swap >= 0) {
                continue
            } else {
                val temp = orderedLines[i]
                orderedLines[i] = orderedLines[j]
                orderedLines[j] = temp
                continue
            }
        }
    }

    println(orderedLines.joinToString("\n"))

    orderedLines.forEachIndexed { index, strings ->
        val bid = strings.last().toInt()
        answer += bid * (index + 1)
    }

    println()
    println(answer)
}

fun String.toWin(): Wins {
    val withoutJoker = this.filter { it != 'J' }
    val jokerCount = this.filter { it == 'J' }.length

    if (jokerCount == 5) return Wins.FIVE
    if (jokerCount == 4) return Wins.FIVE
    if (jokerCount == 3) {
        val distincts = withoutJoker.map { it }.distinct()
        return if (distincts.size == 1) Wins.FIVE
        else Wins.FOUR
    }
    if (jokerCount == 2) {
        val distincts = withoutJoker.map { it }.distinct()
        if (distincts.size == 1) return Wins.FIVE
        if (distincts.size == 2) {
            return Wins.FOUR
        }
        return Wins.THREE
    }

    if (jokerCount == 1) {
        /*val distincts = withoutJoker.map { it }.distinct()

        if (distincts.size == 1) {
            return Wins.FIVE
        }
        if (distincts.size == 2) {
            val first = distincts.first()

            if (withoutJoker.filter { it == first }.length == 2) {
                return Wins.FULL_HOUSE
            } else {
                return Wins.FOUR
            }
        }
        if (distincts.size == 3) {
            return Wins.THREE
        }
        return Wins.PAIR*/
        val others = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
        val max = others.minOf { other ->
            val a = withoutJoker + other
            val win = a.toWin2()
            win
        }
        return max
    }

    return this.toWin2()
}

fun String.toWin2(): Wins {
    val distincts = this.map { it }.distinct()

//    println("$this $distincts ${distincts.count()}")

    if (distincts.count() == 1) return Wins.FIVE

    if (distincts.count() == 2) {
        val first = distincts.first()
        val second = distincts.last()

        if ((this.count { it == first } == 1 && this.count { it == second } == 4) || (this.count { it == second } == 1 && this.count { it == first } == 4)) {
            return Wins.FOUR
        }

        if ((this.count { it == first } == 2 && this.count { it == second } == 3) || (this.count { it == second } == 2 && this.count { it == first } == 3)) {
            return Wins.FULL_HOUSE
        }
    }

    if (distincts.count() == 3) {
        val first = distincts[0]
        val second = distincts[1]
        val third = distincts[2]

        if (this.count { it == first } == 3 || this.count { it == second } == 3 || this.count { it == third } == 3) {
            return Wins.THREE
        }

        if (this.count { it == first } == 2 && (this.count { it == second } == 2 || this.count { it == third } == 2) ||
            this.count { it == second } == 2 && (this.count { it == first } == 2 || this.count { it == third } == 2) ||
            this.count { it == third } == 2 && (this.count { it == second } == 2 || this.count { it == first } == 2)) {
            return Wins.TWO_PAIR
        }
    }

    if (distincts.count() == 4) return Wins.PAIR

    return Wins.HIGH_CARD
}
