package class16;

public class GraphGenerator {
    // matrix 所有的边
    // N*3 的矩阵
    // [weight, from节点上面的值，to节点上面的值]
    //
    // [ 5 , 0 , 7]
    // [ 3 , 0,  1]
    public static Graph createGraph(int[][] matrix) {
        Graph graph = new Graph();
        for (int[] tmp : matrix) {
            int weight = tmp[0];
            int from = tmp[1];
            int to = tmp[2];
            if (!graph.nodes.containsKey(from))
                graph.nodes.put(from, new Node(from));

            if (!graph.nodes.containsKey(to))
                graph.nodes.put(to, new Node(to));

            Node fromNode = graph.nodes.get(from);
            Node toNode = graph.nodes.get(to);
            Edge newEdge = new Edge(weight, fromNode, toNode);
            fromNode.nexts.add(toNode);
            fromNode.out++;
            toNode.in++;
            fromNode.edges.add(newEdge);
            graph.edges.add(newEdge);
        }
        return graph;
    }
}
