fun main() {
    // Day 1
    println("Day 1 - Part One - Example case: ${Day1().partOne(FileReader.readFile("puzzle/day1/examplePart1.txt"))}")
    println("Day 1 - Part One - Puzzle input: ${Day1().partOne(FileReader.readFile("puzzle/day1/part1.txt"))}")
    println("Day 1 - Part Two - Example case: ${Day1().partTwo(FileReader.readFile("puzzle/day1/examplePart1.txt"))}")
    println("Day 1 - Part Two - Puzzle input: ${Day1().partTwo(FileReader.readFile("puzzle/day1/part1.txt"))}")

    // Day 2
    println("Day 2 - Part One - Example case: ${Day2().partOne(FileReader.readFile("puzzle/day2/example.txt"))}")
    println("Day 2 - Part One - Puzzle Input: ${Day2().partOne(FileReader.readFile("puzzle/day2/puzzleInput.txt"))}")
    println("Day 2 - Part Two - Example case: ${Day2().partTwo(FileReader.readFile("puzzle/day2/example.txt"))}")
    println("Day 2 - Part Two - Puzzle Input: ${Day2().partTwo(FileReader.readFile("puzzle/day2/puzzleInput.txt"))}")
}
