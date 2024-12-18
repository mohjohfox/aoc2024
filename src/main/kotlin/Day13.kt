import java.util.regex.Pattern

class Day13 {

    val pattern = Pattern.compile("\\d+")
    fun partOne(input: List<String>) {
        var c1 = 0
        var c2 = 0L
        val mutableList = input.toMutableList()
        while (mutableList.isNotEmpty()) {
            if (mutableList.first().isNotEmpty()) {
                val firstLine = mutableList.removeFirst()
                val secondLine = mutableList.removeFirst()
                val thirdLine = mutableList.removeFirst()
                val firstSplitted = firstLine.split(" ")
                val secondSplitted = secondLine.split(" ")
                val thirdSplitted = thirdLine.split(" ")
                val aGroup = pattern.matcher(firstSplitted[2])
                aGroup.find()
                val bGroup = pattern.matcher(secondSplitted[2])
                bGroup.find()
                val cGroup = pattern.matcher(firstSplitted[3])
                cGroup.find()
                val dGroup = pattern.matcher(secondSplitted[3])
                dGroup.find()
                val eGroup = pattern.matcher(thirdSplitted[1])
                eGroup.find()
                val fGroup = pattern.matcher(thirdSplitted[2])
                fGroup.find()
                val a = aGroup.group().toInt()
                val b = bGroup.group().toInt()
                val c = cGroup.group().toInt()
                val d = dGroup.group().toInt()
                val e = eGroup.group().toInt()
                val f = fGroup.group().toInt()
                val ePart2 = e + 10000000000000
                val fPart2 = f + 10000000000000

                // Calculate x1
                val nenner = a * d - b * c
                if (nenner == 0) {
                    continue
                }
                val x1 = (e * d - b * f) / nenner
                val x1Part2 = (ePart2 * d - b * fPart2) / nenner

                // Calculate x2
                if (d == 0) {
                    continue
                }
                val x2 = (f - c * x1) / d
                val x2Part2 = (fPart2 - c * x1Part2) / d

                if (a * x1 + b * x2 == e && c * x1 + d * x2 == f && x1 <= 100 && x2 <= 100) {
                    c1 += x1 * 3
                    c1 += x2
                }
                if (a * x1Part2 + b * x2Part2 == ePart2 && c * x1Part2 + d * x2Part2 == fPart2) {
                    c2 += x1Part2 * 3
                    c2 += x2Part2
                }
            } else {
                mutableList.removeFirst()
            }
        }
        println(c1)
        println(c2)
    }

}