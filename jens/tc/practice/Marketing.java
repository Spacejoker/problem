import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Marketing {
	
	class Node{
		int id;
		List<Node> attachedNodes;
		boolean visited;
		boolean flickMe;
		
		public Node(int id) {
			super();
			this.id = id;
		}
		
		public void addNode(Node node){
			if(attachedNodes == null){
				attachedNodes = new ArrayList<Node>();
			}
			attachedNodes.add(node);
		}
	}
	
	public long howMany(String[] compete){
		
		//the length is the nr of nodes
		List<Node> nodes = new ArrayList<Node>();
		for (int i = 0; i < compete.length; i++) {
			nodes.add(new Node(i));
		}
		
		for (int i = 0; i < compete.length; i++) {
			if("".equals(compete[i])){
				continue;
			}
				
			String[] split = compete[i].split(" ");
			Node node = nodes.get(i);
			
			int length = split.length;
			for (int j = 0; j < length; j++) {
				node.addNode(nodes.get(Integer.valueOf(split[j])));
				nodes.get(Integer.valueOf(split[j])).addNode(node);
			}
		}
		//now do dfs on every untouched node, if it can be solved each island will add *2;
		
		int found = 0;
		
		for (int i = 0; i < compete.length; i++) {
			Node node = nodes.get(i);
			if(!node.visited){
				node.visited = true;
				found ++;
				if(!validChain(node)){
					return -1;
				}
			}
		}
		
		return (long) Math.pow(2, found);
	}
	
	private boolean validChain(Node node) {
		
		Stack<Node> s = new Stack<Node>();
		
		s.add(node);
		
		while(!s.isEmpty()){
			Node pop = s.pop();
			
			pop.visited = true;
			if(pop.attachedNodes != null){
				boolean foundWorkingMatch = false;
				for (Node child : pop.attachedNodes) {
					if(child.visited == true){
						if(child.flickMe == pop.flickMe){
//							return false;
						} else {
							foundWorkingMatch = true;
						}
					}  else {
						foundWorkingMatch = true;
						child.flickMe = !pop.flickMe;
						s.add(child);
					}
				}	
				if(!foundWorkingMatch){
					return false;
				}
			}	
		}
		
		return true;
	}

//	private static final String[] testCase = {"1 4","2","3","0",""};
	private static final String[] testCase = {"1","2","3","0","0 5","1"};
//	private static final String[] testCase = {"","","","","","","","","","", "","","","","","","","","","", "","","","","","","","","",""};
	public static void main(String[] args){
		System.out.println(new Marketing().howMany(testCase));
	}
	
}
