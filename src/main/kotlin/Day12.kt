class Day12 {
    fun partOne(input: List<String>) {
        var price = 0
        val visitedRegion = mutableListOf<Pair<Int, Int>>()
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
                                    val horPoint = input[point.first][point.second + 1]
                                    val verPoint = input[point.first + 1][point.second]
                                    if (horPoint == region) {
                                        graphList.add(Pair(point.first, point.second + 1))
                                    } else {
                                        fences++
                                    }
                                    if (verPoint == region) {
                                        graphList.add(Pair(point.first + 1, point.second))
                                    } else {
                                        fences++
                                    }
                                    if (point.first == 0) {
                                        fences++
                                    } else if (region != input[point.first - 1][point.second]) {
                                        fences++
                                    }
                                    if (point.second == 0) {
                                        fences++
                                    } else if (region != input[point.first][point.second - 1]) {
                                        fences++
                                    }
                                } else if (point.first < input.size - 1) {
                                    val verPoint = input[point.first + 1][point.second]
                                    if (verPoint == region) {
                                        graphList.add(Pair(point.first + 1, point.second))
                                        fences += 2
                                    } else {
                                        if (point.first != 0 && region != input[point.first - 1][point.second]) {
                                            fences += 4
                                        } else {
                                            fences += 3
                                        }
                                    }
                                } else if (point.second < input.first().length - 1) {
                                    val horPoint = input[point.first][point.second + 1]
                                    if (horPoint == region) {
                                        graphList.add(Pair(point.first, point.second + 1))
                                    } else {
                                        fences++
                                    }
                                    if (point.second == 0) {
                                        fences++
                                    }
                                    if (region != input[point.first - 1][point.second]) {
                                        fences++
                                    }
                                    fences++
                                } else {
                                    if (point.first == input.size - 1) {
                                        fences++
                                    }
                                    if (point.second == input.first().length - 1) {
                                        fences++
                                    }
                                    if (region != input[point.first - 1][point.second]) {
                                        fences++
                                    }
                                    if (region != input[point.first][point.second - 1]) {
                                        fences++
                                    }
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
}