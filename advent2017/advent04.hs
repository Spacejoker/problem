-- Solution to advent 2017, day 4
module Main where
import Data.Char
import Data.List

checkWords :: [String] -> Bool
checkWords [] = True
checkWords (x:xs) = x `notElem` xs && checkWords xs

isPassphrase :: String -> Bool
isPassphrase line = checkWords (words line)

isTougherPassphrase :: String -> Bool
isTougherPassphrase line = checkWords $ map sort (words line)


main :: IO()
main = do
  fileContent <- readFile "input4.txt"
  putStrLn $ show $ length $ filter isPassphrase (lines fileContent)
  putStrLn $ show $ length $ filter isTougherPassphrase (lines fileContent)

