class Day162 {

    val wallList = mutableListOf<Pair<Int, Int>>()
    val visitedNotes = mutableListOf<Qudatruble>()
    var currPositions = mutableListOf<Triple<Int, Int, Char>>()
    lateinit var startPoint: Triple<Int, Int, Char>
    lateinit var endPoint: Pair<Int, Int>

    fun partOne(input: List<String>) {
        input.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, value ->
                if (value == '#') {
                    wallList.add(Pair(rowIndex, colIndex))
                } else if (value == 'S') {
                    startPoint = Triple(rowIndex, colIndex, '>')
                    visitedNotes.add(Qudatruble(rowIndex, colIndex, 0, '^'))
                    visitedNotes.add(Qudatruble(rowIndex, colIndex, 0, 'v'))
                    visitedNotes.add(Qudatruble(rowIndex, colIndex, 0, '<'))
                    visitedNotes.add(Qudatruble(rowIndex, colIndex, 0, '>'))
                } else if (value == 'E') {
                    endPoint = Pair(rowIndex, colIndex)
                    visitedNotes.add(Qudatruble(rowIndex, colIndex, 0, '^'))
                    visitedNotes.add(Qudatruble(rowIndex, colIndex, 0, 'v'))
                    visitedNotes.add(Qudatruble(rowIndex, colIndex, 0, '<'))
                    visitedNotes.add(Qudatruble(rowIndex, colIndex, 0, '>'))
                } else {
                    visitedNotes.add(Qudatruble(rowIndex, colIndex, 0, '^'))
                    visitedNotes.add(Qudatruble(rowIndex, colIndex, 0, 'v'))
                    visitedNotes.add(Qudatruble(rowIndex, colIndex, 0, '<'))
                    visitedNotes.add(Qudatruble(rowIndex, colIndex, 0, '>'))
                }
            }
        }

        if (canGoUp(startPoint)) {
            val updatedPosition = moveUp(startPoint)
            if (updatedPosition != null) currPositions.add(updatedPosition)
        }
        if (canGoDown(startPoint)) {
            val updatedPosition = moveDown(startPoint)
            if (updatedPosition != null) currPositions.add(updatedPosition)
        }
        if (canGoLeft(startPoint)) {
            val updatedPosition = moveLeft(startPoint)
            if (updatedPosition != null) currPositions.add(updatedPosition)
        }
        if (canGoRight(startPoint)) {
            val updatedPosition = moveRight(startPoint)
            if (updatedPosition != null) currPositions.add(updatedPosition)
        }

        while (currPositions.isNotEmpty()) {
            val currPos = currPositions.removeFirst()
            if (canGoUp(currPos)) {
                val updatedPos = moveUp(currPos)
                if (updatedPos != null) currPositions.add(updatedPos)
            }
            if (canGoDown(currPos)) {
                val updatedPos = moveDown(currPos)
                if (updatedPos != null) currPositions.add(updatedPos)
            }
            if (canGoLeft(currPos)) {
                val updatedPos = moveLeft(currPos)
                if (updatedPos != null) currPositions.add(updatedPos)
            }
            if (canGoRight(currPos)) {
                val updatedPos = moveRight(currPos)
                if (updatedPos != null) currPositions.add(updatedPos)
            }
        }
        val resultList = visitedNotes.filter { endPoint.first == it.first && endPoint.second == it.second }
        resultList.sortedBy { it.third }
        println(resultList.first().third)
    }

    private fun canGoLeft(pos: Triple<Int, Int, Char>): Boolean {
        return wallList.none { it.first == pos.first && it.second == pos.second - 1 }
    }

    private fun canGoUp(pos: Triple<Int, Int, Char>): Boolean {
        return wallList.none { it.second == pos.second && it.first == pos.first - 1 }
    }

    private fun canGoDown(pos: Triple<Int, Int, Char>): Boolean {
        return wallList.none { it.second == pos.second && it.first == pos.first + 1 }
    }

    private fun canGoRight(pos: Triple<Int, Int, Char>): Boolean {
        return wallList.none { it.first == pos.first && it.second == pos.second + 1 }
    }

    private fun moveUp(pos: Triple<Int, Int, Char>): Triple<Int, Int, Char>? {
        val node = visitedNotes.find { it.first == pos.first && pos.second == it.second && pos.third == it.fourth }!!
        val nextNode =
            visitedNotes.find { it.first == pos.first - 1 && pos.second == it.second && pos.third == it.fourth }!!
        return if (pos.third == '<' || pos.third == '>') {
            if (node.third + 1001 <= nextNode.third || nextNode.third == 0) {
                visitedNotes.remove(nextNode.copy(fourth = '^'))
                visitedNotes.add(nextNode.copy(third = node.third + 1001, fourth = '^'))
                pos.copy(first = pos.first - 1, third = '^')
            } else null
        } else if (pos.third == '^') {
            if (node.third + 1 <= nextNode.third || nextNode.third == 0) {
                visitedNotes.remove(nextNode.copy(fourth = '^'))
                visitedNotes.add(nextNode.copy(third = node.third + 1, fourth = '^'))
                pos.copy(first = pos.first - 1, third = '^')
            } else null
        } else {
            if (node.third + 2001 <= nextNode.third || nextNode.third == 0) {
                visitedNotes.remove(nextNode.copy(fourth = '^'))
                visitedNotes.add(nextNode.copy(third = node.third + 2001, fourth = '^'))
                pos.copy(first = pos.first - 1, third = '^')
            } else null
        }
    }

    private fun moveDown(pos: Triple<Int, Int, Char>): Triple<Int, Int, Char>? {
        val node = visitedNotes.find { it.first == pos.first && pos.second == it.second && pos.third == it.fourth }!!
        val nextNode =
            visitedNotes.find { it.first == pos.first + 1 && pos.second == it.second && pos.third == it.fourth }!!
        return if (pos.third == '<' || pos.third == '>') {
            if (node.third + 1001 <= nextNode.third || nextNode.third == 0) {
                visitedNotes.remove(nextNode.copy(fourth = 'v'))
                visitedNotes.add(nextNode.copy(third = node.third + 1001, fourth = 'v'))
                pos.copy(first = pos.first + 1, third = 'v')
            } else null
        } else if (pos.third == 'v') {
            if (node.third + 1 <= nextNode.third || nextNode.third == 0) {
                visitedNotes.remove(nextNode.copy(fourth = 'v'))
                visitedNotes.add(nextNode.copy(third = node.third + 1, fourth = 'v'))
                pos.copy(first = pos.first + 1, third = 'v')
            } else null
        } else {
            if (node.third + 2001 <= nextNode.third || nextNode.third == 0) {
                visitedNotes.remove(nextNode.copy(fourth = 'v'))
                visitedNotes.add(nextNode.copy(third = node.third + 2001, fourth = 'v'))
                pos.copy(first = pos.first + 1, third = 'v')
            } else null
        }
    }

    private fun moveLeft(pos: Triple<Int, Int, Char>): Triple<Int, Int, Char>? {
        val node = visitedNotes.find { it.first == pos.first && pos.second == it.second && pos.third == it.fourth }!!
        val nextNode =
            visitedNotes.find { it.first == pos.first && pos.second - 1 == it.second && pos.third == it.fourth }!!
        return if (pos.third == '^' || pos.third == 'v') {
            if (node.third + 1001 <= nextNode.third || nextNode.third == 0) {
                visitedNotes.remove(nextNode.copy(fourth = '<'))
                visitedNotes.add(nextNode.copy(third = node.third + 1001, fourth = '<'))
                pos.copy(second = pos.second - 1, third = '<')
            } else null
        } else if (pos.third == '<') {
            if (node.third + 1 <= nextNode.third || nextNode.third == 0) {
                visitedNotes.remove(nextNode.copy(fourth = '<'))
                visitedNotes.add(nextNode.copy(third = node.third + 1, fourth = '<'))
                pos.copy(second = pos.second - 1, third = '<')
            } else null
        } else {
            if (node.third + 2001 <= nextNode.third || nextNode.third == 0) {
                visitedNotes.remove(nextNode.copy(fourth = '<'))
                visitedNotes.add(nextNode.copy(third = node.third + 1, fourth = '<'))
                visitedNotes.add(nextNode.copy(third = node.third + 2001, fourth = '<'))
                pos.copy(second = pos.second - 1, third = '<')
            } else null
        }
    }

    private fun moveRight(pos: Triple<Int, Int, Char>): Triple<Int, Int, Char>? {
        val node = visitedNotes.find { it.first == pos.first && pos.second == it.second && pos.third == it.fourth }!!
        val nextNode =
            visitedNotes.find { it.first == pos.first && pos.second + 1 == it.second && pos.third == it.fourth }!!
        if (node.first == 7 && (node.second == 5 || node.second == 4)) {
            //println("$node, $nextNode")
        }
        return if (pos.third == '^' || pos.third == 'v') {
            if (node.third + 1001 <= nextNode.third || nextNode.third == 0) {
                visitedNotes.remove(nextNode.copy(fourth = '>'))
                visitedNotes.add(nextNode.copy(third = node.third + 1001, fourth = '>'))
                pos.copy(second = pos.second + 1, third = '>')
            } else null
        } else if (pos.third == '>') {
            if (node.third + 1 <= nextNode.third || nextNode.third == 0) {
                visitedNotes.remove(nextNode.copy(fourth = '>'))
                visitedNotes.add(
                    nextNode.copy(
                        third = node.third + 1,
                        fourth = '>'
                    )
                )
                pos.copy(second = pos.second + 1, third = '>')
            } else null
        } else {
            if (node.third + 2001 <= nextNode.third || nextNode.third == 0) {
                visitedNotes.remove(nextNode.copy(fourth = '>'))
                visitedNotes.add(nextNode.copy(third = node.third + 2001, fourth = '>'))
                pos.copy(second = pos.second + 1, third = '>')
            } else null
        }
    }
}

data class Qudatruble(
    val first: Int,
    val second: Int,
    val third: Int,
    val fourth: Char
)