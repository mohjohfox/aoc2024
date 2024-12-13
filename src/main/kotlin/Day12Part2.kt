class Day12Part2 {
    val visitedRegion = mutableListOf<Pair<Int, Int>>()
    fun partOne(input: List<String>) {
        var price = 0
        for (i in 0..<input.size) {
            for (k in 0..<input.first().length) {
                if (!visitedRegion.contains(Pair(i, k))) {
                    val region = input[i][k]
                    var sides = 0
                    // Search row from left to right
                    val graphList = mutableListOf<Pair<Int, Int>>()
                    graphList.add(Pair(i, k))
                    val visitedThisRun = mutableListOf<Pair<Int, Int>>()
                    while (graphList.size != 0) {
                        val alreadyChecked = graphList.toMutableList()
                        alreadyChecked.forEach { point ->
                            if (!visitedThisRun.contains(point)) {
                                if (point.first < input.size - 1 && point.second < input.first().length - 1) {
                                    val horPointRight = input[point.first][point.second + 1]
                                    val verPointDown = input[point.first + 1][point.second]
                                    if (horPointRight == region) {
                                        addToList(Pair(point.first, point.second + 1), graphList)
                                    } else {
                                        sides++
                                    }
                                    if (verPointDown == region) {
                                        addToList(Pair(point.first + 1, point.second), graphList)
                                    } else if (point.second == 0 || region != input[point.first][point.second - 1]) {
                                        sides++
                                    }
                                    if (point.first == 0) {
                                        if (point.second == 0 || region != input[0][point.second - 1]) {
                                            sides++
                                        }
                                    } else if (region != input[point.first - 1][point.second] && point.second > 0 && region != input[point.first][point.second - 1]) {
                                        sides++
                                    } else if (region != input[point.first - 1][point.second]) {
                                        sides++
                                    } else {
                                        addToList(Pair(point.first - 1, point.second), graphList)
                                    }
                                    if (point.second == 0) {
                                        sides++
                                    } else if (region != input[point.first][point.second - 1]) {
                                        sides++
                                    } else {
                                        addToList(Pair(point.first, point.second - 1), graphList)
                                    }
                                } else if (point.first < input.size - 1 && point.second == input.first().length - 1) {
                                    val verPoint = input[point.first + 1][point.second]
                                    if (point.first == 0) {
                                        sides += 1
                                    } else if (region != input[point.first - 1][point.second]) {
                                        sides++
                                    } else {
                                        addToList(Pair(point.first - 1, point.second), graphList)
                                    }
                                    if (verPoint == region) {
                                        addToList(Pair(point.first + 1, point.second), graphList)
                                    }
                                    if (region != input[point.first][point.second - 1]) {
                                        sides++
                                    } else {
                                        addToList(Pair(point.first, point.second - 1), graphList)
                                    }
                                    if (point.first in 1..<input.size && region != input[point.first + 1][point.second] && region != input[point.first - 1][point.second]) {
                                        sides++
                                    }
                                } else if (point.second < input.first().length - 1 && point.first == input.size - 1) {
                                    val horPoint = input[point.first][point.second + 1]
                                    if (horPoint == region) {
                                        addToList(Pair(point.first, point.second + 1), graphList)
                                    } else {
                                        sides++
                                    }
                                    if (point.second == 0) {
                                        sides++
                                    } else if (region != input[point.first][point.second - 1]) {
                                        sides++
                                    } else {
                                        addToList(Pair(point.first, point.second - 1), graphList)
                                    }
                                    if (region != input[point.first - 1][point.second]) {
                                        sides++
                                    } else {
                                        addToList(Pair(point.first - 1, point.second), graphList)
                                    }
                                    sides++
                                } else {
                                    if (region != input[point.first - 1][point.second]) {
                                        sides++
                                    } else {
                                        addToList(Pair(point.first - 1, point.second), graphList)
                                    }
                                    if (region != input[point.first][point.second - 1]) {
                                        sides++
                                    } else {
                                        addToList(Pair(point.first, point.second - 1), graphList)
                                    }
                                    sides += 2
                                }
                                visitedThisRun.add(point)
                                visitedRegion.add(point)
                            }
                            graphList.remove(point)
                        }
                    }
                    val alreadyShown = mutableListOf<Pair<Int, Int>>()
                    for (m in 0..<visitedThisRun.size) {
                        val acc = visitedThisRun[m]
                        val sortAfterMax = visitedThisRun.toMutableList()
                        sortAfterMax.sortByDescending { it.first }
                        val maxRow = sortAfterMax.first().second
                        val inRow = mutableListOf<Pair<Int, Int>>()
                        var rowWalker = acc
                        var hasNext = true
                        var rowCounter = sortAfterMax.last().second
                        while (hasNext) {
                            hasNext = false
                            visitedThisRun.find { }
                        }
                        alreadyShown.add(acc)
                    }
                }
            }
        }
        println(price)
    }


    private fun addToList(point: Pair<Int, Int>, list: MutableList<Pair<Int, Int>>) {
        if (!this.visitedRegion.contains(point)) {
            list.add(point)
        }
    }
}