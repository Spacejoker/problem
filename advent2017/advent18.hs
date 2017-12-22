-- Solution to advent 2017, day 18
module Main where
import Data.Char
import Data.Bits
import Data.List as DL
import Data.List.Split
import Debug.Trace
import Data.Maybe
import Data.Sequence as DS
import Data.Foldable
import Data.HashMap.Strict as HM
import Data.Int

findRcv :: Seq [String] -> Int64 -> HashMap String Int64 -> [Int64] -> (Int64, HashMap String Int64, [Int64])
findRcv insSet pc m q
 | ins == "set" = (newPc, (HM.insert opa bVal m), [])
 | ins == "add" = (newPc, (HM.insert opa (aVal + bVal) m), [])
 | ins == "mul" = (newPc, (HM.insert opa (aVal * bVal) m), [])
 | ins == "mod" = (newPc, (HM.insert opa (aVal `mod` bVal) m), [])
 | ins == "snd" = (newPc, m, [aVal])
 | ins == "rcv" = (newPc, HM.insert opa (head q) m, [])
 | ins == "jgz" = (newPc, m, [])
   where opb = head insTail
         aVal = if all isDigit opa then read opa else lookupDefault 0 opa m
         bVal = if all (\x -> isDigit x || x=='-') opb then read opb else lookupDefault 0 opb m
         (ins:opa:insTail) = fromJust $ DS.lookup (fromIntegral pc) insSet
         --(ins:opa:insTail) = trace("pc: " ++ show pc ++ ", insSet: "++ show insSet ++ ", m; " ++ show m) fromJust $ DS.lookup (fromIntegral pc) insSet
         newPc = (if ins == "jgz" && aVal > 0 then pc + bVal else pc + 1)
         --newPc = trace("Happens, ins: " ++ ins ++ ", opa: " ++ opa ++ ", q: " ++ show q ++ ", map: " ++ show m) (if ins == "jgz" && aVal > 0 then pc + bVal else pc + 1)

runSim :: Seq [String] -> Int64 -> HashMap String Int64 -> [Int64] -> Int64 -> HashMap String Int64 -> [Int64] -> Int -> Int
runSim insSet pc0 m0 q0 pc1 m1 q1 sendCnt
 -- Run p0 
 | aIns == "snd" = trace("0 send")runSim insSet pcA mA q0 pc1 m1 (q1++qretA) sendCnt
 | bIns == "snd" = trace("1 send") runSim insSet pc0 m0 (q0++qretB) pcB mB q1 (sendCnt+1)
 | aIns /= "rcv" = runSim insSet pcA mA q0 pc1 m1 q1 sendCnt
 | bIns /= "rcv" = runSim insSet pc0 m0 q0 pcB mB q1 sendCnt
 | DL.length q0 > 0 = trace("0 recieve" ++ show q0)runSim insSet pcA mA (tail q0) pc1 m1 q1 sendCnt
 | DL.length q1 > 0 = trace("1 recieve" ++ show q1)runSim insSet pc0 m0 q0 pcB mB (tail q1) sendCnt
 | True = sendCnt
   where (aIns:_) = fromMaybe [""] (DS.lookup (fromIntegral pc0) insSet)
         (bIns:_) = fromMaybe [""] (DS.lookup (fromIntegral pc1) insSet)
         (pcA, mA, qretA) = findRcv insSet pc0 m0 q0
         (pcB, mB, qretB) = findRcv insSet pc1 m1 q1



solve :: String -> String
solve input = show (runSim ins 0 (HM.singleton "p" 0) [] 0 (HM.singleton "p" 1) [] 0) ++ "\n"
  where ins = DS.fromList $ DL.map (splitOn " ") (lines input)

main :: IO()
main = interact solve
