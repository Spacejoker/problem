-- Solution to advent 2019, day 2
module Main where
import Data.List
import Data.Char
import Data.List.Split


getMod 'U' = (1, 0)
getMod 'D' = (-1, 0)
getMod 'R' = (0, 1)
getMod 'L' = (0, -1)

generate' :: [String] -> (Int, Int, Int) -> [(Int, Int, Int)]-> [(Int, Int, Int)]
generate' [] _ res = res
generate' (t:xs) (y0, x0, dist) res
  | n < 0 = generate' xs (y0, x0, dist) res
  | otherwise = generate' ((dir:show n):xs) newPos newResult
    where n = read (tail t) - 1 :: Int
          dir = head t
          (ymod, xmod) = getMod dir
          newPos = (y0+ymod, x0 + xmod, dist+1)
          newResult = (newPos : res)

findDist :: [(Int, Int, Int)] -> [(Int, Int, Int)] -> (Int, Int) -> Int
findDist a b (y0, x0) = s1+s2
  where f = (\(y, x, z) -> y == y0 && x == x0)
        [(_,_,s1)] = filter f a
        [(_,_,s2)] = filter f b


secondStar :: [String] -> Int
secondStar (a:b:_) = minimum distances
  where agen = generate' (splitOn "," a) (0,0,0) []
        bgen = generate' (splitOn "," b) (0,0,0) []
        apos = map (\(x,y,z) -> (x,y)) agen
        bpos = map (\(x,y,z) -> (x,y)) bgen
        isec = Data.List.intersect apos bpos
        distances = map (findDist agen bgen) isec
        -- crossings = map (\(y, x, z) -> abs y + abs x) isec

generate :: [String] -> (Int, Int) -> [(Int, Int)]-> [(Int, Int)]
generate [] _ res = res
generate (t:xs) (y0, x0) res
  | n == 0 = generate xs (y0, x0) res
  | otherwise = generate ((dir:show n):xs) newpos newresult
    where n = read (tail t) - 1 :: Int
          dir = head t
          (ymod, xmod) = getMod dir
          newpos = (y0+ymod, x0 + xmod)
          newresult = (newpos : res)

firstStar :: [String] -> Int
firstStar (a:b:_) = minimum manhattan
  where agen = generate (splitOn "," a) (0,0) []
        bgen = generate (splitOn "," b) (0,0) []
        isec = Data.List.intersect agen bgen
        manhattan = map (\(y, x) -> abs y + abs x) isec


solve :: String -> String
solve s = "A: " ++ (show (firstStar (lines s)))
          ++ "B: " ++ (show (secondStar (lines s)))


main = interact solve
