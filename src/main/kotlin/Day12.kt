class Day12 {
    val visitedRegion = mutableListOf<Pair<Int, Int>>()
    fun partOne(input: List<String>) {
        var price = 0
        for (i in 0..<input.size) {
            for (k in 0..<input.first().length) {
                if (!visitedRegion.contains(Pair(i, k))) {
                    val region = input[i][k]
                    var fences = 0
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
                                        fences++
                                    }
                                    if (verPointDown == region) {
                                        addToList(Pair(point.first + 1, point.second), graphList)
                                    } else {
                                        fences++
                                    }
                                    if (point.first == 0) {
                                        fences++
                                    } else if (region != input[point.first - 1][point.second]) {
                                        fences++
                                    } else {
                                        addToList(Pair(point.first - 1, point.second), graphList)
                                    }
                                    if (point.second == 0) {
                                        fences++
                                    } else if (region != input[point.first][point.second - 1]) {
                                        fences++
                                    } else {
                                        addToList(Pair(point.first, point.second - 1), graphList)
                                    }
                                } else if (point.first < input.size - 1 && point.second == input.first().length - 1) {
                                    val verPoint = input[point.first + 1][point.second]
                                    if (point.first == 0) {
                                        fences += 1
                                    } else if (region != input[point.first - 1][point.second]) {
                                        fences++
                                    } else {
                                        addToList(Pair(point.first - 1, point.second), graphList)
                                    }
                                    if (verPoint == region) {
                                        addToList(Pair(point.first + 1, point.second), graphList)
                                    } else {
                                        fences++
                                    }
                                    if (region != input[point.first][point.second - 1]) {
                                        fences++
                                    } else {
                                        addToList(Pair(point.first, point.second - 1), graphList)
                                    }
                                    fences++ // Right border fence
                                } else if (point.second < input.first().length - 1 && point.first == input.size - 1) {
                                    val horPoint = input[point.first][point.second + 1]
                                    if (horPoint == region) {
                                        addToList(Pair(point.first, point.second + 1), graphList)
                                    } else {
                                        fences++
                                    }
                                    if (point.second == 0) {
                                        fences++
                                    } else if (region != input[point.first][point.second - 1]) {
                                        fences++
                                    } else {
                                        addToList(Pair(point.first, point.second - 1), graphList)
                                    }
                                    if (region != input[point.first - 1][point.second]) {
                                        fences++
                                    } else {
                                        addToList(Pair(point.first - 1, point.second), graphList)
                                    }
                                    fences++
                                } else {
                                    if (region != input[point.first - 1][point.second]) {
                                        fences++
                                    } else {
                                        addToList(Pair(point.first - 1, point.second), graphList)
                                    }
                                    if (region != input[point.first][point.second - 1]) {
                                        fences++
                                    } else {
                                        addToList(Pair(point.first, point.second - 1), graphList)
                                    }
                                    fences += 2
                                }
                                visitedThisRun.add(point)
                                visitedRegion.add(point)
                            }
                            graphList.remove(point)
                        }
                    }
                    price += visitedThisRun.size * fences
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