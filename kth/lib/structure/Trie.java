/******************* TRIE STRUCTURE *********************/

class Node{
        char c;
        boolean isword;
        Map<Integer, Node> children = new HashMap<Integer, Node>();
        public Node() {
           
        }
        public Node(char c, boolean isword) {
            super();
            this.c = c;
            this.isword = isword;
        }
       
        void build(String w, int pos){
            if(pos < w.length()){
                Node node = children.get(w.charAt(pos)+0);
                if(node == null){
                    node = new Node(w.charAt(pos), pos == w.length()-1);
                    children.put(0+w.charAt(pos), node);
                }
                if(pos==w.length()-1){
                    node.isword = true;
                }
                node.build(w, pos+1);
            }
        }
        boolean contains(String w, int pos){
            Node node = children.get(w.charAt(pos)+0);
            if(node != null){
                if(pos == w.length()-1){
                    return node.isword;
                }
                return node.contains(w, pos+1);
            }
            return false;
        }
        @Override
        public String toString() {
            return "Node [c=" + c + ", isword=" + isword + ", children=" + children + "]";
        }
    }
