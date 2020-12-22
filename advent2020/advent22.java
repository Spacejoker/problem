import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class advent22 {

  public static void main(String[] args) throws Exception {
    new advent22(true);
    new advent22(false);
  }

  public advent22(boolean test) throws Exception {
    run(test);
  }

  private void run(boolean test) throws Exception {
    String[] input = readLines(test);
    long t0 = System.currentTimeMillis();
    long solutionA = solveFirst(input);
    long solutionB = solveSecond(input);
    long t1 = System.currentTimeMillis();

    System.out.printf("Answer 1: %d\nAnswer 2: %d\n", solutionA, solutionB);
    System.out.printf("Time: %dms\n", t1 - t0);
  }

  private long solveFirst(String[] input) {
    int player = 0;
    LinkedList<Integer> deckA = new LinkedList<>();
    LinkedList<Integer> deckB = new LinkedList<>();
    for (int i = 0; i< input.length ; i++ ){
      if (input[i].equals("")) {
        player ++;
        continue;
      }
      if (input[i].contains("Player")) continue;
      int val = Integer.valueOf(input[i]);
      if (player == 0) {
        deckA.offer(val);
      } else {
        deckB.offer(val);
      }
    }
    while(!deckA.isEmpty() && !deckB.isEmpty()){
      int a = deckA.poll();
      int b = deckB.poll();
      if (a>b) {deckA.offer(a);deckA.offer(b);}
      else if (b>a) {deckB.offer(b);deckB.offer(a);}
    }
    if (deckA.isEmpty()) deckA = deckB;
    return scoreDeck(deckA);
  }

  private int scoreDeck(List<Integer> deckA) {
    int ans = 0;
    for(int i= 0; i < deckA.size(); i++) {
      ans += deckA.get(i) * (deckA.size() - i);
    }
    return ans;
  }

  private long solveSecond(String[] input) {
    int player = 0;
    List<Integer> deckA = new ArrayList<>();
    List<Integer> deckB = new ArrayList<>();
    for (int i = 0; i< input.length ; i++ ){
      if (input[i].equals("")) {
        player ++;
        continue;
      }
      if (input[i].contains("Player")) continue;
      int val = Integer.valueOf(input[i]);
      if (player == 0) {
        deckA.add(val);
      } else {
        deckB.add(val);
      }
    }
    long ans = recurse(deckA, deckB).winSum;
    System.out.printf("Rec: %d, Fullcnt: %d, rounds: %d\n", recCnt, fullPlay, rounds);
    return ans;
  }

  Map<Pair,  Result> cache = new HashMap<>();

  long recCnt = 0, fullPlay = 0, rounds = 0;
	private Result recurse(List<Integer> deckA, List<Integer> deckB) {
    recCnt ++;
    Pair cacheKey = new Pair(scoreDeck(deckA), scoreDeck(deckB));
    if (cache.containsKey(cacheKey)) {return cache.get(cacheKey);}
    fullPlay ++;

    Set<String> seen = new HashSet<>();
    while(!deckA.isEmpty() && !deckB.isEmpty()){
      rounds ++;
      int a = deckA.get(0);
      int b = deckB.get(0);
      deckA.remove(0);
      deckB.remove(0);
      boolean aWon = true;
      StringBuilder hashedgameBuilder = new StringBuilder();
      for (int i =0; i < deckA.size(); i++) {
        hashedgameBuilder.append(deckA.get(i) + ",");
      }
      hashedgameBuilder.append("~");
      for (int i =0; i < deckB.size(); i++) {
        hashedgameBuilder.append(deckB.get(i) + ",");
      }
      String hashedGame = hashedgameBuilder.toString();
      // Is this a seen game?
      if (seen.contains(hashedGame)) {
        aWon = true;
      } else if (a <= deckA.size() && b <= deckB.size()) {
        List<Integer> aCopy = new ArrayList<>(a);
        List<Integer> bCopy = new ArrayList<>(b);
        for (int i =0; i < a; i++) {
          aCopy.add(deckA.get(i));
        }
        for (int i =0; i < b; i++) {
          bCopy.add(deckB.get(i));
        }
        aWon = recurse(aCopy, bCopy).aWon;
      } else {
        aWon = a > b;
      }
      seen.add(hashedGame);
      if (aWon) {deckA.add(a);deckA.add(b);}
      else {deckB.add(b);deckB.add(a);}
    }
    boolean aWon = !deckA.isEmpty();
    if (deckA.isEmpty()) deckA = deckB;
    Result res = new Result(aWon, scoreDeck(deckA));
    cache.put(cacheKey, res);
    return res;
  }
  class Result {
    boolean aWon;
    int winSum;

    public Result(boolean aWon, int winSum) {
      this.aWon = aWon;
      this.winSum = winSum;
    }
  }

  class Pair {
    int a, b;

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + getEnclosingInstance().hashCode();
      result = prime * result + a;
      result = prime * result + b;
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      Pair other = (Pair) obj;
      if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
        return false;
      if (a != other.a)
        return false;
      if (b != other.b)
        return false;
      return true;
    }

    private advent22 getEnclosingInstance() {
      return advent22.this;
    }

    public Pair(int a, int b) {
      this.a = a;
      this.b = b;
    }
  }


  String[] readLines(boolean test) throws Exception {
		String filename = test ? "advent2020/test.txt" : "advent2020/input.txt";
		BufferedReader in = new BufferedReader(new FileReader(filename));
		List<String> values = new ArrayList<>();
		String s;
		while ((s = in.readLine()) != null) {
			values.add(s);
		}
		in.close();
		return values.stream().toArray(String[]::new);
	}
}
