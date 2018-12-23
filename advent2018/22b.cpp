#include <iostream>
#include <queue>
using namespace std;

long mod = 20183;
long depth = 9465;
//int targetY = 704;
//int targetX = 13;
const int boardsize = 1000;
const int arraysize = 1001;
long vals[arraysize][arraysize];
int seen[arraysize][arraysize][3];
int searchdim = 1000;

//long depth = 510;
long targetX = 704;
long targetY = 13;
//vals[11][11];

class State {
 public:
  int x, y, eq, steps;
  State(int x, int y, int eq, int steps): x(x),y(y),eq(eq),steps(steps) {}
};

int main() {

  for(long i =1; i<=searchdim; i++){
    vals[i][0] = 48271 * i + depth;
    vals[i][0] %= mod;
  }
  for(long i =1; i<=searchdim; i++){
    vals[0][i] = 16807 * i + depth;
    vals[0][i] %= mod;
  }
  vals[0][0] = depth % mod;

  for(int i =1; i<=searchdim; i++){
    for(int j =1; j<=searchdim; j++){
      if (i == targetY && j == targetX) {
        vals[i][j] = depth;
      } else {
        vals[i][j] = vals[i][j-1] * vals[i-1][j] + depth;
      }
      vals[i][j] %= mod;
    }
  }

  for(int i=0; i<=searchdim;i++){
    for(int j = 0; j<  searchdim; j++){
      vals[i][j] %= 3;
      //cout << (vals[i][j]==0 ? '.' : (vals[i][j]==1 ? '=' : '|'));
    }
    //cout << "\n";
  }

  for(int i =0; i<=searchdim; i++){
    for(int j =0; j<=searchdim; j++){
      seen[i][j][0] = -1;
      seen[i][j][1] = -1;
      seen[i][j][2] = -1;
    }
  }

  seen[0][0][1] = 0;
  std::queue<State> queue;
  queue.push(State(0, 0, 1,0));
  int dir[4][2] = {{0,1},{0,-1},{1,0},{-1,0}};
  // 0 -> 1|2
  // 1 -> 0|2
  // 2 -> 1|2
  while(queue.size() > 0) {
    State s = queue.front();
    queue.pop();
    if (s.x == targetX && s.y == targetY && s.eq == 1){
      cout << s.steps << ", " << s.eq << ", " << vals[s.y][s.x] << "\n";
    }
    int steps = s.steps;
    for (int i =0 ; i < 4; i++){
      int xx = s.x + dir[i][0];
      int yy = s.y + dir[i][1];
      if ( xx < 0 ||  yy < 0 || xx >searchdim || yy > searchdim) {
        continue;
      }
      int c = vals[yy][xx];
      for(int toolswitch = 0; toolswitch < 3; toolswitch ++) {
        int newTool = (s.eq + toolswitch)%3;
        if (newTool == c) {continue;}
        int newSteps = steps + (toolswitch > 0 ? 8 : 1);
        int t1 = seen[yy][xx][newTool];
        if (t1 <0 || newSteps < t1) {
          queue.push(State(xx,yy,newTool,newSteps));
          seen[yy][xx][newTool] = newSteps;
        }
      }
    }
  }
  cout << "Hello, World!\n" << std::endl;
  return 0;
}
