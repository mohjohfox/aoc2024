import java.util.regex.Pattern
import kotlin.math.pow

class Day17Part2 {

    var registerA = 0
    var oldRegisterA = 0
    var registerB = 0
    var registerC = 0
    var instructionPointer = 0
    val program = mutableListOf<Int>()
    val output = mutableListOf<Int>()
    var equalPointer = 0


    fun partOne(input: List<String>) {
        val pattern = Pattern.compile("\\d+")
        val matchRegisterB = pattern.matcher(input[1])
        if (matchRegisterB.find()) {
            registerB = matchRegisterB.group().toInt()
        }
        val matchRegisterC = pattern.matcher(input[2])
        if (matchRegisterC.find()) {
            registerC = matchRegisterC.group().toInt()
        }

        input[4].forEach { char ->
            if (char.isDigit()) {
                program.add(char.digitToInt())
            }
        }

        while (instructionPointer < program.size) {
            val opCode = program[instructionPointer++]
            when (opCode) {
                0 -> {
                    val operand = program[instructionPointer++]
                    val comboOperator = getComboOperand(operand)
                    adv(comboOperator)
                }

                1 -> {
                    bxl(program[instructionPointer++])
                }

                2 -> {
                    val operand = program[instructionPointer++]
                    val comboOperator = getComboOperand(operand)
                    bst(comboOperator)
                }

                3 -> {
                    jnz(program[instructionPointer++])
                }

                4 -> {
                    bxc()
                    instructionPointer++
                }

                5 -> {
                    val operand = program[instructionPointer++]
                    val comboOperator = getComboOperand(operand)
                    if (!out(comboOperator)) {
                        output.clear()
                        instructionPointer = 0
                        registerA++
                        equalPointer = 0
                    }
                }

                6 -> {
                    val operand = program[instructionPointer++]
                    val comboOperator = getComboOperand(operand)
                    bdv(comboOperator)
                }

                7 -> {
                    val operand = program[instructionPointer++]
                    val comboOperator = getComboOperand(operand)
                    cdv(comboOperator)
                }
            }

            if (instructionPointer >= program.size - 1 && output.size != program.size) {
                output.clear()
                instructionPointer = 0
                oldRegisterA++
                registerA = oldRegisterA
                equalPointer = 0
                println(registerA)
            }
        }
        println(output.joinToString(separator = ","))
        println(registerA)
    }

    private fun getComboOperand(operand: Int): Int {
        return when (operand) {
            4 -> registerA
            5 -> registerB
            6 -> registerC
            7 -> throw IllegalArgumentException("7 is not allowed in valid programs")
            else -> operand
        }
    }

    private fun adv(denominator: Int) {
        registerA = (registerA / 2.0.pow(denominator)).toInt()
    }

    private fun bxl(operand: Int) {
        registerB = registerB.xor(operand)
    }

    private fun bst(operand: Int) {
        registerB = (operand % 8) % 7
    }

    private fun jnz(operand: Int) {
        if (registerA != 0) {
            instructionPointer = operand
        }
    }

    private fun bxc() {
        registerB = registerB.xor(registerC)
    }

    private fun out(operand: Int): Boolean {
        val argument = operand % 8
        if (argument == program[equalPointer++]) {
            output.add(argument)
            return true
        }
        return false
    }

    private fun bdv(denominator: Int) {
        registerB = (registerA / 2.0.pow(denominator)).toInt()
    }


    private fun cdv(denominator: Int) {
        registerC = (registerA / 2.0.pow(denominator)).toInt()
    }

}