package class16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Code03_TopologicalOrderDFS2 {
    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<>();
        }
    }

    public static class Record {
        public DirectedGraphNode node;
        public long nodes;

        public Record(DirectedGraphNode n, long o) {
            node = n;
            nodes = o;
        }
    }

    public static class MyComparator implements Comparator<Record> {

        @Override
        public int compare(Record o1, Record o2) {
            return Long.compare(o2.nodes, o1.nodes);
        }
    }

    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode, Record> order = new HashMap<>();
        for (DirectedGraphNode cur : graph) {
            f(cur, order);
        }
        ArrayList<Record> recordArr = new ArrayList<>(order.values());
        recordArr.sort(new MyComparator());

        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        for (Record r : recordArr) {
            ans.add(r.node);
        }
        return ans;
    }

    public static Record f(DirectedGraphNode cur, HashMap<DirectedGraphNode, Record> order) {
        if (order.containsKey(cur)) {
            return order.get(cur);
        }

        long nodes = 0;
        for (DirectedGraphNode next : cur.neighbors) {
            nodes += f(next, order).nodes;
        }

        Record ans = new Record(cur, nodes + 1);
        order.put(cur, ans);
        return ans;
    }
}
