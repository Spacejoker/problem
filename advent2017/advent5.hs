-- Solution to advent 2017, day 5
module Main where
import Data.Char
import Data.List

countSteps :: [Int] -> Int -> Int -> Int
countSteps offsets pos steps
 | pos < 0 || pos >= length offsets = steps
 | True = countSteps newOffset newPos (steps + 1)
   where newPos = pos + prevOffset
         (prefix, prevOffset:t) = splitAt pos offsets
         newOffset = prefix ++ (prevOffset + mod : t)
         mod = if prevOffset >= 3 then -1 else 1

formatInput :: [String] -> [Int]
formatInput lines = map read lines

main :: IO()
main = do
  fileContent <- readFile "input5.txt"
  let input = formatInput (lines fileContent)
  putStrLn $ show $ countSteps input 0 0
