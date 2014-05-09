public class BinaryIndexedTree {

   int max;
   private int[] tree;
   
   public BinaryIndexedTree(int max) {
       this.max = max+1;
       tree = new int[max+1];
   }

   /**
    * sums values from 1 to index
    * @param i
    */
   public int get(int from, int to) {
       if(from == 0){
           int sum = 0;
           for (;to >= 0; to = (to & (to + 1)) - 1) {
               sum += tree[to];
           }
           return sum;
       } else {
           return get(0, to) - get(0, from);
       }
   }    
   
   public void increase(int k, int value){
       for (; k < tree.length; k |= k + 1) {
           tree[k] += value;
       }
   }
}

