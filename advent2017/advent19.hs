-- Solution to advent 2017, day 19
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

runPath :: Seq (Seq Char) -> (Int, Int) -> (Int, Int) -> Int
runPath board (yPos, xPos) (yDir, xDir)
 | nextChar /= ' ' =1 + runPath board nextPos (yDir, xDir)
 | DL.length newDirPos > 0 = 1 + runPath board a ((fst a) - yPos, (snd a) - xPos)
 | True = 1
   where nextChar = findAt nextPos board
         nextPos = (yPos + yDir, xPos + xDir)
         dirs = [(y, x) | (y, x) <- [(-1, 0),(1, 0), (0, -1), (0, 1)]]
         newDirPos = DL.filter (\(y, x) -> (y /= yPos -yDir && x /= xPos - xDir) && (findAt (y, x) board) /= ' ') (DL.map (\(y, x) -> (yPos + y, xPos + x)) dirs)
         ans = DL.filter ((==) (findAt (yPos, xPos) board)) ['A'..'Z']
         a = (DL.head newDirPos)

findAt :: (Int, Int) -> Seq (Seq Char) -> Char
findAt (y, x) board = fromMaybe ' ' (DS.lookup x (fromMaybe DS.empty (DS.lookup y board)))

solve :: String -> String
solve input = show (runPath board (0,x0) (1,0)) ++ "\n"
  where board = DS.fromList $ DL.map DS.fromList (lines input)
        fstLine = fromJust (DS.lookup 0 board)
        x0 = fromJust $ elemIndexL '|' fstLine

main :: IO()
main = interact solve
