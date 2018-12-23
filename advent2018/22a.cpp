#include <iostream>
using namespace std;

long mod = 20183;
long depth = 9465;
int targetY = 704;
int targetX = 13;
long vals[1000][1000];
//long depth = 501;
//int targetY = 10;
//int targetX = 10;
//long vals[11][11];

int main() {

  for(long i =1; i<=targetY; i++){
    vals[i][0] = 48271 * i + depth;
    vals[i][0] %= mod;
  }
  for(long i =1; i<=targetX; i++){
    vals[0][i] = 16807 * i + depth;
    vals[0][i] %= mod;
  }
  vals[0][0] = depth % mod;

  for(int i =1; i<=targetY; i++){
    for(int j =1; j<=targetX; j++){
      vals[i][j] = vals[i][j-1] * vals[i-1][j] + depth;
      vals[i][j] %= mod;
    }
  }
  vals[targetY][targetX]=depth%mod;
  long sum = 0;
  for(int i=0; i <= targetY; i++){
    for(int j =0 ; j <= targetX; j++){
      int val = vals[i][j];
      val %= 3;
      sum += val;
    }
  }
  cout << "Hello, World!\n" << sum << std::endl;
  return 0;
}
