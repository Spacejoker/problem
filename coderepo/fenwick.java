

class FenwickTree{
		
		long[] tree;

		public FenwickTree(int maxValue) {
			super();
			this.tree = new long[maxValue + 1];
		}
		
		long read(int idx){
			if(idx == 0){
				return 0;
			}
			
			long ret = 0;
			while(idx > 0){
				ret += tree[idx];
				idx -= (-idx & idx);
			}
			return ret;
		}
		void add(int idx, long num){
			
			while(idx < tree.length){
				tree[idx] += num;
				idx += -idx & idx;
			}
		}
	}
