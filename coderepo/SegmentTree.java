///Segment tree
	class SegmentTree {
		public SegmentTree left, right;
		public int start, end, max;
		
		public SegmentTree(int start, int end) {
			this. start = start;
			this.end = end;
			max = -(1<<29);
			if(start != end) {
				int mid = (start+end)>>1;
				left = new SegmentTree(start, mid);
				right = new SegmentTree(mid+1, end);
			}
		}
		
		//p = pos, v = value
		public void update(int p, int v) {
			if(start == p && end == p){
				this.max = v;
				return;
			}
			int mid = (start + end) >> 1;
			if (mid >= p) {
				left.update(p, v);
			} else {
				right.update(p, v);
			}
			this.max = Math.max(left.max, right.max);
		}
		
		public int query(int s, int e) {
			if (start == s && end == e) {
				return max;
			}
			int mid = (start + end) >> 1;
			if ( mid >= e) return left.query(s, e);
			else if(mid < s) return right.query(s, e);
			else {
				return Math.max(left.query(s, mid), right.query(mid+1, e));
			}
		}
	}


