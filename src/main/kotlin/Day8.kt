import kotlin.math.abs

class Day8 {
    fun partOne(input: List<String>) {
        val map = HashMap<Char, MutableList<Pair<Int, Int>>>()
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, char ->
                if (char != '.') {
                    map.computeIfAbsent(char) { mutableListOf() }.add(row to col)
                }
            }
        }
        val antinodeLocations = mutableSetOf<Pair<Int, Int>>()
        val antinodeLocationsPart2 = mutableSetOf<Pair<Int, Int>>()
        map.keys.forEach { key ->
            val coords = map[key]!!
            for (i in 0..<coords.size) {
                val firstCord = coords[i]
                for (k in i + 1..<coords.size) {
                    val secondCord = coords[k]
                    val calculateRowDiv = abs(firstCord.first - secondCord.first)
                    val calculateColumnDiv = abs(firstCord.second - secondCord.second)
                    var firstPointRow: Int
                    var firstPointCol: Int
                    var secondPointRow: Int
                    var secondPointColumn: Int

                    println("Checking $firstCord $secondCord, div: $calculateRowDiv, column: $calculateColumnDiv")
                    if (firstCord.first <= secondCord.first) {
                        firstPointRow = firstCord.first - calculateRowDiv
                        secondPointRow = secondCord.first + calculateRowDiv
                    } else {
                        firstPointRow = firstCord.first + calculateRowDiv
                        secondPointRow = secondCord.first - calculateRowDiv
                    }
                    if (firstCord.second <= secondCord.second) {
                        firstPointCol = firstCord.second - calculateColumnDiv
                        secondPointColumn = secondCord.second + calculateColumnDiv
                    } else {
                        firstPointCol = firstCord.second + calculateColumnDiv
                        secondPointColumn = secondCord.second - calculateColumnDiv
                    }

                    if (firstPointRow >= 0 && firstPointCol >= 0 && firstPointRow < input.size && firstPointCol < input.first().length) {
                        antinodeLocations.add(firstPointRow to firstPointCol)
                        println("$firstPointRow, $firstPointCol")
                    }
                    if (secondPointRow >= 0 && secondPointColumn >= 0 && secondPointRow < input.size && secondPointColumn < input.first().length) {
                        println("$secondPointRow, $secondPointColumn")
                        antinodeLocations.add(secondPointRow to secondPointColumn)
                    }

                    // Loop calculation
                    println("Checking Upper")
                    var updatedFirstCoord = firstCord
                    var updatedSecondCoord = secondCord
                    firstPointRow = 0
                    firstPointCol = 0
                    secondPointRow = 0
                    secondPointColumn = 0
                    while (firstPointRow >= 0 && firstPointCol >= 0 && firstPointRow < input.size && firstPointCol < input.first().length) {
                        if (updatedFirstCoord.first <= updatedSecondCoord.first) {
                            firstPointRow = updatedFirstCoord.first - calculateRowDiv
                        } else {
                            firstPointRow = updatedFirstCoord.first + calculateRowDiv
                        }
                        if (updatedFirstCoord.second <= updatedSecondCoord.second) {
                            firstPointCol = updatedFirstCoord.second - calculateColumnDiv
                        } else {
                            firstPointCol = updatedFirstCoord.second + calculateColumnDiv
                        }
                        if (firstPointRow >= 0 && firstPointCol >= 0 && firstPointRow < input.size && firstPointCol < input.first().length) {
                            antinodeLocationsPart2.add(firstPointRow to firstPointCol)
                            println("$firstPointRow, $firstPointCol")
                        }
                        updatedSecondCoord = updatedFirstCoord
                        updatedFirstCoord = Pair(firstPointRow, firstPointCol)
                    }

                    updatedFirstCoord = firstCord
                    updatedSecondCoord = secondCord
                    println("Checking lower")
                    while (secondPointRow >= 0 && secondPointColumn >= 0 && secondPointRow < input.size && secondPointColumn < input.first().length) {
                        if (updatedFirstCoord.first <= updatedSecondCoord.first) {
                            secondPointRow = updatedSecondCoord.first + calculateRowDiv
                        } else {
                            secondPointRow = updatedSecondCoord.first - calculateRowDiv
                        }
                        if (updatedFirstCoord.second <= updatedSecondCoord.second) {
                            secondPointColumn = updatedSecondCoord.second + calculateColumnDiv
                        } else {
                            secondPointColumn = updatedSecondCoord.second - calculateColumnDiv
                        }
                        if (secondPointRow >= 0 && secondPointColumn >= 0 && secondPointRow < input.size && secondPointColumn < input.first().length) {
                            println("$secondPointRow, $secondPointColumn")
                            antinodeLocationsPart2.add(secondPointRow to secondPointColumn)
                        }
                        updatedFirstCoord = updatedSecondCoord
                        updatedSecondCoord = Pair(secondPointRow, secondPointColumn)
                    }
                }
            }
        }
        println(antinodeLocations.size)
        val completeAntanas = mutableSetOf<Pair<Int, Int>>()
        antinodeLocationsPart2.forEach {
            completeAntanas.add(it)
        }
        antinodeLocations.forEach { completeAntanas.add(it) }
        map.values.filter { it.size > 1 }.flatten().forEach { completeAntanas.add(it) }
        println(completeAntanas.size)
    }
}