/**
 * Created by js on 2017-12-02.
 */
public class Advent3 {

    public static void main(String[] args) {
        System.out.println(new Advent3().nextNumber(325489));
    }

    public int nextNumber(int input) {
        int[][] board = new int[11][11];
        int x = 5, y = 5;
        int[][] dir = new int[][]{{0,1},{-1,0},{0,-1},{1,0}};
        int curdir = 0;
        board [y][x] = 1;
        for (int i = 0; i < 9*9 ; i ++) {
            int[] leftDir = dir[(curdir + 1) % 4];
            int leftX = x + leftDir[1];
            int lefty = y + leftDir[0];
            if (board[lefty][leftX] == 0) {
                curdir += 1;
                curdir %= 4;
            }
            x += dir[curdir][1];
            y += dir[curdir][0];
            int sum = 0;
            for (int z = 0 ; z < 9; z ++) {
                sum += board[y + z%3 - 1][x + z/3 - 1];
            }
            board[y][x] = sum;
            if (sum > input) {
                return sum;
            }
        }
        return -1;
    }
}
