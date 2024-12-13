class Day6 {
    fun partOne(input: List<String>) {
        var row = 0
        var column = 0
        var soldier = ' '
        val visitedPlaces = mutableListOf<Triple<Char,Int, Int>>()
        val obstaclePlaces = mutableListOf<Pair<Int, Int>>()
        var ps2 = 0
        var startPos = Triple(' ', 0, 0)
        for (i in 0..<input.size) {
            for (k in 0..<input[i].length) {
                if (input[i][k] == '^' || input[i][k] == '<' || input[i][k] == '>' || input[i][k] == 'v') {
                    row = i
                    column = k
                    soldier = input[i][k]
                    visitedPlaces.add(Triple(soldier, row, column))
                    startPos = Triple(soldier ,row, column)
                }
            }
        }

        while(true) {
            if (soldier == '^' && row-1 >= 0) {
                if (input[row-1][column] != '#') {
                    if (placeObstacle(startPos, input, Pair(row-1, column))) {
                        obstaclePlaces.add(Pair(row-1, column))
                        println(Pair(row-1, column))
                        ps2++
                    }
                    row--
                } else {
                    soldier = '>'
                }
            } else if (soldier == 'v' && row+1 < input.size) {
                if (input[row+1][column] != '#') {
                    if (placeObstacle(startPos, input, Pair(row+1, column))) {
                        obstaclePlaces.add(Pair(row+1, column))
                        println(Pair(row+1, column))
                        ps2++
                    }
                    row++
                } else {
                    soldier = '<'
                }
            } else if (soldier == '<' && column -1 >= 0) {
                if (input[row][column-1] != '#') {
                    if (placeObstacle(startPos, input, Pair(row, column-1))) {
                        obstaclePlaces.add(Pair(row, column-1))
                        println(Pair(row, column-1))
                        ps2++
                    }
                    column--
                } else {
                    soldier = '^'
                }
            } else if (soldier == '>' && column+1 < input[row].length) {
                if (input[row][column+1] != '#') {
                    if (placeObstacle(startPos, input, Pair(row, column+1))) {
                        obstaclePlaces.add(Pair(row, column+1))
                        println(Pair(row, column+1))
                        ps2++
                    }
                   column++
                } else {
                    soldier = 'v'
                }
            } else {
                break
            }
            visitedPlaces.add(Triple(soldier ,row, column))
        }
        println("Visited: ${visitedPlaces.map { Pair(it.second, it.third) }.distinct().size}")
        println("PS2: ${obstaclePlaces.distinct().size}")
    }

    private fun placeObstacle(startPos: Triple<Char, Int, Int>, input: List<String>, obstaclePosition: Pair<Int, Int>): Boolean {
        var row = startPos.second
        var column = startPos.third
        var soldier = startPos.first
        val visitedPlaces = mutableListOf<Triple<Char,Int, Int>>()
        while(row < input.size && column < input[row].length) {
            if (soldier == '^' && row-1 >= 0) {
                if (input[row-1][column] != '#' && Pair(row-1, column) != obstaclePosition) {
                    row--
                } else {
                    soldier = '>'
                }
            } else if (soldier == 'v' && row+1 < input.size) {
                if (input[row+1][column] != '#' && Pair(row+1, column) != obstaclePosition) {
                    row++
                } else {
                    soldier = '<'
                }
            } else if (soldier == '<' && column -1 >= 0 ) {
                if (input[row][column-1] != '#' && Pair(row, column-1) != obstaclePosition) {
                    column--
                } else {
                    soldier = '^'
                }
            } else if (soldier == '>' && column+1 < input[row].length) {
                if (input[row][column+1] != '#' && Pair(row, column+1) != obstaclePosition) {
                    column++
                } else {
                    soldier = 'v'
                }
            } else {
                break
            }
            if (visitedPlaces.contains(Triple(soldier, row, column))) {
                return true
            }
            visitedPlaces.add(Triple(soldier ,row, column))
        }
        return false
    }
}