class Day2 {

    fun partOne(input: List<String>): Int {
        var sumOfCorrectReports = 0
        input.forEach { report ->
            val levels = report.split(" ").map { it.toInt() }
            val order = if (levels[0] <= levels[1]) 'i' else 'd'
            var i = 0
            var correctLevels = 0
            while(i < levels.size - 1) {
                val abs = levels[i] - levels[i+1]
                if (order == 'i') {
                    if (abs in -1 downTo -3) {
                        correctLevels++
                    }
                } else {
                    if (abs in 1..3) {
                        correctLevels++
                    }
                }
                i++
            }
            if (correctLevels == levels.size - 1) {
                sumOfCorrectReports++
            }
        }
        return sumOfCorrectReports
    }

    fun partTwo(input: List<String>): Int {
        var sumOfCorrectReports = 0
        input.forEach { report ->
            val levels = report.split(" ").map { it.toInt() }
            val order = if (levels[0] <= levels[1]) 'i' else 'd'
            var i = 0
            var correctLevels = 0
            var skipCounter = 0
            while(i < levels.size - 1) {
                val abs = levels[i] - levels[i+1]
                if (order == 'i') {
                    if (abs in -1 downTo -3) {
                        correctLevels++
                    } else if (skipCounter < 1 && couldSkip(i, levels, order)) {
                        skipCounter++
                    }
                } else {
                    if (abs in 1..3) {
                        correctLevels++
                    } else if (skipCounter < 1 && couldSkip(i, levels, order)) {
                        skipCounter++
                    }
                }
                i++
            }
            if (correctLevels == levels.size - 1 || (skipCounter == 1 && correctLevels == levels.size - 2)) {
                sumOfCorrectReports++
            }
        }
        return sumOfCorrectReports
    }

    private fun couldSkip(pointer: Int, levels: List<Int>, order: Char): Boolean {
        if (pointer < levels.size-2) {
            val abs = levels[pointer] - levels[pointer+2]
            if (order == 'i') {
                if (abs in -1 downTo -3) {
                    return true
                }
            } else {
                if (abs in 1..3) {
                    return true
                }
            }
        }
        return false
    }
}