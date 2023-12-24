fun main() {
    val count = readln().toInt()
    val lines = (1..count).map { readln() }

    val start = lines.first().indexOfFirst { it == '.' }
    val steps = MutableList(count) { MutableList(count) { Int.MIN_VALUE } }
    val visited = MutableList(count) { MutableList(count) { false } }

    val result = dfs(lines, 1, 0, count, 0, visited)
    println(result)
}

fun dfs(lines: List<String>, x: Int, y: Int, count: Int, steps: Int, visited: MutableList<MutableList<Boolean>>): Int {
    if (x < 0 || x >= count || y < 0 || y >= count) return Int.MIN_VALUE
    if (visited[y][x]) return Int.MIN_VALUE

    visited[y][x] = true

    if (x == count - 2 && y == count - 1) {
        visited[y][x] = false
        return steps
    }

    var maxSteps = Int.MIN_VALUE
    when (lines[y][x]) {
        '#' -> Unit
        '>' -> maxSteps = dfs(lines, x + 1, y, count, steps + 1, visited)
        '<' -> maxSteps = dfs(lines, x - 1, y, count, steps + 1, visited)
        'v' -> maxSteps = dfs(lines, x, y + 1, count, steps + 1, visited)
        '^' -> maxSteps = dfs(lines, x, y - 1, count, steps + 1, visited)
        else -> {
            val right = dfs(lines, x + 1, y, count, steps + 1, visited)
            val left = dfs(lines, x - 1, y, count, steps + 1, visited)
            val down = dfs(lines, x, y + 1, count, steps + 1, visited)
            val up = dfs(lines, x, y - 1, count, steps + 1, visited)
            maxSteps = maxOf(right, left, down, up)
        }
    }

    visited[y][x] = false
    return maxSteps
}
