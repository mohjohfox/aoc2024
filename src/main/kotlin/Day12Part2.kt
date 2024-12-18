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
                                    }
                                    if (verPointDown == region) {
                                        addToList(Pair(point.first + 1, point.second), graphList)
                                    }
                                    if (point.first == 0) {
                                    } else if (region != input[point.first - 1][point.second] && point.second > 0 && region != input[point.first][point.second - 1]) {
                                    } else if (region != input[point.first - 1][point.second]) {
                                    } else {
                                        addToList(Pair(point.first - 1, point.second), graphList)
                                    }
                                    if (point.second == 0) {
                                    } else if (region != input[point.first][point.second - 1]) {
                                    } else {
                                        addToList(Pair(point.first, point.second - 1), graphList)
                                    }
                                } else if (point.first < input.size - 1 && point.second == input.first().length - 1) {
                                    val verPoint = input[point.first + 1][point.second]
                                    if (point.first == 0) {
                                    } else if (region != input[point.first - 1][point.second]) {
                                    } else {
                                        addToList(Pair(point.first - 1, point.second), graphList)
                                    }
                                    if (verPoint == region) {
                                        addToList(Pair(point.first + 1, point.second), graphList)
                                    }
                                    if (region == input[point.first][point.second - 1]) {
                                        addToList(Pair(point.first, point.second - 1), graphList)
                                    }
                                } else if (point.second < input.first().length - 1 && point.first == input.size - 1) {
                                    val horPoint = input[point.first][point.second + 1]
                                    if (horPoint == region) {
                                        addToList(Pair(point.first, point.second + 1), graphList)
                                    }
                                    if (point.second == 0) {
                                    } else if (region != input[point.first][point.second - 1]) {
                                    } else {
                                        addToList(Pair(point.first, point.second - 1), graphList)
                                    }
                                    if (region != input[point.first - 1][point.second]) {
                                    } else {
                                        addToList(Pair(point.first - 1, point.second), graphList)
                                    }
                                } else {
                                    if (region == input[point.first - 1][point.second]) {
                                        addToList(Pair(point.first - 1, point.second), graphList)
                                    }
                                    if (region == input[point.first][point.second - 1]) {
                                        addToList(Pair(point.first, point.second - 1), graphList)
                                    }
                                }
                                visitedThisRun.add(point)
                                visitedRegion.add(point)
                            }
                            graphList.remove(point)
                        }
                    }
                    visitedThisRun.sortWith(compareBy<Pair<Int, Int>> { it.second }.thenBy { it.first })
                    val amountOfItems = visitedThisRun.size
                    var counter = 0
                    while (counter < visitedThisRun.size) {
                        val curr = visitedThisRun[counter++]
                        if (isDownLeftCorner(curr, visitedThisRun)) {
                            sides += 2
                        } else if (isDownRightCorner(curr, visitedThisRun)) {
                            sides += 2
                        } else if (isUpperRightCorner(curr, visitedThisRun)) {
                            sides += 2
                        } else if (isUpperLeftCorner(curr, visitedThisRun)) {
                            sides += 2
                        }
                    }
                    price += amountOfItems * sides
                }
            }
        }
        println(price)
    }

    private fun isDownLeftCorner(
        curr: Pair<Int, Int>,
        list: MutableList<Pair<Int, Int>>
    ): Boolean {
        return list.any { curr.first == it.first && curr.second + 1 == it.second } && list.any { curr.second == it.second && curr.first - 1 == it.first }
        //list.any { curr.first == it.f }
    }

    private fun isUpperLeftCorner(
        curr: Pair<Int, Int>,
        list: MutableList<Pair<Int, Int>>
    ): Boolean {
        return list.any { curr.first == it.first && curr.second + 1 == it.second } && list.any { curr.second == it.second && curr.first + 1 == it.first } ||
                list.any { curr.first == it.first && curr.second + 1 == it.first } ||
                list.any { curr.second == it.second && curr.first + 1 == it.first }
    }

    private fun isUpperRightCorner(
        curr: Pair<Int, Int>,
        list: MutableList<Pair<Int, Int>>
    ): Boolean {
        return list.any { curr.first == it.first && curr.second - 1 == it.second } && list.any { curr.second == it.second && curr.first + 1 == it.first }
    }

    private fun isDownRightCorner(
        curr: Pair<Int, Int>,
        list: MutableList<Pair<Int, Int>>
    ): Boolean {
        return list.any { curr.first == it.first && curr.second - 1 == it.second } && list.any { curr.second == it.second && curr.first - 1 == it.first }
    }

    private fun addToList(point: Pair<Int, Int>, list: MutableList<Pair<Int, Int>>) {
        if (!this.visitedRegion.contains(point)) {
            list.add(point)
        }
    }

}