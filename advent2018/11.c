/**
 * Prints all records, with increasing square size from 3.
 */
#include <stdio.h>
#include <math.h>

int main() {
  int SERIAL = 7139;
  int board[300][300];
  int sum[300][300];
  for (int y = 0; y < 300; y++) {
    for(int x =0;x <300; x++){
      int rackId = x+ 11;
      int power = (rackId * y + SERIAL) * rackId;
      board[y][x] = (power / 100)%10 - 5;
      //    printf("%d ", board[y][x]);
    }
    //printf("\n");
  }
  for(int y = 0; y< 300; y++){
    for(int x = 0; x < 300; x++){
      sum[y][x] = board[y][x];
      if(x>0 && y > 0){
        sum[y][x] += sum[y-1][x] + sum[y][x-1] - sum[y-1][x-1];
      }
      else if(y>0)  {
        sum[y][x] += sum[y-1][x];
      }
      else if(x>0)  {
        sum[y][x] += sum[y][x-1];
      }
    }
  }

  int best = -100;
  for(int w = 3; w< 290; w++)
    for(int x =0; x< 300-w; x++){
      for(int y =0; y<300-w; y++){
        int val = sum[y+w][x+w] - sum[y][x+w] - sum[y+w][x] + sum[y][x];

        if (val > best) {
          best = val;
          printf("%d,%d,%d,%d\n", x+2, y+1,w,best);
        }
      }
    }
  printf("%d\n", best);

}
