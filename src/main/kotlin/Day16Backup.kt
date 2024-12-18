class Day16Backup {

    val wallList = mutableListOf<Pair<Int, Int>>()
    val freePos = mutableListOf<Pair<Int, Int>>()
    lateinit var startPoint: Triple<Int, Int, Char>
    lateinit var endPoint: Pair<Int, Int>
    val graph = mutableListOf<Route>()
    private var cost = 0

    fun partOne(input: List<String>) {
        var loopCounter = 0
        input.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, value ->
                if (value == '#') {
                    wallList.add(Pair(rowIndex, colIndex))
                } else if (value == 'S') {
                    startPoint = Triple(rowIndex, colIndex, '>')
                } else if (value == 'E') {
                    endPoint = Pair(rowIndex, colIndex)
                } else {
                    freePos.add(Pair(rowIndex, colIndex))
                }
            }
        }

        val firstRoute = Route(mutableListOf(startPoint), 0)
        if (canGoUp(firstRoute, true)) {
            val updatedRoute = moveUp(firstRoute.copy())
            graph.addFirst(updatedRoute)
        }
        if (canGoDown(firstRoute, true)) {
            val updatedRoute = moveDown(firstRoute.copy())
            graph.addFirst(updatedRoute)
        }
        if (canGoLeft(firstRoute, true)) {
            val updatedRoute = moveLeft(firstRoute.copy())
            graph.addFirst(updatedRoute)
        }
        if (canGoRight(firstRoute, true)) {
            val updatedRoute = moveRight(firstRoute.copy())
            graph.addFirst(updatedRoute)
        }

        while (graph.isNotEmpty()) {
            if (loopCounter++ % 10000 == 0) {
                printField()
                println("Size: ${graph.size}")
            }
            graph.sortBy { it.cost }
            val cheapestRouter = graph.removeFirst()
            val lastPos = cheapestRouter.getLastPosition()

            if (lastPos.first == endPoint.first && lastPos.second == endPoint.second) {
                if (cheapestRouter.cost < cost || cost == 0) {
                    println("Found something")
                    cost = cheapestRouter.cost
                }
                break
            }

            if (canGoUp(cheapestRouter)) {
                val updatedRoute = moveUp(cheapestRouter.copy())
                graph.addFirst(updatedRoute)
            }
            if (canGoDown(cheapestRouter)) {
                val updatedRoute = moveDown(cheapestRouter.copy())
                graph.addFirst(updatedRoute)
            }
            if (canGoLeft(cheapestRouter)) {
                val updatedRoute = moveLeft(cheapestRouter.copy())
                graph.addFirst(updatedRoute)
            }
            if (canGoRight(cheapestRouter)) {
                val updatedRoute = moveRight(cheapestRouter.copy())
                graph.addFirst(updatedRoute)
            }
        }

        println(cost)
    }

    private fun printField() {
        graph.distinctBy { it.mutableList.last() }
        graph.forEach { println(it.mutableList.last()) }
    }

    private fun canGoLeft(route: Route, start: Boolean = false): Boolean {
        val pos = route.getLastPosition()
        //return wallList.none { it.first == pos.first && it.second == pos.second - 1 } && (pos.third != '>' || start)
        return wallList.none { it.first == pos.first && it.second == pos.second - 1 } && route.mutableList.none { it.first == pos.first && it.second == pos.second - 1 }
    }

    private fun canGoUp(route: Route, start: Boolean = false): Boolean {
        val pos = route.getLastPosition()
        //return wallList.none { it.second == pos.second && it.first == pos.first - 1 } && (pos.third != 'v' || start)
        return wallList.none { it.second == pos.second && it.first == pos.first - 1 } && route.mutableList.none { it.first == pos.first - 1 && it.second == pos.second }
    }

    private fun canGoDown(route: Route, start: Boolean = false): Boolean {
        val pos = route.getLastPosition()
        //return wallList.none { it.second == pos.second && it.first == pos.first + 1 } && (pos.third != '^' || start)
        return wallList.none { it.second == pos.second && it.first == pos.first + 1 } && route.mutableList.none { it.first == pos.first + 1 && it.second == pos.second }
    }

    private fun canGoRight(route: Route, start: Boolean = false): Boolean {
        val pos = route.getLastPosition()
        //return wallList.none { it.first == pos.first && it.second == pos.second + 1 } && (pos.third != '<' || start)
        return wallList.none { it.first == pos.first && it.second == pos.second + 1 } && route.mutableList.none { it.first == pos.first && it.second == pos.second + 1 }
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