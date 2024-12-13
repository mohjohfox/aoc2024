class Day9 {
    fun partOne(input: List<String>) {
        val idList = mutableListOf<Int>()
        var index = 0
        var fileCounter = 0
        input.forEach { line ->
            line.forEach { char ->
                for (i in 0..<char.digitToInt()) {
                    if ((index + 1) % 2 == 0) {
                        idList.add(-1)
                    } else {
                        idList.add(fileCounter)
                    }
                }
                index++
                if ((index + 1) % 2 == 0) {
                    fileCounter++
                }
            }
        }
        val part2List = idList.toMutableList()
        var forwardPointer = 0
        var backwardPointer = idList.size - 1
        while (forwardPointer <= backwardPointer) {
            if (idList[forwardPointer] == -1) {
                if (idList[backwardPointer] != -1) {
                    idList[forwardPointer] = idList[backwardPointer]
                    idList[backwardPointer] = -1
                    forwardPointer++
                }
                backwardPointer--
            } else {
                forwardPointer++
            }
        }
        var c1 = 0L
        idList.forEachIndexed { pointer, id ->
            if (id != -1) {
                c1 += pointer * id
            }
        }
        println(c1)

        backwardPointer = idList.size - 1
        val fileIds = mutableListOf<Pair<Int, Int>>()
        while (backwardPointer >= 0) {
            forwardPointer = 0
            if (part2List[backwardPointer] != -1) {
                if (fileIds.isEmpty() || part2List[backwardPointer] == fileIds.first().first) {
                    fileIds.add(part2List[backwardPointer] to backwardPointer)
                    backwardPointer--
                    continue
                }
            }
            if (fileIds.isNotEmpty()) {
                while (forwardPointer <= backwardPointer) {
                    var length = 0
                    if (part2List[forwardPointer] != -1) {
                        forwardPointer++
                        continue
                    }
                    for (i in 0..<fileIds.size) {
                        if (part2List[i + forwardPointer] == -1) {
                            length++
                        }
                    }
                    if (length == fileIds.size) {
                        var buffer = 0
                        for (i in 0..<fileIds.size) {
                            part2List[forwardPointer + buffer] = fileIds[buffer].first
                            buffer++
                        }
                        fileIds.forEach { part2List[it.second] = -1 }
                        break
                    }
                    forwardPointer++
                }
                fileIds.clear()
            } else {
                backwardPointer--
            }
        }
        var c2 = 0L
        part2List.forEachIndexed { pointer, value ->
            if (value != -1) {
                c2 += pointer * value
            }
        }
        println(c2)
    }
}