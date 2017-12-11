-- Solution to advent 2017, day 3
module Main where
import Data.List
import Data.Char

manhattan :: Int -> Int
manhattan value = distFromMid + depth
  where squareSizes = takeWhile (\x -> x <= value) $ map (\x -> x*x) [1,3..]
        depth = length squareSizes
        edgeLength = depth * 2
        posInEdge = (value - (last squareSizes) + (edgeLength `quot` 2)) `mod` edgeLength
        distFromMid = min posInEdge (edgeLength - posInEdge)

main = do
  putStrLn $ show $ manhattan 325489

