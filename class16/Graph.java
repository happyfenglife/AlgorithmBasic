package class16;

import java.util.HashMap;
import java.util.HashSet;

public class Graph {
    public HashMap<Integer, Node> nodes;
    public HashSet<Edge> edges;

    public Graph() {
        edges = new HashSet<>();
        nodes = new HashMap<>();
    }
}
