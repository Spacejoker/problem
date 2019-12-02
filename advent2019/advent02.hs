-- Solution to advent 2019, day 2
module Main where
import Data.List
import Data.Char
import Data.Sequence as DS
import Data.List.Split

--
-- CPU implementation
--
-- 1: ADD regA regB targetReg
-- 2: MUL regA regB targetReg
--
-- Returns reg0
--
cpu :: Seq Int -> Int -> Int
cpu mem pc
 | ins == 1 = cpu (DS.update destReg (opA + opB) mem) pc'
 | ins == 2 = cpu (DS.update destReg (opA * opB) mem) pc'
 | otherwise = DS.index mem 0
   where ins = DS.index mem pc
         opA = DS.index mem (DS.index mem (pc+1))
         opB = DS.index mem (DS.index mem (pc+2))
         destReg = DS.index mem (pc+3)
         pc' = pc + (getOffset ins)

getOffset :: Int -> Int
getOffset ins
 | ins >= 1 && ins <= 2 = 4
 | otherwise = 0

-- Problem stuff
targetScore :: Int
targetScore = 19690720

firstStar :: Seq Int -> Int -> Int
firstStar mem idx = cpu mem idx

secondStar :: Seq Int -> Int -> [(Int, Int)]
secondStar seq _ = Data.List.filter (\(v,_) -> v == targetScore) candidates
  where candidates = [(cpu (DS.update 1 x (DS.update 2 y seq)) 0, x*100 + y) | x<- [0..99], y<-[0..99]]

buildMemory :: String -> Seq Int
buildMemory s = DS.fromList (map read (splitOn "," s))

solve :: String -> String
solve s = "A: " ++ (show (firstStar seq 0)) ++ "\n"
    ++ "B: " ++ (show (secondStar seq 0)) ++ "\n"
  where seq = buildMemory s

main = interact solve
