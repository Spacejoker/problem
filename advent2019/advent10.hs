-- Solution to advent 2019
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

ymax = 26

toAngle :: [String] ->(Int, Int) -> (Int, Int) -> Double
toAngle grid (x, y) (x1, y1) = atan2 (fromIntegral (x1 - x)) (fromIntegral (y1 - y))
    where cell = (grid !! y1) !! x1

--countPos :: [String] -> (Int, Int) -> (Int, Int, Int) -- [Double]
countPos grid (x, y) = length angles -- (x, y, length angles, angles, length points) -- (length angles) - 1
  where points = [(xp, yp) | xp <- [0..ymax], yp <- [0..ymax], ((grid !! yp) !! xp) == '#', (xp/=x || yp /= y)]
        angles = nub (map (toAngle grid (x, y)) points)

--firstStar :: String -> [Double]
firstStar s = maximum candidates
  where grid = lines s
        candidates = map (countPos grid) [(x, y) | x <- [0..ymax], y <- [0..ymax], ((grid !! y) !! x) == '#']

--secondStar :: String -> [Double]
secondStar s =  42


solve :: String -> String
solve s = "A: " ++ (show (firstStar s)) ++ "\n"
    ++ "B: " ++ (show (secondStar s)) ++ "\n"

main = interact solve
