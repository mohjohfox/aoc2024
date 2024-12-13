import java.util.regex.Pattern

class Day3 {

    fun partOne(input: List<String>): Int {
        val pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)")
        var result = 0
        input.forEach { line ->
            val matcher = pattern.matcher(line)
                while (matcher.find()) {
                    val withGroups = matcher.toMatchResult()
                    val first = withGroups.group(1)
                    val second = withGroups.group(2)
                    result += first.toInt() * second.toInt()
                }
        }
        return result
    }

    fun partTwo(input: List<String>): Int {
        val pattern = Pattern.compile("do\\(\\)|don't\\(\\)|mul\\((\\d{1,3}),(\\d{1,3})\\)")
        var result = 0
        var enabled = true
        input.forEach { line ->
            val matcher = pattern.matcher(line)
            while (matcher.find()) {
                val accGroup = matcher.group()
                if (accGroup.startsWith("do()")) {
                    enabled = true
                    continue
                } else if (accGroup.startsWith("don't()")) {
                    enabled = false
                }
                if (enabled) {
                    val withGroups = matcher.toMatchResult()
                    val first = withGroups.group(1)
                    val second = withGroups.group(2)
                    result += first.toInt() * second.toInt()
                }
            }
        }
        return result
    }
}