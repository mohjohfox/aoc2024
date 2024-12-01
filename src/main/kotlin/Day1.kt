import kotlin.math.abs
import kotlin.time.times

class Day1 {

    fun partOne(input: List<String>): Int {
        val leftSide = mutableListOf<Int>()
        val rightSide = mutableListOf<Int>()
        input.forEach { line ->
            val splittedLine = line.split(" ")
            leftSide.add(splittedLine[0].toInt())
            rightSide.add(splittedLine[3].toInt())
        }
        leftSide.sort()
        rightSide.sort()
        var distance = 0
        for (i in 0..<leftSide.size) {
            distance += abs(leftSide[i] - rightSide[i])
        }
        return distance
    }

    fun partTwo(input: List<String>): Int {
        val leftSide = mutableListOf<Int>()
        val rightSide = mutableListOf<Int>()
        input.forEach { line ->
            val splittedLine = line.split(" ")
            leftSide.add(splittedLine[0].toInt())
            rightSide.add(splittedLine[3].toInt())
        }

        var simScore = 0
        leftSide.forEach { leftSide ->
            var amountInRightSide = 0
            rightSide.forEach { rightSide ->
                if (leftSide == rightSide) {
                    amountInRightSide++
                }
            }
            simScore += leftSide * amountInRightSide
        }
        return simScore
    }
}