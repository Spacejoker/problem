import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class advent20 {

  public static void main(String[] args) throws Exception {
    new advent20().solve();
  }

  Map<String, String> m = new HashMap<>();

  private void solve() throws Exception {
    BufferedReader r = new BufferedReader(new FileReader("input21.txt"));
    String line = r.readLine();
    while (line !=null) {
      String[] dict = line.split(" => ");
      addRotKeyVal(dict[0], dict[1]);
      line = r.readLine();
    }

    String[] board = new String[]{".#.","..#","###"};
    for ( int i = 0 ; i < 18 ; i++ ) {
      String[] nextBoard = performStep(board);
      board = nextBoard;
      // System.out.println("Step;" + Arrays.toString(board));
    }
    String s = Arrays.toString(board);
    int cnt = 0;
    for ( int i = 0 ; i < s.length(); i++) {
      if (s.charAt(i) == '#') {
        cnt ++;
      }
    }

    System.out.println("Cnt: " + cnt);
    // System.out.println("Ans ;" + s);
  }

  private String[] performStep(String[] board) {
    boolean useSizeTwo = board.length % 2 == 0;

    int splitLength = useSizeTwo ? 2 : 3;
    int pieceSize = splitLength + 1;
    int numBreaks = board.length/splitLength;
    System.out.println("new width: " + numBreaks*pieceSize);
    char[][] newBoard = new char[numBreaks*pieceSize][numBreaks*pieceSize];
    for (int x0 = 0; x0 < numBreaks; x0++) {
      for (int y0 = 0 ; y0 < numBreaks; y0++) {
        String newKey = "";
        for(int y = 0; y < splitLength; y++) {
          for(int x = 0; x < splitLength; x++) {
            newKey += board[y+splitLength*y0].charAt(x+splitLength*x0);
          }
          newKey += "/";
        }
        String[] newSub = m.get(newKey.substring(0, newKey.length()-1)).split("/");
        for (int y = 0; y < newSub.length; y++) {
          for (int x = 0 ; x < newSub.length; x++) {
            newBoard[y0*newSub.length + y][x0*newSub.length+x] = newSub[y].charAt(x);
          }
        }
      }
    }
    String[] ret = new String[newBoard.length];
    for (int i = 0 ; i < newBoard.length; i++) {
      ret[i] = new String(newBoard[i]);
    }

    return ret;
  }

  private void addRotKeyVal(String key, String value) {
    String[] rots = genRots(key);
    String mirror1 = mirror(rots[0]);
    String mirror2 = mirror(rots[1]);
    String[] rot2 = genRots(mirror1);
    String[] rot3 = genRots(mirror2);
    Set<String> allVersions = new HashSet<>();
    for (int i = 0 ; i < 4; i++) {
      allVersions.add(rots[i]);
      allVersions.add(rot2[i]);
      allVersions.add(rot3[i]);
    }
    for (String s : allVersions) {
      if (m.containsKey(s)) {
        System.out.println("Duplicate: " + s);
      }
      m.put(s, value);
    }
  }

  private String mirror(String key) {
    String[] x = key.split("/");
    String ret = "";
    for (String row : x) {
      ret += new StringBuffer(row).reverse().toString();
      ret += "/";
    }
    return ret.substring(0, ret.length()-1);
  }

  /**
   * The 4 rotations of key
   */
  private String[] genRots(String key) {
    String[] rots = new String[4];
    rots[0] = key;
    String[] rows = key.split("/");
    String newKey = "";
    for (int x = rows.length-1; x >= 0; x--) {
      for (int y = 0; y < rows.length; y++) {
        newKey += rows[y].charAt(x);
      }
      newKey += "/";
    }
    newKey = newKey.substring(0, newKey.length() - 1);
    rots[1] = newKey;
    newKey = "";
    for (int y = rows.length - 1; y >= 0; y--) {
      for (int x = rows.length-1; x >= 0; x--) {
        newKey += rows[y].charAt(x);
      }
      newKey += "/";
    }
    newKey = newKey.substring(0, newKey.length() - 1);
    rots[2] = newKey;
    newKey = "";

    for (int x = 0 ; x < rows.length; x++) {
      for (int y = rows.length - 1;  y >= 0; y--) {
        newKey += rows[y].charAt(x);
      }
      newKey += "/";
    }
    newKey = newKey.substring(0, newKey.length() - 1);
    rots[3] = newKey;
    return rots;
  }

  private void go(String[] strings, int stepsLeft) {

  }
}
