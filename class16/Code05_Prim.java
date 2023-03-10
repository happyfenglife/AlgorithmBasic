package class16;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Code05_Prim {
    public static Set<Edge> primMST(Graph graph) {
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        HashSet<Node> nodeSet = new HashSet<>();

        Set<Edge> result = new HashSet<>();
        for (Node node : graph.nodes.values()) {
            if (!nodeSet.contains(node)) {
                nodeSet.add(node);
                priorityQueue.addAll(node.edges);

                while (!priorityQueue.isEmpty()) {
                    Edge edge = priorityQueue.poll();
                    Node toNode = edge.to;
                    if (!nodeSet.contains(toNode)) {
                        nodeSet.add(toNode);
                        result.add(edge);
                        priorityQueue.addAll(toNode.edges);
                    }
                }
            }
        }

        return result;
    }

    // 返回值是最小连通图的路径之和
    public static int prim(int[][] graph) {
        int size = graph.length;
        int[] distances = new int[size];
        boolean[] visit = new boolean[size];
        visit[0] = true;
        System.arraycopy(graph[0], 0, distances, 0, size);

        int sum = 0;
        for (int i = 1; i < size; i++) {
            int minPath = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < size; j++) {
                if (!visit[j] && distances[j] < minPath) {
                    minPath = distances[j];
                    minIndex = j;
                }
            }

            if (minIndex == -1)
                return sum;

            visit[minIndex] = true;
            sum += minPath;

            for (int j = 0; j < size; j++) {
                if (!visit[j]
                        && distances[j] > graph[minIndex][j]) {
                    distances[j] = graph[minIndex][j];
                }
            }
        }

        return sum;
    }
}
