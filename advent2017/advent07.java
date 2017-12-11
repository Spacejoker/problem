import com.sun.java.swing.plaf.windows.WindowsTreeUI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by js on 2017-12-07.
 */

class Node {
    String name;
    int weight;
    int totalWeight;
    List<Node> children = new LinkedList<>();
    boolean hasParent;
}

public class Advent7 {
    public static void main(String[] args) throws IOException {
        new Advent7().solve();
    }

    private void solve() throws IOException {
        Stream<String> stream = Files.lines(Paths.get("input.txt"));

        Map<String, Node> nodes = new HashMap<>();

        List<String> collect = stream
                .collect(Collectors.toList());

        for (String s : collect) {
            Node node = toNode(s);
            nodes.put(node.name, node);
        }

        for (String s : collect) {
            String[] split = s.split(" ");
            Node parent = nodes.get(split[0]);
            for (int i = 3 ; i < split.length ; i++) {
                String childName = split[i].replace(",","");
                Node child = nodes.get(childName);
                child.hasParent = true;
                parent.children.add(child);
            }
        }

        Node root = null;
        for (Node potentialRoot :  nodes.values()) {
            if (!potentialRoot.hasParent) {
                root = potentialRoot;
                break;
            }
        }

        findDiff(root);
    }

    private void findDiff(Node root) {
        // Don't consider leaf nodes.
        if (root.children.isEmpty()) {
            root.totalWeight = root.weight;
            return;
        }
        for(Node child : root.children) {
            findDiff(child);
        }
        Set<Integer> childWeights = root.children.stream().map(child -> child.totalWeight).collect(Collectors.toSet());
        if (childWeights.size() > 1) {
            Entry<Integer, Long> correctWeight =
                    root.children.stream()
                            .collect(Collectors.groupingBy(node -> node.totalWeight, Collectors.counting()))
                            .entrySet().stream()
                            .max(Comparator.comparingLong(a -> a.getValue())).get();
            for (Node child : root.children) {
                if (child.totalWeight != correctWeight.getKey()) {
                    System.out.println("Correct weight: " + (child.weight + (correctWeight.getKey() - child.totalWeight)));
                }
            }
        }
        root.totalWeight = root.weight + root.children.get(0).totalWeight * root.children.size();
    }

    private static Node toNode(String s) {
        String[] parts = s.split(" ");
        Node node = new Node();
        node.name = parts[0];
        node.weight = Integer.parseInt(parts[1].replaceAll("[()]", ""));
        return node;
    }
}
