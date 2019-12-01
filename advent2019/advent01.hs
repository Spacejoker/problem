-- Solution to advent 2019, day 1
module Main where
import Data.List
import Data.Char

solveA :: String -> String
solveA = show . sum . map (\x -> x`div`3 - 2) . map read . lines

fuelSum :: Int -> Int
fuelSum s 
 | val < 0 = 0
 | val <= 2 = val
 | otherwise = val + fuelSum val
   where val = s`div`3 - 2

solveB :: String -> String
solveB = show . sum . map fuelSum . map read . lines

solve :: String -> String
solve s = "A: " ++ solveA s ++ "\nB: " ++ solveB s ++ "\n"

main = interact solve
