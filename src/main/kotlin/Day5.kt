import java.time.Clock
import java.time.Instant
import kotlin.math.abs

class Day5 {
    fun partOne(input: List<String>): Int {
        val orders = HashMap<Int, MutableList<Int>>()
        var c1 = 0
        var c2 = 0
        var counter = 0
        var line = input[counter]
        while (line.isNotEmpty()) {
            val order = line.split("|")
            orders.computeIfAbsent(order[0].toInt()) { mutableListOf() }.add(order[1].toInt())
            line = input[++counter]
        }
        counter++
        while (counter < input.size) {
            line = input[counter]
            var lineCorrect = true
            var corrected = false
            val order = line.split(",").map { it.toInt() }.toMutableList()
            for (i in 0..<order.size) {
                val accValue = order[i]
                for (k in 0..<i) {
                    if (orders[accValue] != null && orders[accValue]!!.contains(order[k])) {
                        lineCorrect = false
                        corrected = true
                        val temp = order[k]
                        order[k] = order[i]
                        order[i] = temp
                    }
                }
            }
            if (lineCorrect) {
               c1 += order[order.size/2]
            }

            if (corrected) {
                c2 += order[order.size/2]
            }
            counter++
        }
        println("Part one $c1")
        println("Part two $c2")
        return c1
    }
}