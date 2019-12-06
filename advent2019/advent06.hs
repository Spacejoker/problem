-- Solution to advent 2019, day 6
module Main where
import Data.List as DL
import Data.Char
import Data.Sequence as DS
import Data.List.Split
import Data.HashMap.Strict as HM

makePair :: String -> (String, String)
makePair s = (a,b)
  where [a,b] = splitOn ")" s

-- Key = child, Value = parent
makeParentMap :: HashMap String String -> [(String, String)] -> HashMap String String
makeParentMap m [] = m
makeParentMap m ((p,c):xs) = makeParentMap (HM.insert c p m) xs

countSteps :: HashMap String String -> String -> Int
countSteps m child = case x of
         Just n -> (1::Int) + (countSteps m n)
         Nothing -> 0
   where x = HM.lookup child m

enumerateSteps :: HashMap String String -> String -> [String]
enumerateSteps m child = case x of
         Just n -> (n:(enumerateSteps m n))
         Nothing -> []
   where x = HM.lookup child m

firstStar :: String -> Int
firstStar s = sum steps
  where pairs = DL.map makePair (lines s)
        parentMap = makeParentMap HM.empty pairs
        steps = DL.map (countSteps parentMap) (DL.map snd pairs)

secondStar :: String -> Int
secondStar s = totalNodes - 2*intersectionNodes
  where pairs = DL.map makePair (lines s)
        parentMap = makeParentMap HM.empty pairs
        mySteps = enumerateSteps parentMap "YOU"
        santaSteps = enumerateSteps parentMap "SAN"
        totalNodes = (DL.length mySteps) + (DL.length santaSteps)
        intersectionNodes = DL.length (intersect santaSteps mySteps)

solve :: String -> String
solve s = "A: " ++ (show (firstStar s)) ++ "\n"
    ++ "B: " ++ (show (secondStar s)) ++ "\n"

main = interact solve
