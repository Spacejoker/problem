import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class advent24 {

  public static void main(String[] args) throws Exception {
    new advent24(true);
    new advent24(false);
  }

  public advent24(boolean test) throws Exception {
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

  class Point {
    int x, y;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public String toString() {
      return "Point [x=" + x + ", y=" + y + "]";
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + getEnclosingInstance().hashCode();
      result = prime * result + x;
      result = prime * result + y;
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
      Point other = (Point) obj;
      if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
        return false;
      if (x != other.x)
        return false;
      if (y != other.y)
        return false;
      return true;
    }

    private advent24 getEnclosingInstance() {
      return advent24.this;
    }

  }

  private String solveFirst(String[] input) {
    Set<Point> flipped = new HashSet<>();
    for (String row : input) {
      int x = 0, y = 0;
      for (int i = 0; i < row.length(); i++) {
        char next = row.charAt(i);
        char snd = 0;
        if (i < row.length() - 1) {
          snd = row.charAt(i + 1);
        }
        if (next == 'n' || next == 's') {
          i++;
          String w = "" + next + snd;
          if (w.equals("sw")) {
            x--;
            y--;
          }
          if (w.equals("nw")) {
            x--;
            y++;
          }
          if (w.equals("se")) {
            x++;
            y--;
          }
          if (w.equals("ne")) {
            x++;
            y++;
          }
        } else {
          if (next == 'e')
            x += 2;
          if (next == 'w')
            x -= 2;
        }
      }
      Point pt = new Point(x, y);
      if (flipped.contains(pt))
        flipped.remove(pt);
      else
        flipped.add(pt);

    }
    return flipped.size() + "";
  }

  private String solveSecond(String[] input) {
    Set<Point> flipped = new HashSet<>();
    for (String row : input) {
      int x = 0, y = 0;
      for (int i = 0; i < row.length(); i++) {
        char next = row.charAt(i);
        char snd = 0;
        if (i < row.length() - 1) {
          snd = row.charAt(i + 1);
        }
        if (next == 'n' || next == 's') {
          i++;
          String w = "" + next + snd;
          if (w.equals("sw")) {
            x--;
            y--;
          }
          if (w.equals("nw")) {
            x--;
            y++;
          }
          if (w.equals("se")) {
            x++;
            y--;
          }
          if (w.equals("ne")) {
            x++;
            y++;
          }
        } else {
          if (next == 'e')
            x += 2;
          if (next == 'w')
            x -= 2;
        }
      }
      Point pt = new Point(x, y);
      if (flipped.contains(pt))
        flipped.remove(pt);
      else
        flipped.add(pt);

    }
    int[][] dirs = new int[][] { { 2, 0 }, { -2, 0 }, { 1, 1 }, { -1, 1 }, { 1, -1 }, { -1, -1 } };

    int steps = 100;
    int dim = 500;
    for (int i = 0; i < steps; i++) {
      Set<Point> nextSet = new HashSet<>();
      for (int x0 = dim * -1; x0 <= dim; x0++) {
        for (int y0 = dim * -1; y0 <= dim; y0++) {
          if ((y0 + 10000) % 2 != (x0 + 10000) % 2) {
            if (flipped.contains(new Point(x0, y0))) {
              System.out.println("CHAOS");
            }
            continue;
          }
          Point pt = new Point(x0, y0);
          boolean isBlack = flipped.contains(pt);
          int adjacent = 0;
          for (int[] d : dirs) {
            Point nextPt = new Point(x0 + d[0], y0 + d[1]);
            adjacent += flipped.contains(nextPt) ? 1 : 0;
          }
          if (isBlack && (adjacent == 0 || adjacent > 2)) {
            isBlack = false;
          } else if (!isBlack && adjacent == 2) {
            isBlack = true;
          }
          if (isBlack) {
            nextSet.add(pt);
          }
        }
      }
      flipped = nextSet;
    }
    return flipped.size() + "";

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
