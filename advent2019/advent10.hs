-- Solution to advent 2019
module Main where
import Data.List as DL
import Data.List.Split
import Data.Maybe
import Data.Char
import Data.Function
--import Data.Char
--import Data.Sequence as DS
--import Data.List.Split
--import Data.HashMap.Strict as HM
--

ymax = 26

toAngle :: [String] ->(Int, Int) -> (Int, Int) -> Double
toAngle grid (x, y) (x1, y1) = atan2 (fromIntegral (x1 - x)) (fromIntegral (y1 - y))
    where cell = (grid !! y1) !! x1

countPos :: [String] -> (Int, Int) -> (Int, Int, Int)
countPos grid (x, y) = (length angles, x, y)
  where points = [(xp, yp) | xp <- [0..ymax], yp <- [0..ymax], ((grid !! yp) !! xp) == '#', (xp/=x || yp /= y)]
        angles = nub (map (toAngle grid (x, y)) points)

firstStar :: String -> (Int , Int, Int)
firstStar s = maximumBy (compare `on` (\(c, _, _) -> c)) candidates
  where grid = lines s
        candidates = map (countPos grid) [(x, y) | x <- [0..ymax], y <- [0..ymax], ((grid !! y) !! x) == '#']

toData :: [String] ->(Int, Int) -> (Int, Int) -> (Double, Double, Int)
toData grid (x, y) (x1, y1) = (angle, distance, x1*100 + y1)
    where cell = (grid !! y1) !! x1
          xDelta = (fromIntegral (x1 - x))
          yDelta = (fromIntegral (y1 - y))
          angle = atan2 xDelta yDelta
          distance = xDelta * xDelta + yDelta * yDelta


-- constructList :: [String] -> (Int, Int) -> [(Double, Double, Int)]
constructList grid (x, y) = sortedGroupsByDistance
  where points = [(xp, yp) | xp <- [0..ymax], yp <- [0..ymax], ((grid !! yp) !! xp) == '#', (xp/=x || yp /= y)]
        d = sortBy (compare `on` (\(a,_,_)->a)) (map (toData grid (x, y)) points)
        grouped = groupBy (\(a1, _, _) (a2, _, _) ->  a1 == a2) d
        sortedGroupsByDistance = map (sortBy (compare `on` (\(a,_,_)->a))) grouped

findX n list pos
  | 
  | length inner > 0 = 

    where inner = list !! (pos `mod` (length list))

--secondStar :: String -> [Double]
secondStar s = res
  where grid = lines s
        candidates = map (constructList grid) [(17, 23)]
        res = findX 200 candidates 0


solve :: String -> String
solve s = "A: " ++ (show (firstStar s)) ++ "\n"
    ++ "B: " ++ (show (secondStar s)) ++ "\n"

main = interact solve
