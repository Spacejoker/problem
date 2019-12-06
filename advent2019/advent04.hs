-- Solution to advent 2019, day 2
module Main where
import Data.List
import Data.Char
import Data.List.Split

matches2' :: String -> Bool -> Bool
matches2' [y] hasDouble = hasDouble
matches2' [x,y] hasDouble = hasDouble
matches2' [x,y,z] hasDouble = x<=y && hasDouble
matches2' (l:x:y:z:t) hasDouble
  | l/=x && x==y && y/=z = matches2' (x:y:z:t) True
  | x<=y = matches2' (x:y:z:t) hasDouble
  | otherwise = False

matches2 :: Int -> Bool
matches2 v = matches2' ("L" ++ (show v) ++ "L") False

secondStar :: [Int] -> Int
secondStar s = length (filter matches2 s)

matches' :: String -> Bool -> Bool
matches' [y] hasSame = hasSame
matches' (x:y:t) hasSame
  | x==y = matches' (y:t) True
  | x<=y = matches' (y:t) hasSame
  | otherwise = False

matches :: Int -> Bool
matches v = matches' (show v) False

firstStar :: [Int] -> Int
firstStar s = length (filter matches s)

solve :: String -> String
solve s = "A: " ++ (show (firstStar [254032..789860]))
          ++ "\nB: " ++ (show (secondStar [254032..789860])) ++ "\n"

main = interact solve
