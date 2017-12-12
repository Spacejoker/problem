-- Solution to advent 2017, day 12
module Main where
import Data.Char
import Data.List as DL
import Data.List.Split
import Debug.Trace
import Data.HashSet as HS

toEdges :: [String] -> [Int]
toEdges (_:_:xs) = DL.map read edgeIds
  where edgeIds = DL.map (DL.filter (\x -> x /= ',')) xs

-- f: eval function, i.e. size or minimum element
floodFill :: [Int] -> Set Int -> [[Int]] -> (Set Int -> Int) -> Int
floodFill [] set _ f = f set
floodFill (curNode : restOfNodes) set edges f = floodFill (restOfNodes ++ newNodes) (HS.insert curNode set) edges f
  where edgesFromNode = edges !! curNode
        newNodes = DL.filter (\x -> notMember x set) edgesFromNode

solveA :: String -> String
solveA s = show $ floodFill [0] HS.empty edges size
  where edges = DL.map toEdges (DL.map words (lines s))

solveB :: String -> String
solveB s = show $ length (nub [ floodFill [y] HS.empty edges (minimum . toList) | y <- [0..  (length edges) - 1]])
  where edges = DL.map toEdges (DL.map words (lines s))

solve :: String -> String
solve s = "A: " ++ solveA s ++ ", B: " ++ solveB s

main :: IO()
main = interact solve

