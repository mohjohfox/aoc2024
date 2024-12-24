class Day19 {
    private var patterns = listOf<String>()
    fun partOne(input: List<String>) {
        var count = 0
        patterns = input[0].split(",").map { it.trim() }

        for (i in 2..<input.size) {
            println(i)
            val towel = input[i]
            if (matchSubString(towel, 0)) {
                count++
            }
        }
        println(count)
    }

    private fun matchSubString(subString: String, depth: Int): Boolean {
        println(depth)
        val matchingPatterns = patterns.filter { subString.startsWith(it) }
        if (matchingPatterns.isEmpty()) {
            return false
        }
        val restOfStrings = matchingPatterns.map { subString.removePrefix(it) }
        if (restOfStrings.isNotEmpty() && restOfStrings.none { it.isEmpty() }) {
            restOfStrings.forEach {
                if (matchSubString(it, depth + 1)) {
                    return true
                }
            }
        } else {
            return true
        }
        return false
    }
}