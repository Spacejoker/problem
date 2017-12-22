module Main where
import Data.Char
import Data.List as DL
import Data.List.Split
import Debug.Trace
import Data.Maybe

go :: Int -> [[Int]] ->  Int
go _ [] = 0
go pos ((a:b:_):xs) = addedValue + (go pos xs)
   where cycle = (b - 1) * 2
         newPos = pos + a
         addedValue
           | newPos `mod` cycle == 0 = b * newPos
           | True = 0

toIntPair :: String -> [Int]
toIntPair s = map read splitString
  where splitString = splitOn ": " (filter (\x -> x /= ',' && x /= '\n') s)

solve :: String -> String
solve s = "A: " ++ (show $ go 0 parsedInput) ++ ", B: " ++ show (fromJust firstPass) ++ "\n"
  where parsedInput = map toIntPair (lines s)
        firstPass = find (\(val, _) -> val == 0) [(go i parsedInput, i) | i <- [0..]]

main :: IO()
main = interact solve
