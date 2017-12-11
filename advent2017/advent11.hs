-- Solution to advent 2017, day 11
module Main where
import Data.Char
import Data.List
import Data.List.Split
import Debug.Trace

toStep :: (Int, Int) -> String -> (Int, Int)
toStep (x, y) step
 | step == "ne" = (x + 1, y)
 | step == "se" = (x + 1, y - 1)
 | step == "s" = (x, y - 1)
 | step == "sw" = (x - 1, y)
 | step == "nw" = (x - 1, y + 1)
 | step == "n" = (x, y + 1)
-- | True = trace ("calling toStep with (x, y, step) = " ++ show (x, y, step)) (0, 0)

minSteps :: (Int, Int) -> Int
minSteps (x, y)
 | signum x == signum y = x + y
 | otherwise = max x y

-- A
distAtEnd :: [String] -> String
distAtEnd path = show $ minSteps (x, y)
  where (x, y) = foldl toStep (0,0) path

checkPath :: [String] -> (Int, Int) -> Int
checkPath [] pos = minSteps pos
checkPath (x: xs) pos = max (minSteps pos) (checkPath xs (toStep pos x))

-- B
maxDist :: [String] -> String
maxDist path = show $ checkPath path (0, 0)

solve :: String -> String
solve s = "A: " ++ distAtEnd input ++ ", B: " ++ maxDist input
  where input = splitOn "," (filter (\x -> x /= '\n') s)

main :: IO()
main = interact solve
