class Day11 {
    fun partOne(input: List<String>) {
        val line = input.first()
        val hashmap = HashMap<Long, Pair<Long, Long>>()
        var numbers = line.split(" ").map { it.toLong() }
        val numberOfBlinks = 75
        for (i in 0..<numberOfBlinks) {
            val runList = mutableListOf<Long>()
            numbers.forEach { number ->
                if (hashmap.containsKey(number)) {
                    println("here: $i")
                    runList.add(hashmap[number]!!.first)
                    if (hashmap[number]!!.second != -1L) {
                        runList.add(hashmap[number]!!.second)
                    }
                } else {
                    if (number == 0L) {
                        hashmap[number] = Pair(1, -1)
                    } else if (number.toString().length % 2 == 0) {
                        val numAsString = number.toString()
                        val firstPart = numAsString.subSequence(0, numAsString.length / 2).toString().toLong()
                        val secondPart =
                            numAsString.subSequence(numAsString.length / 2, numAsString.length).toString().toLong()
                        hashmap.putIfAbsent(number, Pair(firstPart, secondPart))
                    } else {
                        hashmap.putIfAbsent(number, Pair(number * 2024, -1))
                    }
                    runList.add(hashmap[number]!!.first)
                    if (hashmap[number]!!.second != -1L) {
                        runList.add(hashmap[number]!!.second)
                    }
                }
            }
            numbers = runList
        }
        println(numbers.size)
    }
}