class Day18 {

    var routes = mutableSetOf<Pair<Int, Int>>()
    var bits = mutableListOf<Pair<Int, Int>>()
    var visitedFields = mutableMapOf<Pair<Int, Int>, Int>()
    var amountOfBytestoFall = 1024
    val maxFieldSize = 70
    var foundFirstSolution = false

    fun partOne(input: List<String>) {
        for (i in 0..<amountOfBytestoFall) {
            val cord = input[i].split(",")
            bits.add(Pair(cord[0].toInt(), cord[1].toInt()))
        }

        val startPos = Pair(0, 0)
        val endPos = Pair(maxFieldSize, maxFieldSize) // Change this
        routes.add(startPos)
        visitedFields[startPos] = 0

        while (true) {
            val newRoutes = mutableSetOf<Pair<Int, Int>>()
            routes.forEach { route ->
                // Upper
                if (bits.none { it.first == route.first - 1 && it.second == route.second } && route.first - 1 >= 0) {
                    val pair = Pair(route.first - 1, route.second)
                    if (visitedFields[pair] == null) {
                        visitedFields[pair] = visitedFields[route]!! + 1
                        newRoutes.add(pair)
                    } else if (visitedFields[pair]!! > visitedFields[route]!!) {
                        visitedFields[pair] = visitedFields[route]!! + 1
                        newRoutes.add(pair)
                    }
                }
                // Down
                if (bits.none { it.first == route.first + 1 && it.second == route.second } && route.first + 1 <= endPos.first) {
                    val pair = Pair(route.first + 1, route.second)
                    if (visitedFields[pair] == null) {
                        visitedFields[pair] = visitedFields[route]!! + 1
                        newRoutes.add(pair)
                    } else if (visitedFields[pair]!! > visitedFields[route]!!) {
                        visitedFields[pair] = visitedFields[route]!! + 1
                        newRoutes.add(pair)
                    }
                }
                // Left
                if (bits.none { it.first == route.first && it.second == route.second - 1 } && route.second - 1 >= 0) {
                    val pair = Pair(route.first, route.second - 1)
                    if (visitedFields[pair] == null) {
                        visitedFields[pair] = visitedFields[route]!! + 1
                        newRoutes.add(pair)
                    } else if (visitedFields[pair]!! > visitedFields[route]!!) {
                        visitedFields[pair] = visitedFields[route]!! + 1
                        newRoutes.add(pair)
                    }
                }
                // Right
                if (bits.none { it.first == route.first && it.second == route.second + 1 } && route.second + 1 <= endPos.second) {
                    val pair = Pair(route.first, route.second + 1)
                    if (visitedFields[pair] == null) {
                        visitedFields[pair] = visitedFields[route]!! + 1
                        newRoutes.add(pair)
                    } else if (visitedFields[pair]!! > visitedFields[route]!!) {
                        visitedFields[pair] = visitedFields[route]!! + 1
                        newRoutes.add(pair)
                    }
                }
            }
            if (newRoutes.isEmpty()) {
                println(bits.last())
                break
            }
            routes = newRoutes
            if (visitedFields[endPos] != null && !foundFirstSolution) {
                println(visitedFields[endPos])
                foundFirstSolution = true
            }
            if (visitedFields[endPos] != null && foundFirstSolution) {
                visitedFields.clear()
                routes.clear()
                val cord = input[amountOfBytestoFall++].split(",")
                bits.add(Pair(cord[0].toInt(), cord[1].toInt()))
                routes.add(startPos)
                visitedFields[startPos] = 0
            }
        }
    }
}