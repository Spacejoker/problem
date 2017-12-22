-- Solution to advent 2017, day 17
module Main where
import Data.Char
import Data.Bits
import Data.List as DL
import Data.List.Split
import Debug.Trace
import Data.Maybe
import Data.Sequence as DS
import Data.Foldable

spinLock :: Seq Int -> Int -> Int -> Int
spinLock state idx step
 | step == 2017 = nextVal
 | True = spinLock newSeq newIdx (step + 1)
   where newSeq = DS.insertAt newIdx step state
         newIdx = (idx + 348) `mod` (DS.length state) + 1
         nextVal = fromJust (DS.lookup ((newIdx+1)`mod`DS.length newSeq) newSeq)
 
--findFst :: Int -> Int -> Int
findFst pos step cur
 | step == 50000000 = newCur 
 | True = findFst newPos (step+1) newCur
   where newPos = (pos + 348) `mod` (step) + 1
   --where newPos = ((pos + 3) `mod` step) + 1
         newCur
           | newPos == 1 = step
           | True = cur
         
solve :: Int -> Int
solve a = spinLock (fromList [0]) 0 1

main :: IO()
main = putStrLn $ show $ findFst 0 1 0
