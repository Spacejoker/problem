-- Solution to advent 2019, day 1
module Main where

firstStar :: String -> String
firstStar = show . sum . map (\x -> x`div`3 - 2) . map read . lines

modulePrice :: Int -> Int
modulePrice s 
 | val <= 2 = max 0 val
 | otherwise = val + modulePrice val
   where val = s`div`3 - 2

secondStar :: String -> String
secondStar = show . sum . map modulePrice . map read . lines

solve :: String -> String
solve s = "Star 1: " ++ firstStar s ++ "\nStar 2: " ++ secondStar s ++ "\n"

main = interact solve
