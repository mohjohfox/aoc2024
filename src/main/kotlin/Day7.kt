import kotlin.math.pow

class Day7 {
    fun partOne(input: List<String>) {
        var counter = 0L
        val listOfEquations = mutableListOf<String>()
        input.forEach {
            val strings = it.split(" ")
            val correctResult = strings[0].removeSuffix(":").toLong()
            val numbers = mutableListOf<Long>()
            for (i in strings.indices) {
                if (i != 0) {
                    numbers.add(strings[i].toLong())
                }
            }
            val possibilities = 2.0.pow((numbers.size - 1).toDouble()).toInt()
            var equation = ""
            for (i in 0..<possibilities) {
                var result = 0L
                var fullEquation = ""
                for (k in 0..<numbers.size - 1) {
                    if (k == 0) {
                        if ((i shr 0) and 1 == 1) {
                            equation += "* "
                            result += numbers[k] * numbers[1]
                            fullEquation += "${numbers[k]}*${numbers[1]}"
                        } else {
                            equation += "+ "
                            result += numbers[k] + numbers[1]
                            fullEquation += "${numbers[k]}+${numbers[1]}"
                        }
                    } else {
                        if ((i shr k) and 1 == 1) {
                            equation += "* "
                            result *= numbers[k + 1]
                            fullEquation += "*${numbers[k + 1]}"
                        } else {
                            equation += "+ "
                            result += numbers[k + 1]
                            fullEquation += "+${numbers[k + 1]}"
                        }
                    }
                }
                equation = ""
                if (result == correctResult) {
                    listOfEquations.add("$correctResult=$fullEquation")
                    counter += result
                    break
                } else {
                    listOfEquations.add("$correctResult=$fullEquation")
                }
            }
        }
        println("Solution Part 1: $counter")
        partTwo(listOfEquations)
    }

    private fun partTwo(equations: List<String>) {
        var counter = 0L
        var result = -1L
        var solvedForResult = false
        equations.forEach { equation ->
            val accResult = equation.split("=")[0].toLong()
            if (accResult != result || !solvedForResult) {
                if (accResult != result) {
                    solvedForResult = false
                }
                result = accResult
                val expression = equation.split("=")[1]
                val argumentList = mutableListOf<Long>()
                val operatorList = mutableListOf<Char>()
                var argument = ""
                expression.forEach { char ->
                    if (char != '+' && char != '*') {
                        argument += char
                    } else {
                        argumentList.add(argument.toLong())
                        argument = ""
                        operatorList.add(char)
                    }
                }
                argumentList.add(argument.toLong())
                for (k in 0..2.0.pow(argumentList.size - 1).toInt()) {
                    var calculatedResult = 0L
                    var fullEquation = ""
                    for (i in 0..<argumentList.size - 1) {
                        if (i == 0) {
                            if ((k shr 0) and 1 == 1) {
                                calculatedResult = (argumentList[i].toString() + argumentList[1].toString()).toLong()
                                fullEquation += "${argumentList[i]}||${argumentList[1]}"
                            } else {
                                calculatedResult += if (operatorList[i] == '*') {
                                    fullEquation += "${argumentList[i]}*${argumentList[1]}"
                                    argumentList[i] * argumentList[1]
                                } else {
                                    fullEquation += "${argumentList[i]}+${argumentList[1]}"
                                    argumentList[i] + argumentList[1]
                                }
                            }
                        } else {
                            if ((k shr i) and 1 == 1) {
                                fullEquation += "||${argumentList[i + 1]}"
                                calculatedResult =
                                    (calculatedResult.toString() + argumentList[i + 1].toString()).toLong()
                            } else {
                                if (operatorList[i] == '*') {
                                    calculatedResult *= argumentList[i + 1]
                                    fullEquation += "*${argumentList[i + 1]}"
                                } else {
                                    calculatedResult += argumentList[i + 1]
                                    fullEquation += "+${argumentList[i + 1]}"
                                }
                            }
                        }
                    }
                    println("$accResult=$fullEquation")
                    if (calculatedResult == accResult) {
                        println("Found solution for $calculatedResult")
                        counter += calculatedResult
                        solvedForResult = true
                        break
                    }
                }
            }
        }
        println(counter)
    }
}