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

makeParentMap :: HashMap String String -> [(String, String)] -> HashMap String String
makeParentMap m [] = m
makeParentMap m ((p,c):xs) = makeParentMap (HM.insert c p m) xs

countSteps :: HashMap String String -> (String, String) -> Int
-- countSteps m (_,child) = 1
countSteps m (_,child) = case x of
         Just n -> (1::Int) + (countSteps m ("",n))
         Nothing -> 0
   where x = HM.lookup child m

-- firstStar :: String -> [(String, String)]
firstStar s = sum steps
  where pairs = DL.map makePair (lines s)
        hashMap = makeParentMap HM.empty pairs
        steps = DL.map (countSteps hashMap) pairs
        

secondStar :: String -> Int
secondStar s = 42

solve :: String -> String
solve s = "A: " ++ (show (firstStar s)) ++ "\n"
    ++ "B: " ++ (show (secondStar s)) ++ "\n"

main = interact solve
