class Day2 {

    fun partOne(input: List<String>): Int {
        var sumOfCorrectReports = 0
        input.forEach { report ->
            val levels = report.split(" ").map { it.toInt() }
            if (isValid(1, levels)) {
                sumOfCorrectReports++
            }
        }
        return sumOfCorrectReports
    }

    fun partTwo(input: List<String>): Int {
        var sumOfCorrectReports = 0
        input.forEach { report ->
            val levels = report.split(" ").map { it.toInt() }
            if (isValid(0, levels)) {
                sumOfCorrectReports++
            }
        }
        return sumOfCorrectReports
    }

    private fun isValid(depth: Int = 0, levels: List<Int>): Boolean {
        val order = if (levels[0] <= levels[1]) 'i' else 'd'
        var i = 0
        var correctLevels = 0
        while(i < levels.size - 1) {
            val abs = levels[i] - levels[i+1]
            if (order == 'i') {
                if (abs in -1 downTo -3) {
                    correctLevels++
                } else if (depth == 0) {
                    for (i in 0..<levels.size) {
                        val newList = levels.toMutableList()
                        newList.removeAt(i)
                        if (isValid(1, newList)) {
                            return true
                        }
                    }
                }
            } else {
                if (abs in 1..3) {
                    correctLevels++
                } else if (depth == 0) {
                    for (i in 0..<levels.size) {
                        val newList = levels.toMutableList()
                        newList.removeAt(i)
                        if (isValid(1, newList)) {
                            return true
                        }
                    }
                }
            }
            i++
        }
        return (correctLevels == levels.size - 1)
    }
}