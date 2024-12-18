class Day15Part2 {

    val wallList = mutableListOf<Wall>()
    val boxes = mutableListOf<Box>()
    val moveList = mutableListOf<Char>()
    lateinit var robotPos: Pair<Int, Int>

    fun partOne(input: List<String>) {
        var moveInput = false
        input.forEachIndexed { rowIndex, line ->
            var newColIndex = 0
            if (line.isBlank()) {
                moveInput = true
            }
            if (!moveInput) {
                for (colIndex in 0..<line.length) {
                    if (line[colIndex] == '#') {
                        wallList.add(Wall(Pair(rowIndex, newColIndex), Pair(rowIndex, newColIndex + 1)))
                    } else if (line[colIndex] == 'O') {
                        boxes.add(Box(Pair(rowIndex, newColIndex), Pair(rowIndex, newColIndex + 1)))
                    } else if (line[colIndex] == '@') {
                        robotPos = rowIndex to newColIndex
                    }
                    newColIndex += 2
                }
            } else {
                line.forEach { char -> moveList.add(char) }
            }
        }

        moveList.forEach { move ->
            checkAndMoveRobot(robotPos, move, 0)
        }

        println("Final robot pos: $robotPos")
        boxes.forEach { println("${it.leftSide}, ${it.rightSide}") }
        val gps = boxes.sumOf { 100 * it.leftSide.first + it.leftSide.second }
        println(gps)
    }

    private fun checkAndMoveRobot(pos: Pair<Int, Int>, move: Char, depth: Int) {
        if (!hasRobotWallNextToIt(pos, move)) {
            if (hasRobotBoxNextToIt(pos, move)) {
                val couldMove = when (move) {
                    '^' -> {
                        checkAndMoveBox(
                            boxes.filter { (it.leftSide.second == pos.second && it.leftSide.first == pos.first - 1) || (it.rightSide.second == pos.second && it.rightSide.first == pos.first - 1) },
                            move,
                            depth + 1
                        )
                    }

                    '>' -> {
                        checkAndMoveBox(
                            boxes.filter { it.leftSide.first == pos.first && it.leftSide.second == pos.second + 1 },
                            move,
                            depth + 1
                        )
                    }

                    '<' -> {
                        checkAndMoveBox(
                            boxes.filter { it.rightSide.first == pos.first && it.rightSide.second == pos.second - 1 },
                            move,
                            depth + 1
                        )
                    }

                    'v' -> {
                        checkAndMoveBox(
                            boxes.filter { (it.leftSide.second == pos.second && it.leftSide.first == pos.first + 1) || (it.rightSide.second == pos.second && it.rightSide.first == pos.first + 1) },
                            move,
                            depth + 1
                        )
                    }

                    else -> {
                        false
                    }
                }
                if (couldMove) {
                    moveRobot(move)
                }
            } else {
                moveRobot(move)
            }
        }
    }

    private fun checkAndMoveBox(foundBoxes: List<Box>, move: Char, depth: Int): Boolean {
        check(foundBoxes.isNotEmpty())
        foundBoxes.forEach { box ->
            if (!hasBoxWallNextToIt(box, move)) {
                if (hasBoxNextToIt(box, move)) {
                    val couldMove = when (move) {
                        '^' -> {
                            checkAndMoveBox(boxes.filter {
                                it.leftSide.first == box.leftSide.first - 1 && (it.leftSide.second == box.leftSide.second || it.rightSide.second == box.leftSide.second || it.leftSide.second == box.rightSide.second)
                            }, move, depth + 1)
                        }

                        '>' -> {
                            checkAndMoveBox(
                                boxes.filter { it.leftSide.first == box.leftSide.first && it.leftSide.second == box.rightSide.second + 1 },
                                move,
                                depth + 1
                            )
                        }

                        '<' -> {
                            checkAndMoveBox(
                                boxes.filter { it.leftSide.first == box.leftSide.first && it.rightSide.second == box.leftSide.second - 1 },
                                move,
                                depth + 1
                            )
                        }

                        'v' -> {
                            checkAndMoveBox(boxes.filter {
                                it.leftSide.first == box.leftSide.first + 1 && (it.leftSide.second == box.leftSide.second || it.rightSide.second == box.leftSide.second || it.leftSide.second == box.rightSide.second)
                            }, move, depth + 1)
                        }

                        else -> {
                            false
                        }
                    }
                    if (couldMove) {
                        moveBox(box, move)
                        return true
                    }
                } else {
                    moveBox(box, move)
                    return true
                }
            }
            return false
        }
        return false
    }

    private fun moveBox(box: Box, move: Char) {
        boxes.remove(box)
        when (move) {
            '^' -> {
                val leftSide = box.leftSide.copy(first = box.leftSide.first - 1)
                val rightSide = box.rightSide.copy(first = box.rightSide.first - 1)
                boxes.add(Box(leftSide, rightSide))
            }

            '>' -> {
                val leftSide = box.leftSide.copy(second = box.leftSide.second + 1)
                val rightSide = box.rightSide.copy(second = box.rightSide.second + 1)
                boxes.add(Box(leftSide, rightSide))
            }

            '<' -> {
                val leftSide = box.leftSide.copy(second = box.leftSide.second - 1)
                val rightSide = box.rightSide.copy(second = box.rightSide.second - 1)
                boxes.add(Box(leftSide, rightSide))
            }

            'v' -> {
                val leftSide = box.leftSide.copy(first = box.leftSide.first + 1)
                val rightSide = box.rightSide.copy(first = box.rightSide.first + 1)
                boxes.add(Box(leftSide, rightSide))
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

    private fun hasRobotBoxNextToIt(pair: Pair<Int, Int>, move: Char): Boolean {
        return when (move) {
            '^' -> {
                boxes.any { it.leftSide.first == pair.first - 1 && (it.leftSide.second == pair.second || it.rightSide.second == pair.second) }
            }

            '>' -> {
                boxes.any { it.leftSide.first == pair.first && it.leftSide.second == pair.second + 1 }
            }

            '<' -> {
                boxes.any { it.leftSide.first == pair.first && it.rightSide.second == pair.second - 1 }
            }

            'v' -> {
                boxes.any { it.leftSide.first == pair.first + 1 && (it.leftSide.second == pair.second || it.rightSide.second == pair.second) }
            }

            else -> {
                false
            }
        }
    }

    private fun hasBoxNextToIt(box: Box, move: Char): Boolean {
        return when (move) {
            '^' -> {
                boxes.any {
                    it.leftSide.first == box.leftSide.first - 1 && (it.leftSide.second == box.leftSide.second ||
                            it.rightSide.second == box.leftSide.second ||
                            it.leftSide.second == box.rightSide.second)
                }
            }

            '>' -> {
                boxes.any { it.leftSide.first == box.leftSide.first && it.leftSide.second == box.rightSide.second + 1 }
            }

            '<' -> {
                boxes.any { it.leftSide.first == box.leftSide.first && it.rightSide.second == box.leftSide.second - 1 }
            }

            'v' -> {
                boxes.any {
                    it.leftSide.first == box.leftSide.first + 1 && (it.leftSide.second == box.leftSide.second ||
                            it.rightSide.second == box.leftSide.second ||
                            it.leftSide.second == box.rightSide.second)
                }
            }

            else -> {
                false
            }
        }
    }

    private fun hasRobotWallNextToIt(pair: Pair<Int, Int>, move: Char): Boolean {
        return when (move) {
            '^' -> {
                wallList.any { it.leftSide.first == pair.first - 1 && it.leftSide.second == pair.second || it.rightSide.first == pair.first - 1 && it.rightSide.second == pair.second }
            }

            '>' -> {
                wallList.any { it.leftSide.first == pair.first && it.leftSide.second == pair.second + 1 }
            }

            '<' -> {
                wallList.any { it.leftSide.first == pair.first && it.rightSide.second == pair.second - 1 }
            }

            'v' -> {
                wallList.any { it.leftSide.first == pair.first + 1 && it.leftSide.second == pair.second || it.rightSide.first == pair.first + 1 && it.rightSide.second == pair.second }
            }

            else -> {
                false
            }
        }
    }

    private fun hasBoxWallNextToIt(box: Box, move: Char): Boolean {
        return when (move) {
            '^' -> {
                wallList.any {
                    it.leftSide.first == box.leftSide.first - 1 && (it.leftSide.second == box.leftSide.second ||
                            it.rightSide.second == box.leftSide.second ||
                            it.leftSide.second == box.rightSide.second)
                }
            }

            '>' -> {
                wallList.any { it.leftSide.first == box.leftSide.first && it.leftSide.second == box.rightSide.second + 1 }
            }

            '<' -> {
                wallList.any { it.leftSide.first == box.leftSide.first && it.rightSide.second == box.leftSide.second - 1 }
            }

            'v' -> {
                wallList.any {
                    it.leftSide.first == box.leftSide.first + 1 && (it.leftSide.second == box.leftSide.second ||
                            it.rightSide.second == box.leftSide.second ||
                            it.leftSide.second == box.rightSide.second)
                }
            }

            else -> {
                false
            }
        }
    }
}

data class Box(
    val leftSide: Pair<Int, Int>,
    val rightSide: Pair<Int, Int>,
)

data class Wall(
    val leftSide: Pair<Int, Int>,
    val rightSide: Pair<Int, Int>,
)