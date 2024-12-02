import System.IO
import Control.Monad
import Data.List (sort)

main = do
        contents <- readFile "../src/main/resources/puzzle/day1/part1.txt"
        let leftSide = (getFirstNumbers (lines contents))
        let rightSide = (getSecondNumbers (lines contents))
        let sortedLeftSide = sort leftSide
        let sortedRightSide = sort rightSide
        let distances = calcDistances sortedLeftSide sortedRightSide
        print (sum distances)

getFirstNumbers :: [String] -> [Int]
getFirstNumbers linesOfInput = map getFirstNumber linesOfInput

getFirstNumber:: String -> Int
getFirstNumber line =
  let firstWord = head (words line)
  in read firstWord :: Int

getSecondNumbers :: [String] -> [Int]
getSecondNumbers linesOfInput = map getSecondNumber linesOfInput

getSecondNumber :: String -> Int
getSecondNumber line =
  let secondWord = (words line) !! 1
  in read secondWord :: Int

calcDistances :: [Int] -> [Int] -> [Int]
calcDistances first second = zipWith calcDistance first second

calcDistance :: Int -> Int -> Int
calcDistance first second = abs (first - second)