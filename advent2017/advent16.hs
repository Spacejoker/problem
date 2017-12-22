-- Solution to advent 2017, day 16
module Main where
import Data.Char
import Data.Bits
import Data.List as DL
import Data.List.Split
import Debug.Trace
import Data.Maybe
import Data.Sequence as DS
import Data.Foldable

-- st : spin of size t
spin :: Seq Char -> Int -> Seq Char
spin programs idx = ((><) spinB spinA)
  where (spinA, spinB) = DS.splitAt splitIndex programs
        splitIndex = seqLen - idx
        seqLen = DS.length programs

-- Swap elements at aIdx and bIdx
swap :: Seq Char -> (Int, Int) -> Seq Char
swap pms (aIdx, bIdx) = update aIdx bElem (update bIdx aElem pms)
  where aElem = index pms aIdx
        bElem = index pms bIdx

-- xa/b: swap programs at idx a and b
swapIdx :: Seq Char -> String -> Seq Char
swapIdx pms cmd = swap pms (aIdx, bIdx)
  where (aStr:bStr:_) = splitOn "/" cmd
        aIdx = (read aStr)::Int
        bIdx = (read bStr)::Int

-- pA/B: swap probram called A with program called B
swapPrograms :: Seq Char -> String -> Seq Char
swapPrograms pms (a:_:b:_) = swap pms (aIdx,bIdx)
  where aIdx = fromJust $ elemIndexL a pms
        bIdx = fromJust $ elemIndexL b pms

executePlan :: Seq Char -> [String] -> Seq Char
executePlan programs [] = programs
executePlan programs ((h:t):rest)
  | h == 's' = executePlan (spin programs (read t)) rest
  | h == 'x' = executePlan (swapIdx programs t) rest
  | h == 'p' = executePlan (swapPrograms programs t) rest
  | True = programs

checkCycleLength :: Seq Char -> [String] -> Int -> Int
checkCycleLength perm input step
 | step > 0 && perm == fromList ['a'..'p'] = step
 | True = checkCycleLength executedPlan input (step+1)
   where executedPlan = executePlan perm input

applyExecution :: Seq Char -> [String] -> Int -> Seq Char
applyExecution perm input step
 | step == 0 = perm
 | True = applyExecution (executePlan perm input) input (step-1)

solveProblem :: String -> String
solveProblem s = finalPerm ++ ", cycle length: " ++ show numsToRun ++ "\n"
  where pms = fromList ['a'..'p']
        len = DS.length pms - 1
        input = splitOn "," s
        cycleLength = checkCycleLength pms input 0
        numsToRun = 10^9 `mod` cycleLength
        finalPerm = toList $  applyExecution pms input numsToRun

main :: IO()
main = interact solveProblem
