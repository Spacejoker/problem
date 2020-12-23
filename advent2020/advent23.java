import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class advent23 {

  public static void main(String[] args) throws Exception {
    new advent23(true);
    new advent23(false);
  }

  public advent23(boolean test) throws Exception {
    run(test);
  }

  private void run(boolean test) throws Exception {
    String[] input = readLines(test);
    long t0 = System.currentTimeMillis();
    String solutionA = solveFirst(input);
    String solutionB = solveSecond(input);
    long t1 = System.currentTimeMillis();

    System.out.printf("Answer 1: %s\nAnswer 2: %s\n", solutionA, solutionB);
    System.out.printf("Time: %dms\n", t1 - t0);
  }

  private String solveFirst(String[] input) {
    String s = input[0];
    int[] v = new int[s.length()];
    for(int i =0 ; i < s.length(); i++){
      v[i] = s.charAt(i) - '0';
    }
    HashSet<String> seen = new HashSet<>();
    int curCupIdx = 0;
    for(int step = 0; step < 100; step++) {
      boolean[] forbidden = new boolean[9];
      forbidden[(curCupIdx+1)%9] = true;
      forbidden[(curCupIdx+2)%9] = true;
      forbidden[(curCupIdx+3)%9] = true;
      int[] forbiddenValues = new int[3];
      forbiddenValues[0] = v[(curCupIdx+1)%9];
      forbiddenValues[1] = v[(curCupIdx+2)%9];
      forbiddenValues[2] = v[(curCupIdx+3)%9];

      int curCup = v[curCupIdx];
      int targetIdx = 0;
      out:for (int target = curCup -1; ;target --) {
        if (target == 0){
          target = 9;
        }
        for (int i = 0; i < 5; i++) {
          int idx = (i+curCupIdx+4)%9;
          if (v[idx] == target) {
            targetIdx = idx;
            break out;
          }
        }
      }
      int[] next = new int[v.length];
      int targetPos = curCupIdx;
      for (int i = 0; i < 9; i++) {
        int sourceIncrement = (i + curCupIdx)%9;
        if (sourceIncrement == curCupIdx) {
          next[targetPos%9] = v[sourceIncrement];
          targetPos++;
        } else if (sourceIncrement == targetIdx){
          next[targetPos%9] =v[sourceIncrement];
          targetPos++;
          next[targetPos%9] =v[(curCupIdx+1)%9];
          targetPos++;
          next[targetPos%9] =v[(curCupIdx+2)%9];
          targetPos++;
          next[targetPos%9] =v[(curCupIdx+3)%9];
          targetPos++;
        } else if (!forbidden[sourceIncrement]) {
          next[targetPos%9] = v[sourceIncrement];
          targetPos++;
        }
      }
      v = next;
      for(int i =0 ; i <9 ;i++) {
        if (v[i] == curCup) {
          curCupIdx = (i+1)%9;
          break;
        }
      }
      String strval = toValue(v);
      if (seen.contains(strval)) {System.out.println("dupe: " +  strval + ", step: " + step);
      break;};
      seen.add(strval);
      //System.out.println(toValue(v));
    }
    String val = toValue(v);
    String[] vsplit = val.split("1");
    //new String(ans).split("1");
    return "";
  }

  private String toValue(int[] v) {
    StringBuilder s = new StringBuilder();
    for (int i= 0; i < v.length;  i++) {
      s.append( v[i] + "");
    }
    return s.toString();
  }

  class Node {
    Node next;
    int value;
  }

  private String solveSecond(String[] input) {
    Map<Integer, Node> valueToNode = new HashMap<>();
    Node curCup = null, lastNode = null;
    String s = input[0];
    int numNodes = 1000000, numSteps = 10000000;
    for(int i =0 ; i < s.length(); i++){
      int val = s.charAt(i) - '0';
      Node n = new Node();
      n.value = val;
      valueToNode.put(val, n);
      if (i==0) {
        curCup = n;
      } else {
        lastNode.next = n;
      }
      lastNode = n;
    }
    for (int i = 10; i<=numNodes; i++) {
      Node n = new Node();
      n.value = i;
      lastNode.next = n;
      valueToNode.put(i, n);
      lastNode = n;
    }
    lastNode.next = curCup;

    for(int step = 0; step < numSteps; step++) {
      Node n1 = curCup.next;
      Node n2 = n1.next;
      Node n3 = n2.next;
      int destinationValue = curCup.value - 1;
      if (destinationValue == 0) destinationValue = numNodes;
      while(destinationValue == n1.value || destinationValue == n2.value || destinationValue == n3.value) {
        destinationValue --;
        if (destinationValue <= 0) destinationValue = numNodes;
      }
      Node destinationCup = valueToNode.get(destinationValue);
      curCup.next = n3.next;
      n3.next = destinationCup.next;
      destinationCup.next = n1;
      curCup = curCup.next;
    }
    Node one = valueToNode.get(1);
    return "" + (long)(one.next.value) * (long)(one.next.next.value);
  }

  private String printSequence(Node next) {
    String s = "";
    while(next.value != 1) {
      s += next.value;
      next = next.next;
    }
    return s;
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
