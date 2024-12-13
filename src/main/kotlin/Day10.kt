class Day10 {
    fun partOne(input: List<String>) {
        var ways = mutableListOf<MutableList<Pair<Int, Int>>>()
        val fullTrailHeads = mutableListOf<MutableList<Pair<Int, Int>>>()
        val allTrailHeads = mutableListOf<MutableList<Pair<Int, Int>>>()
        // Find all 0
        input.forEachIndexed { rowIndex, line ->
            line.forEachIndexed { colIndex, char -> if (char == '0') ways.add(mutableListOf(Pair(rowIndex, colIndex))) }
        }
        var counter = 0
        while (ways.size > 0) {
            val furtherWays = mutableListOf<MutableList<Pair<Int, Int>>>()
            for (i in 0..<ways.size) {
                val lastElement = ways[i].last()
                if (lastElement.first > 0) {
                    if (input[lastElement.first - 1][lastElement.second].digitToInt() == ways[i].size && ways[i].size < 10) {
                        val updatedList = ways[i].toMutableList()
                        updatedList.add(Pair(lastElement.first - 1, lastElement.second))
                        furtherWays.add(updatedList)
                    }
                    if (ways[i].size == 10 && checkIfTrailheadNotExists(fullTrailHeads, ways[i])) {
                        fullTrailHeads.add(ways[i])
                    }
                    if (ways[i].size == 10 && !allTrailHeads.contains(ways[i])) {
                        allTrailHeads.add(ways[i])
                    }
                }
                if (lastElement.first < input.size - 1) {
                    if (input[lastElement.first + 1][lastElement.second].digitToInt() == ways[i].size && ways[i].size < 10) {
                        val updatedList = ways[i].toMutableList()
                        updatedList.add(Pair(lastElement.first + 1, lastElement.second))
                        furtherWays.add(updatedList)
                    }
                    if (ways[i].size == 10 && checkIfTrailheadNotExists(fullTrailHeads, ways[i])) {
                        fullTrailHeads.add(ways[i])
                    }
                    if (ways[i].size == 10 && !allTrailHeads.contains(ways[i])) {
                        allTrailHeads.add(ways[i])
                    }
                }
                if (lastElement.second > 0) {
                    if (input[lastElement.first][lastElement.second - 1].digitToInt() == ways[i].size && ways[i].size < 10) {
                        val updatedList = ways[i].toMutableList()
                        updatedList.add(Pair(lastElement.first, lastElement.second - 1))
                        furtherWays.add(updatedList)
                    }
                    if (ways[i].size == 10 && checkIfTrailheadNotExists(fullTrailHeads, ways[i])) {
                        fullTrailHeads.add(ways[i])
                    }
                    if (ways[i].size == 10 && !allTrailHeads.contains(ways[i])) {
                        allTrailHeads.add(ways[i])
                    }
                }
                if (lastElement.second < input.first().length - 1) {
                    if (input[lastElement.first][lastElement.second + 1].digitToInt() == ways[i].size && ways[i].size < 10) {
                        val updatedList = ways[i].toMutableList()
                        updatedList.add(Pair(lastElement.first, lastElement.second + 1))
                        furtherWays.add(updatedList)
                    }
                    if (ways[i].size == 10 && checkIfTrailheadNotExists(fullTrailHeads, ways[i])) {
                        fullTrailHeads.add(ways[i])
                    }
                    if (ways[i].size == 10 && !allTrailHeads.contains(ways[i])) {
                        allTrailHeads.add(ways[i])
                    }
                }
            }
            counter++
            ways = furtherWays
        }
        println(fullTrailHeads.size)
        println(allTrailHeads.size)
    }

    private fun checkIfTrailheadNotExists(
        fullTrailHeads: MutableList<MutableList<Pair<Int, Int>>>,
        fullTrailHead: MutableList<Pair<Int, Int>>
    ): Boolean {
        return fullTrailHeads.none { it.first() == fullTrailHead.first() && it.last() == fullTrailHead.last() }
    }
}