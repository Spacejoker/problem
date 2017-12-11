-- Solution to advent 2017, day 6
module Main where
import Data.Char
import Data.List

evalGarbage :: String -> Int -> Bool -> Int
evalGarbage [] _ _ = 0
evalGarbage (h:t) depth garbage
 | h == '!' = evalGarbage (tail t) depth garbage
 | garbage == False && h == '<' = evalGarbage t depth True
 | garbage == True && h /= '>' = 1 + evalGarbage t depth True
 | garbage == True && h == '>' = evalGarbage t depth False
 | garbage == False && h == '}' = evalGarbage t (depth - 1) False
 | garbage == False && h == '{' = evalGarbage t (depth + 1) False
 | True = evalGarbage t depth garbage

evalScore :: String -> Int -> Bool -> Int
evalScore [] _ _ = 0
evalScore (h:t) depth garbage
 | h == '!' = evalScore (tail t) depth garbage
 | garbage == False && h == '<' = evalScore t depth True
 | garbage == True = evalScore t depth (h /= '>')
 | garbage == False && h == '}' = depth + (evalScore t (depth - 1) False)
 | garbage == False && h == '{' = evalScore t (depth + 1) False
 | True = evalScore t depth garbage

solve :: String -> String
solve s = (show $ evalScore s 0 False) ++ ", " ++ (show $ evalGarbage s 0 False)

main :: IO()
main = interact solve
