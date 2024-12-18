import java.util.regex.Pattern
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class Day14 {
    private val height = 103
    private val width = 101
    private val seconds = 10000

    fun partOne(input: List<String>) {
        val positionList = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        val pattern = Pattern.compile("\\-?\\d+")
        val varianceX = mutableListOf<Pair<Int, Double>>()
        val varianceY = mutableListOf<Pair<Int, Double>>()

        input.forEach { line ->
            val matcher = pattern.matcher(line)
            val argumentList = mutableListOf<Int>()
            while (matcher.find()) {
                val argument = matcher.group().toInt()
                argumentList.add(argument)
            }
            positionList.add(Pair(Pair(argumentList[0], argumentList[1]), Pair(argumentList[2], argumentList[3])))
        }

        var accPosList = positionList.toMutableList()

        for (i in 0..<seconds) {
            val updatedPosList = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
            for (m in 0..<accPosList.size) {
                val robotInfo = accPosList[m]
                val x = robotInfo.first.first
                val y = robotInfo.first.second
                val xVel = robotInfo.second.first
                val yVel = robotInfo.second.second

                val newX = if (x + xVel < 0) {
                    width + (x + xVel)
                } else if (x + xVel >= width) {
                    abs(width - (x + xVel))
                } else {
                    x + xVel
                }
                val newY = if (y + yVel < 0) {
                    height + (y + yVel)
                } else if (y + yVel >= height) {
                    abs(height - (y + yVel))
                } else {
                    y + yVel
                }
                val updatedRobotPos = Pair(newX, newY)
                updatedPosList.add(robotInfo.copy(first = updatedRobotPos))
            }
            accPosList = updatedPosList.toMutableList()
            val currVarianceX = calcVarianceForX(accPosList)
            val currVarianceY = calcVarianceForY(accPosList)
            varianceX.add(Pair(i, currVarianceX))
            varianceY.add(Pair(i, currVarianceY))
            if (currVarianceX < 22 && currVarianceY < 22) {
                printArray(accPosList, i)
            }
        }
        varianceX.sortBy { it.second }
        varianceY.sortBy { it.second }
        varianceX.removeAll { it.second >= 60 }
        varianceY.removeAll { it.second >= 60 }
        // Quadrant 1
        val q1 = accPosList.filter { it.first.first < width / 2 && it.first.second < height / 2 }.size
        // Quadrant 2
        val q2 = accPosList.filter { it.first.first < width / 2 && it.first.second > height / 2 }.size
        // Quadrant 3
        val q3 = accPosList.filter { it.first.first > width / 2 && it.first.second > height / 2 }.size
        // Quadrant 4
        val q4 = accPosList.filter { it.first.first > width / 2 && it.first.second < height / 2 }.size
        val safetyFactor = q1 * q2 * q3 * q4
        println(safetyFactor)
    }

    private fun printArray(pos: MutableList<Pair<Pair<Int, Int>, Pair<Int, Int>>>, second: Int) {
        val array = Array(height) { Array(width) { " " } }
        pos.forEach { currPos -> array[currPos.first.second][currPos.first.first] = "X" }

        for (i in 0..<array.size) {
            print("$second: ")
            for (j in 0..<array[i].size) {
                print(array[i][j])
            }
            println()
        }
    }

    private fun calcVarianceForX(pos: MutableList<Pair<Pair<Int, Int>, Pair<Int, Int>>>): Double {
        val average = pos.map { it.first.first }.average()
        val variance = pos.map { (it.first.first - average).pow(2) }.average()
        return sqrt(variance)
    }

    private fun calcVarianceForY(pos: MutableList<Pair<Pair<Int, Int>, Pair<Int, Int>>>): Double {
        val average = pos.map { it.first.second }.average()
        val variance = pos.map { (it.first.second - average).pow(2) }.average()
        return sqrt(variance)
    }

}