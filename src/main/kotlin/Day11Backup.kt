class Day11Backup {
    fun partOne(input: List<String>) {
        val line = input.first()
        var numbers = line.split(" ").map { it.toLong() }
        var counter = 0

        numbers.forEach {
            counter += calculate(it, 0)
        }

        println(counter)
    }

    private fun calculate(number: Long, depth: Int): Int {
        if (depth == 75) {
            return 1
        } else {
            if (number == 0L) {
                return calculate(1, depth + 1)
            } else if (number.toString().length % 2 == 0) {
                val numAsString = number.toString()
                val firstPart = numAsString.subSequence(0, numAsString.length / 2).toString().toLong()
                val secondPart =
                    numAsString.subSequence(numAsString.length / 2, numAsString.length).toString().toLong()
                return calculate(firstPart, depth + 1) + calculate(secondPart, depth + 1) + 2
            } else {
                return calculate(number * 2024, depth + 1) + 0
            }
        }
    }
}