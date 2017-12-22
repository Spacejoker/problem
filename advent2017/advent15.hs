module Main where
import Data.Char
import Data.Bits
import Data.List as DL
import Data.List.Split
import Debug.Trace
import Data.Maybe

generator :: Int -> Int -> Int -> Int
generator m divider prev
 | nextVal `mod` divider == 0 = nextVal
 | True = generator m divider nextVal
   where nextVal = m * prev `mod` (2^31 - 1)

countSame :: (Int -> Int) -> (Int -> Int) -> Int -> Int -> Int -> Int
countSame _ _ _ _ 0 = 0
countSame aGen bGen a b steps = (if ((.&.) ((.&.) a b) (2^16-1) == 0) then 1 else 0) + (countSame aGen bGen (aGen a) (bGen b) (steps -1))

main :: IO()
main = putStrLn $ "Count: " ++ (show $ countSame aGen bGen (aGen 289) (bGen 629) numSteps)
    where numSteps = 5*10^6
          aGen = generator 16807 4
          bGen = generator 48271 8

