class Day16 {

    val wallList = mutableListOf<Pair<Int, Int>>()
    val freePos = mutableListOf<Pair<Int, Int>>()
    lateinit var startPoint: Triple<Int, Int, Char>
    lateinit var endPoint: Pair<Int, Int>
    val graph = mutableListOf<Route>()
    private var cost = 0
    val posMap = mutableMapOf<Triple<Int, Int, Char>, Int>()
    var seats = mutableListOf<Pair<Int, Int>>()

    fun partOne(input: List<String>) {
        var loopCounter = 0
        input.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, value ->
                if (value == '#') {
                    wallList.add(Pair(rowIndex, colIndex))
                } else if (value == 'S') {
                    startPoint = Triple(rowIndex, colIndex, '>')
                    posMap[Triple(rowIndex, colIndex, '^')] = 0
                    posMap[Triple(rowIndex, colIndex, 'v')] = 0
                    posMap[Triple(rowIndex, colIndex, '<')] = 0
                    posMap[Triple(rowIndex, colIndex, '>')] = 0
                } else if (value == 'E') {
                    endPoint = Pair(rowIndex, colIndex)
                    posMap[Triple(rowIndex, colIndex, '^')] = 0
                    posMap[Triple(rowIndex, colIndex, 'v')] = 0
                    posMap[Triple(rowIndex, colIndex, '<')] = 0
                    posMap[Triple(rowIndex, colIndex, '>')] = 0
                } else {
                    freePos.add(Pair(rowIndex, colIndex))
                    posMap[Triple(rowIndex, colIndex, '^')] = 0
                    posMap[Triple(rowIndex, colIndex, 'v')] = 0
                    posMap[Triple(rowIndex, colIndex, '<')] = 0
                    posMap[Triple(rowIndex, colIndex, '>')] = 0
                }
            }
        }

        val firstRoute = Route(mutableListOf(startPoint), 0)
        if (canGoUp(firstRoute, true)) {
            val updatedRoute = moveUp(firstRoute.copy())
            val last = updatedRoute.getLastPosition()
            if (posMap[Triple(last.first, last.second, '^')]!! == 0 || posMap[Triple(
                    last.first,
                    last.second, '^'
                )]!! >= updatedRoute.cost
            ) {
                graph.addFirst(updatedRoute)
                posMap[Triple(last.first, last.second, '^')] = updatedRoute.cost
            }
        }
        if (canGoDown(firstRoute, true)) {
            val updatedRoute = moveDown(firstRoute.copy())
            val last = updatedRoute.getLastPosition()
            if (posMap[Triple(last.first, last.second, 'v')]!! == 0 || posMap[Triple(
                    last.first,
                    last.second, 'v'
                )]!! >= updatedRoute.cost
            ) {
                graph.addFirst(updatedRoute)
                posMap[Triple(last.first, last.second, 'v')] = updatedRoute.cost
            }
        }
        if (canGoLeft(firstRoute, true)) {
            val updatedRoute = moveLeft(firstRoute.copy())
            val last = updatedRoute.getLastPosition()
            if (posMap[Triple(last.first, last.second, '<')]!! == 0 || posMap[Triple(
                    last.first,
                    last.second, '<'
                )]!! >= updatedRoute.cost
            ) {
                graph.addFirst(updatedRoute)
                posMap[Triple(last.first, last.second, '<')] = updatedRoute.cost
            }
        }
        if (canGoRight(firstRoute, true)) {
            val updatedRoute = moveRight(firstRoute.copy())
            val last = updatedRoute.getLastPosition()
            if (posMap[Triple(last.first, last.second, '>')]!! == 0 || posMap[Triple(
                    last.first,
                    last.second, '>'
                )]!! >= updatedRoute.cost
            ) {
                graph.addFirst(updatedRoute)
                posMap[Triple(last.first, last.second, '>')] = updatedRoute.cost
            }
        }

        while (graph.isNotEmpty()) {
            graph.sortBy { it.cost }
            //printField()
            val cheapestRouter = graph.removeFirst()
            val lastPos = cheapestRouter.getLastPosition()

            if (lastPos.first == endPoint.first && lastPos.second == endPoint.second) {
                if (cheapestRouter.cost <= cost || cost == 0) {
                    println("Found something")
                    cost = cheapestRouter.cost
                    seats.addAll(cheapestRouter.mutableList.map { Pair(it.first, it.second) })
                }
                continue
            }

            if (canGoUp(cheapestRouter)) {
                val updatedRoute = moveUp(cheapestRouter.copy())
                val last = updatedRoute.getLastPosition()
                if (posMap[Triple(last.first, last.second, '^')]!! == 0 || posMap[Triple(
                        last.first,
                        last.second, '^'
                    )]!! >= updatedRoute.cost
                ) {
                    graph.addFirst(updatedRoute)
                    posMap[Triple(last.first, last.second, '^')] = updatedRoute.cost
                }
            }
            if (canGoDown(cheapestRouter)) {
                val updatedRoute = moveDown(cheapestRouter.copy())
                val last = updatedRoute.getLastPosition()
                if (posMap[Triple(last.first, last.second, 'v')]!! == 0 || posMap[Triple(
                        last.first,
                        last.second, 'v'
                    )]!! >= updatedRoute.cost
                ) {
                    graph.addFirst(updatedRoute)
                    posMap[Triple(last.first, last.second, 'v')] = updatedRoute.cost
                }
            }
            if (canGoLeft(cheapestRouter)) {
                val updatedRoute = moveLeft(cheapestRouter.copy())
                val last = updatedRoute.getLastPosition()
                if (posMap[Triple(last.first, last.second, '<')]!! == 0 || posMap[Triple(
                        last.first,
                        last.second, '<'
                    )]!! >= updatedRoute.cost
                ) {
                    graph.addFirst(updatedRoute)
                    posMap[Triple(last.first, last.second, '<')] = updatedRoute.cost
                }
            }
            if (canGoRight(cheapestRouter)) {
                val updatedRoute = moveRight(cheapestRouter.copy())
                val last = updatedRoute.getLastPosition()
                if (posMap[Triple(last.first, last.second, '>')]!! == 0 || posMap[Triple(
                        last.first,
                        last.second, '>'
                    )]!! >= updatedRoute.cost
                ) {
                    graph.addFirst(updatedRoute)
                    posMap[Triple(last.first, last.second, '>')] = updatedRoute.cost
                }
            }
        }

        println(cost)
        println(seats.distinctBy { Pair(it.first, it.second) }.size)
    }

    private fun canGoLeft(route: Route, start: Boolean = false): Boolean {
        val pos = route.getLastPosition()
        return wallList.none { it.first == pos.first && it.second == pos.second - 1 }
    }

    private fun canGoUp(route: Route, start: Boolean = false): Boolean {
        val pos = route.getLastPosition()
        return wallList.none { it.second == pos.second && it.first == pos.first - 1 }
    }

    private fun canGoDown(route: Route, start: Boolean = false): Boolean {
        val pos = route.getLastPosition()
        return wallList.none { it.second == pos.second && it.first == pos.first + 1 }
    }

    private fun canGoRight(route: Route, start: Boolean = false): Boolean {
        val pos = route.getLastPosition()
        return wallList.none { it.first == pos.first && it.second == pos.second + 1 }
    }

    private fun moveUp(route: Route): Route {
        val pos = route.getLastPosition()
        val newList = route.mutableList.toMutableList()
        newList.add(Triple(pos.first - 1, pos.second, '^'))
        return if (pos.third == '<' || pos.third == '>') {
            Route(newList, route.cost + 1001)
        } else if (pos.third == '^') {
            Route(newList, route.cost + 1)
        } else {
            Route(newList, route.cost + 2001)
        }
    }

    private fun moveDown(route: Route): Route {
        val pos = route.getLastPosition()
        val newList = route.mutableList.toMutableList()
        newList.add(Triple(pos.first + 1, pos.second, 'v'))
        return if (pos.third == '<' || pos.third == '>') {
            Route(newList, route.cost + 1001)
        } else if (pos.third == 'v') {
            Route(newList, route.cost + 1)
        } else {
            Route(newList, route.cost + 2001)
        }
    }

    private fun moveLeft(route: Route): Route {
        val pos = route.getLastPosition()
        val newList = route.mutableList.toMutableList()
        newList.add(Triple(pos.first, pos.second - 1, '<'))
        return if (pos.third == '^' || pos.third == 'v') {
            Route(newList, route.cost + 1001)
        } else if (pos.third == '<') {
            Route(newList, route.cost + 1)
        } else {
            Route(newList, route.cost + 2001)
        }
    }

    private fun moveRight(route: Route): Route {
        val pos = route.getLastPosition()
        val newList = route.mutableList.toMutableList()
        newList.add(Triple(pos.first, pos.second + 1, '>'))
        return if (pos.third == '^' || pos.third == 'v') {
            Route(newList, route.cost + 1001)
        } else if (pos.third == '>') {
            Route(newList, route.cost + 1)
        } else {
            Route(newList, route.cost + 2001)
        }
    }
}

data class Route(
    val mutableList: MutableList<Triple<Int, Int, Char>>,
    var cost: Int,
) {

    fun getLastPosition(): Triple<Int, Int, Char> {
        return mutableList.last()
    }

}