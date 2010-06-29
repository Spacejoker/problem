import java.util.ArrayList;
import java.util.List;

public class Circuits {

	class Node{
		int nodeId;
		List<NodeInfo> nodeInfo = new ArrayList<NodeInfo>();
		boolean visited;
		public Node(int nodeId, List<NodeInfo> nodeInfo) {
			super();
			this.nodeId = nodeId;
			this.nodeInfo = nodeInfo;
		}
		int price = 0;
	}
	
	class NodeInfo{
		int nodeId;
		int price;
		public NodeInfo(int nodeId, int price) {
			super();
			this.nodeId = nodeId;
			this.price = price;
		}
	}
	
	static String[][] testCase = {{"1 2", "2", ""},{"5 3","7", ""}};
	
	private int move(Node node, int currentScore, int bestScore){
		
		int score = currentScore;
		
		for (NodeInfo nodeInfo : node.nodeInfo) {
			Node childNode = nodes[nodeInfo.nodeId];
			int tmp = move(childNode, currentScore + nodeInfo.price, bestScore);
			if(tmp > score){
				score = tmp;
			}
		}

		if(score > bestScore){
			return score;
		}
		return bestScore;
	}
	
	private Node[] nodes;
	
	public int howLong(String[] connects, String[] costs){
		//Create a list with all nodes
		nodes = new Node[connects.length];
		
		for (int i = 0; i < connects.length; i++) {
			String[] conns = connects[i].split(" ");
			String[] csts = costs[i].split(" ");
			
			List<NodeInfo> info = new ArrayList<NodeInfo>();
			
			for (int j = 0; j < csts.length; j++) {
				if(!"".equals(conns[j])){
					info.add(new NodeInfo(Integer.valueOf(conns[j]), Integer.valueOf(csts[j])));
				}
			}
			nodes[i] = new Node(i, info);
		}
		
		int best = 0;
		
		//start at all nodes
		for (int i = 0; i < nodes.length; i++) {
			best = move(nodes[i], 0,best);
		}
		
		return best;	
	}
	
	public static void main(String[] args){
		System.out.println(new Circuits_malz().howLong(testCase[0], testCase[1]));
		
	}
}
