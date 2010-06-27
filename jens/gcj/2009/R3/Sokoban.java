package gcj;

import gcj.DigginTwo.Res;
import gcj.DigginTwo.State;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Sokoban {

//	final String f = "A-small-practice(3).in";
	final String f = "A-large-practice(3).in";
//	final String f = "test.in";
	
	private BufferedReader in;
	private PrintWriter out;
	private StringTokenizer st;
	
	List<Node> enqueuedNodes;
	
;	Sokoban() throws IOException {
		in = new BufferedReader(new FileReader(f));
		out = new PrintWriter(f + ".out");
		
		eat("");
		
		long start = System.currentTimeMillis();
		
		int tests = nextInt();

		for (int test = 0; test < tests; ++test) {
			System.out.println("Test #" + (test + 1));
			out.print("Case #" + (test + 1) + ": ");
			enqueuedNodes = new Vector<Node>(); 
			boxes = new ArrayList<Position>();
			targets = new ArrayList<Position>();
			solve();
			System.gc();
		}
		
		System.out.println("Time: " + (System.currentTimeMillis() - start));
		System.out.println("endConditinonTime: " + endConditinonTime);
		
		in.close();
		out.close();
	}
	
	
	
	List<Position> boxes;
	List<Position> targets;
	int height;
	int width;
	int[][] board;
	List<SortedSet<Position>> visitedPositionSets;
	
	private void solve() throws IOException {
		height = nextInt();
		width = nextInt();
		
		board = new int[height][width];

		//find the boxes and goals!
		for (int i = 0; i < height; i++) {
			String line = next();
			for (int j = 0; j < width; j++) {
				char tileChar = line.charAt(j);
				board[i][j] = 0;
				
				switch(tileChar){
				case '#':
					board[i][j] = 1;
				break;
				case 'x':
					targets.add(new Position(j, i));
					break;
				case 'o':
					boxes.add(new Position(j, i));
					break;	
				case 'w':
					targets.add(new Position(j, i));
					boxes.add(new Position(j, i));
					break;	
					//wall
				}
			}
		}
		
		Position[] inBoxes = new Position[targets.size()];
		Position[] inTargets = new Position[targets.size()];
		
		for (int i = 0; i < inTargets.length; i++) {
			inBoxes[i] = boxes.get(i);
			inTargets[i] = targets.get(i);
		}
		
		//now we got our boxes etc...
		int best = Integer.MAX_VALUE;
		best = moveBoxes(inBoxes,0,best);
		System.out.println(best == Integer.MAX_VALUE ? -1 : best);
		out.println(best == Integer.MAX_VALUE ? -1 : best);
	}
	
	static long endConditinonTime = 0;
	
	//use linked list implementation to get bfs behaviour
	private int moveBoxes(Position[] inBoxes, int currentVal, int best) {

		LinkedList<Node> nextQueue = new LinkedList<Node>();

		//Enqueue root
		Node n = createNode(Arrays.asList(inBoxes), 0);
		nextQueue.offer(n);
	 	
		while (true) {
	 
			//Dequeue next node for comparison
			//And add it to list of traversed nodes
			Node node = null;
			try {
				node = nextQueue.remove();
			} catch (NoSuchElementException nsee) {
				break; // exit early if we're out of next elements to search...
			}
			// check end condition
			boolean match = false;
			for (int i = 0; i < inBoxes.length; i++) {
				match = false;
				for (int j = 0; j < inBoxes.length; j++) {
					if (node.positions.get(i).x == targets.get(j).x && node.positions.get(i).y == targets.get(j).y) {
						match = true;
						break;
					}
				}
				if (!match) {
					break;
				}
			}
			
			if (match) {
				// if we get here it was the shortest path, bfs
				return node.steps;
			}
			else {
				//Enqueue new neighbors
				
				//get mode, if unsafe that limits us a bit... which is good

				boolean safeMode = isSafeMode(node.positions);
//				System.out.println(safeMode);
				
				List<Position> tmpPos = new ArrayList<Position>();
				tmpPos.addAll(node.positions);
				//test all possible moves on all boxes... hmm, is this smart?
				for (int i = 0; i < node.positions.size(); i++) {
					
					Position tmpBox = null;  
					Position box = tmpPos.get(i);
					
					if(box.y >= 0  
							&& box.y < height 
							&& box.x > 0  
							&& box.x < width - 1 
							&& board[box.y][box.x-1] == 0 
							&& board[box.y][box.x+1] == 0 
							&& noHorizionalBox(tmpPos, box, false)){ // safe on right and left
						
						//new position
						tmpBox = new Position(tmpPos.get(i).x-1, tmpPos.get(i).y);
						
						tmpPos.remove(i);
						tmpPos.add(i, tmpBox);
						
						enqueueNodeIfAppropriate(nextQueue, node, safeMode, tmpPos, i, tmpBox);
						tmpBox = new Position(tmpPos.get(i).x+2, tmpPos.get(i).y);
						
						tmpPos.remove(i);
						tmpPos.add(i, tmpBox);
						
						enqueueNodeIfAppropriate(nextQueue, node, safeMode, tmpPos, i, tmpBox);
						tmpBox = new Position(tmpPos.get(i).x-1, tmpPos.get(i).y);
						tmpPos.remove(i);
						tmpPos.add(i, tmpBox);
					}
					
					//do the same for vertical
					if(box.y > 0  
							&& box.y < height - 1 
							&& box.x >= 0  
							&& box.x < width 
							&& board[box.y-1][box.x] == 0 
							&& board[box.y+1][box.x] == 0
							&& noHorizionalBox(tmpPos, box, true)){ // safe on right and left
						//new position
						tmpBox = new Position(tmpPos.get(i).x, tmpPos.get(i).y-1);
						
						tmpPos.remove(i);
						tmpPos.add(i, tmpBox);
						
						enqueueNodeIfAppropriate(nextQueue, node, safeMode, tmpPos, i, tmpBox);
						tmpBox = new Position(tmpPos.get(i).x, tmpPos.get(i).y+2);
						
						tmpPos.remove(i);
						tmpPos.add(i, tmpBox);
						
						enqueueNodeIfAppropriate(nextQueue, node, safeMode, tmpPos, i, tmpBox);
						tmpBox = new Position(tmpPos.get(i).x, tmpPos.get(i).y-1);
						tmpPos.remove(i);
						tmpPos.add(i, tmpBox);
					}
				}
			}
		}
		return Integer.MAX_VALUE;
	}

	private boolean noHorizionalBox(List<Position> tmpPos, Position box, boolean upDown) {
		
		for (int i = 0; i < tmpPos.size(); i++) {
			Position position = tmpPos.get(i);
			
			if(box.x != position.x || box.y != position.y){
				
				//some other box, is it interfering horizionaly?
				if(!upDown){
					if(box.y == position.y && (box.x +1 == position.x || box.x -1 == position.x)){
						return false;
					}					
				} else { //updown
					if(box.x == position.x && (box.y +1 == position.y || box.y -1 == position.y)){
						return false;
					}
				}
				
			}
		}
		
		return true;
	}

	private void enqueueNodeIfAppropriate(LinkedList<Node> nextQueue, Node node, boolean safeMode, List<Position> tmpPos, int i, Position tmpBox) {
		if(safeMode || isSafeMode(tmpPos)){ 			//also check that its ok from a safe mode perspective
			for (int j = 0; j < tmpPos.size(); j++) {
				if(i != j){
					if( tmpPos.get(j).x == tmpBox.x &&  tmpPos.get(j).y == tmpBox.y){
						return;
					}
				}
			}
			
			enqueueNode(tmpPos, nextQueue, node);
			
			
		}
	}

	private void enqueueNode(List<Position> inBoxes, LinkedList<Node> nextQueue, Node node) {
		Node createNode = createNode(inBoxes,node.steps +1);
		long tmp = System.currentTimeMillis();
		
		if(!enqueuedNodes.contains(createNode)){
			nextQueue.offer(createNode);
			enqueuedNodes.add(createNode);	
		}
		endConditinonTime += System.currentTimeMillis() - tmp;
	}
	
	static long nextId = 0;

	private Node createNode(List<Position> inBoxes, int i) {
		Node n = new Node(nextId++);
		ArrayList<Position> list = new ArrayList<Position>();
		for (int j = 0; j < inBoxes.size(); j++) {
			Position p = new Position(inBoxes.get(j).x, inBoxes.get(j).y);
			list.add(p);
		}
		n.steps = i;
		n.positions = list;
		return n;
	}

	//dfs, without recursion!
	private boolean isSafeMode(List<Position> inBoxes) {
		
		//Kanske flytta upp alla i hörnet, se om vi har testat kombon förut
		Stack<Integer> stack = new Stack<Integer>();
		
		boolean[] visited = new boolean[inBoxes.size()];
		Arrays.fill(visited, false);
		
		int found = 0;
		
		stack.add(0);
		visited[0] = true;
		
		while(!stack.empty()){
			found ++;
			
			Integer popId = stack.pop();
			
			Position popPos = inBoxes.get(popId);
			
			//find all adjacent nodes and add them to the stack if not visited
			for (int i = 0; i < inBoxes.size(); i++) {
				Position pos = inBoxes.get(i);
				if(visited[i] == false 
						&& ((pos.x == popPos.x -1 && pos.y == popPos.y)
						|| (pos.x == popPos.x +1 && pos.y == popPos.y)
						|| (pos.x == popPos.x && pos.y == popPos.y  +1)
						|| (pos.x == popPos.x && pos.y == popPos.y -1 ))){
					stack.add(i);
					visited[i] = true;
				}
			}
		}
		return found == inBoxes.size();
	}

	private void eat(String str) {
		st = new StringTokenizer(str);
	}
	
	String next() throws IOException {
		while (!st.hasMoreTokens()) {
			String line = in.readLine();
			if (line == null) {
				return null;
			}
			eat(line);
		}
		return st.nextToken();
	}
	
	int nextInt() throws IOException {
		return Integer.parseInt(next());
	}
	
	long nextLong() throws IOException {
		return Long.parseLong(next());
	}
	
	double nextDouble() throws IOException {
		return Double.parseDouble(next());
	}

	public static void main(String[] args) throws IOException {
		new Sokoban();
	}
	
	class Node implements Comparable<Node>{
		List<Position> positions;
		int steps = 0;
		long creationId = 0;
		
		public Node(long id){
			creationId = id;
		}
	
		@Override
		public String toString() {
			return "Node [positions=" + positions + ", steps=" + steps + "]";
		}
	
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof Node)){
				return false;
			}
			
			Node other = (Node) obj;
			
			boolean found = false;
			for (Position pos : positions) {
				found = false;
				
				for (Position pos2 : other.positions) {
					if(pos.equals(pos2)){
						found = true;
						break;
					}
				}
				if(!found){
					return false;
				}
			}
			return true;
		}
	
		@Override
		public int compareTo(Node o) {
			if(this.equals(o))
				return 0;
			else 
				return this.creationId > o.creationId ? 1 : -1;
		}
	}

	class Position implements Comparable<Position>{
		final int x;
		final int y;
		public Position(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		@Override
		public String toString() {
			return "Position [x=" + x + ", y=" + y + "]";
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
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
			Position other = (Position) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
		private Sokoban getOuterType() {
			return Sokoban.this;
		}
		@Override
		public int compareTo(Position o) {
			if(this.x > o.x)
				return 1;
			if(this.x < o.x)
				return -1;
			if(this.y > o.y)
				return 1;
			if(this.y < o.y)
				return -1;
			return 0;
		}
	}
}
