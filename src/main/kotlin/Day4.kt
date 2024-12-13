import java.time.Clock
import java.time.Instant
import kotlin.math.abs

class Day4 {

    fun partOne(input: List<String>): Int {
        val xFindings = mutableListOf<Triple<Char, Int, Int>>()
        val mFindings = mutableListOf<Triple<Char, Int, Int>>()
        val aFindings = mutableListOf<Triple<Char, Int, Int>>()
        val sFindings = mutableListOf<Triple<Char, Int, Int>>()

        for (i in 0..<input.size) {
                for (k in 0..<input[i].length) {
                            if (input[i][k] == 'X') {
                                xFindings.add(Triple(' ' ,i, k))
                            } else if (input[i][k] == 'M') {
                                mFindings.add(Triple(' ', i, k))
                            } else if (input[i][k] == 'A') {
                                aFindings.add(Triple(' ', i, k))
                            } else if (input[i][k] == 'S') {
                                sFindings.add(Triple(' ', i, k))
                            }
                }
            }

        var letterCounter = 0
        val resultListX = mutableListOf<Triple<Char, Int, Int>>()
        var resultListM = mutableListOf<Triple<Char, Int, Int>>()
        var resultListA = mutableListOf<Triple<Char, Int, Int>>()
        var resultListS = mutableListOf<Triple<Char, Int, Int>>()

        while (letterCounter < 4) {
            if (letterCounter == 0) {
                xFindings.forEach { resultListX.add(it) }
            } else if (letterCounter == 1) {
                for (i in 0..<resultListX.size) {
                   resultListM.addAll(mFindings.map { map(resultListX[i], it) }.filter { it.first != 'x' })
                }
                resultListM = resultListM.distinct().toMutableList()
            } else if (letterCounter == 2) {
                for (i in 0..<resultListM.size) {
                    resultListA.addAll(aFindings.map { map(resultListM[i], it) }.filter { it.first != 'x' })
                }
                resultListA = resultListA.distinct().toMutableList()
            } else if (letterCounter == 3) {
                for (i in 0..<resultListA.size) {
                    resultListS.addAll(sFindings.map { map(resultListA[i], it) }.filter { it.first != 'x' })
                }
                resultListS = resultListS.distinct().toMutableList()
            }
            letterCounter++
        }
        return resultListS.size
    }

    fun partTwo(input: List<String>): Int {
        val mFindings = mutableListOf<Triple<Char, Int, Int>>()
        val aFindings = mutableListOf<Triple<Char, Int, Int>>()
        val sFindings = mutableListOf<Triple<Char, Int, Int>>()

        for (i in 0..<input.size) {
            for (k in 0..<input[i].length) {
                if (input[i][k] == 'M') {
                    mFindings.add(Triple(' ' ,i, k))
                } else if (input[i][k] == 'A') {
                    aFindings.add(Triple(' ', i, k))
                } else if (input[i][k] == 'S') {
                    sFindings.add(Triple(' ', i, k))
                }
            }
        }

        var letterCounter = 0
        val resultListM = mutableListOf<Triple<Char, Int, Int>>()
        var resultListA = mutableListOf<Triple<Char, Int, Int>>()
        var resultListS = mutableListOf<Triple<Char, Int, Int>>()

        while (letterCounter < 3) {
            if (letterCounter == 0) {
                mFindings.forEach { resultListM.add(it) }
            } else if (letterCounter == 1) {
                for (i in 0..<resultListM.size) {
                    resultListA.addAll(aFindings.map { mapDiagonal(resultListM[i], it) }.filter { it.first != 'x' })
                }
                resultListA = resultListA.distinct().toMutableList()
            } else if (letterCounter == 2) {
                for (i in 0..<resultListA.size) {
                    resultListS.addAll(sFindings.map { mapDiagonal(resultListA[i], it) }.filter { it.first != 'x' })
                }
                resultListS = resultListS.distinct().toMutableList()
            }
            letterCounter++
        }

        var counter = 0
        resultListS.forEach { entry ->
            resultListS.forEach { entry2 ->
                if (entry.first == 'f' && entry2.first == 'c') {
                    if (entry.third == entry2.third && (entry.second - entry2.second) == -2) {
                        counter++
                    }
                } else if (entry.first == 'f' && entry2.first == 'e') {
                    if (entry.second == entry2.second && (entry.third - entry2.third) == -2) {
                        counter++
                    }
                } else if (entry.first == 'd' && entry2.first == 'e') {
                    if (entry.third == entry2.third && (entry.second - entry2.second) == 2) {
                        counter++
                    }
                } else if (entry.first == 'd' && entry2.first == 'c') {
                    if (entry.second == entry2.second && (entry.third - entry2.third) == 2) {
                        counter++
                    }
                }
            }
        }
        return counter
    }

    private fun map(first: Triple<Char, Int, Int>, second: Triple<Char, Int, Int>): Triple<Char, Int, Int> {
        return when (first.first) {
            ' ' -> {
                return if (first.second == second.second-1 && first.third == second.third) {
                    Triple('a', second.second, second.third)
                } else if (first.second == second.second+1 && first.third == second.third) {
                    Triple('b', second.second, second.third)
                } else if (first.second == second.second-1 && first.third == second.third+1) {
                    Triple('c', second.second, second.third)
                } else if (first.second == second.second-1 && first.third == second.third-1) {
                    Triple('d', second.second, second.third)
                } else if (first.second == second.second+1 && first.third == second.third-1) {
                    Triple('e', second.second, second.third)
                } else if (first.second == second.second+1 && first.third == second.third+1) {
                    Triple('f', second.second, second.third)
                } else if (first.second == second.second && first.third == second.third-1) {
                    Triple('g', second.second, second.third)
                } else if (first.second == second.second && first.third == second.third+1) {
                    Triple('h', second.second, second.third)
                } else {
                    Triple('x', second.second, second.third)
                }
            }
            'a' -> {
                return if (first.second == second.second -1 && first.third == second.third ) {
                    Triple(first.first, second.second, second.third)
                } else {
                    Triple('x', second.second, second.third)
                }
            }
            'b' -> {
                return if (first.second == second.second +1 && first.third == second.third ) {
                    Triple(first.first, second.second, second.third)
                } else {
                    Triple('x', second.second, second.third)
                }
            }
            'c' -> {
                return if (first.second == second.second -1 && first.third == second.third +1) {
                    Triple(first.first, second.second, second.third)
                } else {
                    Triple('x', second.second, second.third)
                }
            }
            'd' -> {
                return if (first.second == second.second -1 && first.third == second.third -1) {
                    Triple(first.first, second.second, second.third)
                } else {
                    Triple('x', second.second, second.third)
                }
            }
            'e' -> {
                return if (first.second == second.second +1 && first.third == second.third -1) {
                    Triple(first.first, second.second, second.third)
                } else {
                    Triple('x', second.second, second.third)
                }
            }
            'f' -> {
                return if (first.second == second.second +1 && first.third == second.third +1) {
                    Triple(first.first, second.second, second.third)
                } else {
                    Triple('x', second.second, second.third)
                }
            }
            'g' -> {
                return if (first.second == second.second && first.third == second.third -1) {
                    Triple(first.first, second.second, second.third)
                } else {
                    Triple('x', second.second, second.third)
                }
            }
            'h' -> {
                return if (first.second == second.second && first.third == second.third + 1) {
                    Triple(first.first, second.second, second.third)
                } else {
                    Triple('x', second.second, second.third)
                }
            }
            else -> Triple('x', second.second, second.third)
        }
    }

    private fun mapDiagonal(first: Triple<Char, Int, Int>, second: Triple<Char, Int, Int>): Triple<Char, Int, Int> {
        return when (first.first) {
            ' ' -> {
                return if (first.second == second.second-1 && first.third == second.third+1) { // Diagonal links runter
                    Triple('c', second.second, second.third)
                } else if (first.second == second.second-1 && first.third == second.third-1) { // Diagnoal rechts runter
                    Triple('d', second.second, second.third)
                } else if (first.second == second.second+1 && first.third == second.third-1) { // Diagonal rechts hoch
                    Triple('e', second.second, second.third)
                } else if (first.second == second.second+1 && first.third == second.third+1) { // Diagonal links hoch
                    Triple('f', second.second, second.third)
                } else {
                    Triple('x', second.second, second.third)
                }
            }
            'c' -> {
                return if (first.second == second.second -1 && first.third == second.third +1) {
                    Triple(first.first, second.second, second.third)
                } else {
                    Triple('x', second.second, second.third)
                }
            }
            'd' -> {
                return if (first.second == second.second -1 && first.third == second.third -1) {
                    Triple(first.first, second.second, second.third)
                } else {
                    Triple('x', second.second, second.third)
                }
            }
            'e' -> {
                return if (first.second == second.second +1 && first.third == second.third -1) {
                    Triple(first.first, second.second, second.third)
                } else {
                    Triple('x', second.second, second.third)
                }
            }
            'f' -> {
                return if (first.second == second.second +1 && first.third == second.third +1) {
                    Triple(first.first, second.second, second.third)
                } else {
                    Triple('x', second.second, second.third)
                }
            }
            else -> Triple('x', second.second, second.third)
        }
    }

}