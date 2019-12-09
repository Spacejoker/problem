-- Solution to advent 2019, day 2
module Main where
import Data.List
import Data.Char
import Data.Sequence as DS
import Data.List.Split


data Mode = Position | Immediate deriving (Show)

-- CPU state
data CpuState = CpuState { 
  pc :: Int, 
  memory :: Seq Int, 
  mode :: Mode,
  inputs  :: [Int],
  outputs :: [Int]
} deriving (Show)

data Instruction = Instruction {
  code :: Int,
  params :: [Int]
}

getArity :: Int -> Int
getArity ins
 | ins == 1 || ins == 2 || ins == 7 || ins == 8 = 3
 | ins == 3 || ins == 4 = 1
 | ins == 5 || ins == 6 = 2
 | otherwise = 0

getModes :: Int -> Int -> Int -> [Mode]
getModes value arity code
 | arity == 0 = []
 | arity == 1 && (code <= 2 || code >= 7 || code == 3) = [Immediate]
 | value `mod` 10 == 1 = (Immediate:getModes (value`quot`10) (arity - 1) code)
 | value `mod` 10 == 0 = (Position:getModes (value`quot`10) (arity - 1) code)
 | otherwise = []

readParams [] _ = []
readParams (Position:xs) cpu = (lookup:readParams xs  (cpu {pc = (1 + pc cpu)}))
  where memval = DS.index (memory cpu) (1 + pc cpu)
        lookup = DS.index (memory cpu) memval
readParams (Immediate:xs) cpu = (memval:readParams xs  (cpu {pc = (1 + pc cpu)}))
  where memval = DS.index (memory cpu) (1 + pc cpu)

parseInstruction :: CpuState -> Instruction
parseInstruction cpu = Instruction code params
  where raw = (DS.index (memory cpu) (pc cpu))
        code = raw `mod` 100
        modes = getModes (raw `quot` 100) (getArity code) code
        params = readParams modes cpu
        memval2 = DS.index (memory cpu) (2 + pc cpu)
        lookup2 = DS.index (memory cpu) memval2

apply :: CpuState -> Instruction -> CpuState
apply cpu ins = case (code ins)
   of 1 -> cpu' {memory = (DS.update (v!!2) ((v!!0) + (v!!1)) mem)}
      2 -> cpu' {memory = (DS.update (v!!2) ((v!!0) * (v!!1)) mem)}
      3 -> cpu' {memory = (DS.update (v!!0) (head (inputs cpu')) mem), inputs = tail (inputs cpu')}
      4 -> cpu' {outputs = (head v:outputs cpu')}
      5 -> case (v!!0) of 0 -> cpu'
                          _ -> cpu' {pc = v!!1}
      6 -> case (v!!0) of 0 -> cpu' {pc = v!!1}
                          _ -> cpu'
      7 -> case (v!!0) < (v!!1) of True  -> cpu' {memory = (DS.update (v!!2) 1 mem)}
                                   _     -> cpu' {memory = (DS.update (v!!2) 0 mem)}
      8 -> case (v!!0) == (v!!1) of True -> cpu' {memory = (DS.update (v!!2) 1 mem)}
                                    _    -> cpu' {memory = (DS.update (v!!2) 0 mem)}
      _ -> cpu'
  where mem = memory cpu
        pc' = (pc cpu) + 1 + (getArity (code ins))
        cpu' = cpu {pc = pc'}
        v = params ins

runProgram :: CpuState -> [Int]
runProgram cpu
 | code ins == 99 = outputs cpu -- [(DS.index (memory cpu)0 )]
 | otherwise = runProgram cpu'
   where ins = parseInstruction cpu
         cpu' = apply cpu ins

firstStar :: CpuState -> [Int]
firstStar cpu = runProgram cpu

secondStar :: Seq Int -> Int -> Int
secondStar seq _ = 0

buildMemory :: String -> Seq Int
buildMemory s = DS.fromList (map read (splitOn "," s))

initComputer :: String -> [Int] -> CpuState
initComputer s inputs = CpuState 0 initMemory Position inputs []
  where initMemory =  DS.fromList (map read (splitOn "," s))

solve :: String -> String
solve s = "A: " ++ (show (firstStar computer)) ++ "\n"
    -- ++ "B: " ++ (show (secondStar seq 0)) ++ "\n"
  where computer = initComputer s [5]

main = interact solve
