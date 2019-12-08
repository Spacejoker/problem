-- Solution to advent 2019, day 6
module Main where
import Data.List as DL
import Data.List.Split
import Data.Maybe
import Data.Char
--import Data.Char
--import Data.Sequence as DS
--import Data.List.Split
--import Data.HashMap.Strict as HM
--

numberCount :: String -> [(Int, Int)]
numberCount s = map (\l -> (digitToInt (head l), length l))  s'
  where s' = group (sort s)

numberLookup :: [(Int, Int)] -> Int -> Int
numberLookup list searchFor = value 
  where (_, value) = fromJust $ find (\(n,_) -> n == searchFor) list
        
minimumZeroComparator l1 l2 = compare (numberLookup l1 0) (numberLookup l2 0)

firstStar :: String -> Int
firstStar s = (numberLookup el 1) * (numberLookup el 2)
  where layers = splitEvery (25*6) (filter isDigit s)
        counts = map numberCount layers
        el = minimumBy minimumZeroComparator counts

secondStar :: String -> Int
secondStar s = 42

solve :: String -> String
solve s = "A: " ++ (show (firstStar s)) ++ "\n"
    ++ "B: " ++ (show (secondStar s)) ++ "\n"

main = interact solve
