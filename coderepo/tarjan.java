
	int[] num, lowlink, comp;
	Stack<Integer> stack;
	// List<Integer>[] edges;
	int no_vert, no_comp;
	private void tarjan(int v, List[] edges) {
		num[v] = no_vert;
		no_vert++;
		lowlink[v] = num[v];

		comp[v] = Integer.MAX_VALUE / 2;
		stack.push(v);
		for (int i = 0; i < edges[v].size(); i++) {
			int w = (Integer) edges[v].get(i);
			if (comp[w] == -1) {
				tarjan(w, edges);
				lowlink[v] = Math.min(lowlink[w], lowlink[v]);
			} else if (comp[w] == Integer.MAX_VALUE / 2) {
				lowlink[v] = Math.min(num[w], lowlink[v]);
			}
		}
		if (lowlink[v] == num[v]) {
			while (true) {
				int x = stack.pop();
				comp[x] = no_comp;
				if (x == v) {
					no_comp++;
					break;
				}
			}

		}

	}
