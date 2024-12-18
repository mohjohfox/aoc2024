class Day15 {

    val wallList = mutableListOf<Pair<Int, Int>>()
    val boxes = mutableListOf<Pair<Int, Int>>()
    val moveList = mutableListOf<Char>()
    lateinit var robotPos: Pair<Int, Int>

    fun partOne(input: List<String>) {
        var moveInput = false
        input.forEachIndexed { rowIndex, line ->
            if (line.isBlank()) {
                moveInput = true
            }
            if (!moveInput) {
                line.forEachIndexed { colIndex, char ->
                    if (char == '#') {
                        wallList.add(Pair(rowIndex, colIndex))
                    } else if (char == 'O') {
                        boxes.add(Pair(rowIndex, colIndex))
                    } else if (char == '@') {
                        robotPos = rowIndex to colIndex
                    }
                }
            } else {
                line.forEach { char -> moveList.add(char) }
            }
        }

        moveList.forEach { move ->
            checkAndMove(robotPos, move, 0)
        }

        val result1 = boxes.sumOf { 100 * it.first + it.second }
        println(result1)
    }

    private fun checkAndMove(pos: Pair<Int, Int>, move: Char, depth: Int): Boolean {
        if (!hasWallNextToIt(pos, move)) {
            if (hasBoxNextToIt(pos, move)) {
                val couldMove = when (move) {
                    '^' -> {
                        checkAndMove(pos.copy(first = pos.first - 1), move, depth + 1)
                    }

                    '>' -> {
                        checkAndMove(pos.copy(second = pos.second + 1), move, depth + 1)
                    }

                    '<' -> {
                        checkAndMove(pos.copy(second = pos.second - 1), move, depth + 1)
                    }

                    'v' -> {
                        checkAndMove(pos.copy(first = pos.first + 1), move, depth + 1)
                    }

                    else -> {
                        false
                    }
                }
                if (couldMove) {
                    if (depth == 0) {
                        moveRobot(move)
                    } else {
                        moveBox(pos, move)
                    }
                    return true
                }
            } else {
                if (depth == 0) {
                    moveRobot(move)
                } else {
                    moveBox(pos, move)
                }
                return true
            }
        }
        return false
    }

    private fun moveBox(pos: Pair<Int, Int>, move: Char) {
        boxes.remove(pos)
        when (move) {
            '^' -> {
                boxes.add(pos.copy(first = pos.first - 1))
            }

            '>' -> {
                boxes.add(pos.copy(second = pos.second + 1))
            }

            '<' -> {
                boxes.add(pos.copy(second = pos.second - 1))
            }

            'v' -> {
                boxes.add(pos.copy(first = pos.first + 1))
            }
        }
    }

    private fun moveRobot(move: Char) {
        when (move) {
            '^' -> {
                robotPos = (robotPos.copy(first = robotPos.first - 1))
            }

            '>' -> {
                robotPos = (robotPos.copy(second = robotPos.second + 1))
            }

            '<' -> {
                robotPos = (robotPos.copy(second = robotPos.second - 1))
            }

            'v' -> {
                robotPos = (robotPos.copy(first = robotPos.first + 1))
            }
        }
    }

    private fun hasBoxNextToIt(pair: Pair<Int, Int>, move: Char): Boolean {
        return when (move) {
            '^' -> {
                boxes.contains(pair.copy(first = pair.first - 1))
            }

            '>' -> {
                boxes.contains(pair.copy(second = pair.second + 1))
            }

            '<' -> {
                boxes.contains(pair.copy(second = pair.second - 1))
            }

            'v' -> {
                boxes.contains(pair.copy(first = pair.first + 1))
            }

            else -> {
                false
            }
        }
    }

    private fun hasWallNextToIt(pair: Pair<Int, Int>, move: Char): Boolean {
        return when (move) {
            '^' -> {
                wallList.contains(pair.copy(first = pair.first - 1))
            }

            '>' -> {
                wallList.contains(pair.copy(second = pair.second + 1))
            }

            '<' -> {
                wallList.contains(pair.copy(second = pair.second - 1))
            }

            'v' -> {
                wallList.contains(pair.copy(first = pair.first + 1))
            }

            else -> {
                false
            }
        }
    }

}