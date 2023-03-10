package class15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Code02_NumberOfIslands {
    public static int numIslands1(char[][] grid) {
        int ans = 0;
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[0].length; j++)
                if (grid[i][j] == '1') {
                    infect(grid, i, j);
                    ans++;
                }

        return ans;
    }

    private static void infect(char[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length
                || j < 0 || j >= grid[0].length
                || grid[i][j] != '1') {
            return;
        }

        grid[i][j] = '0';
        infect(grid, i + 1, j);
        infect(grid, i - 1, j);
        infect(grid, i, j + 1);
        infect(grid, i, j - 1);
    }

    public static int numIslands2(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        Dot[][] dots = new Dot[row][col];
        List<Dot> dotList = new ArrayList<>();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == '1') {
                    dots[i][j] = new Dot();
                    dotList.add(dots[i][j]);
                }
            }
        }

        UnionFind1<Dot> uf = new UnionFind1<>(dotList);
        for (int j = 1; j < col; j++) {
            if (board[0][j - 1] == '1'
                    && board[0][j] == '1') {
                uf.union(dots[0][j - 1], dots[0][j]);
            }
        }

        for (int i = 1; i < row; i++) {
            if (board[i - 1][0] == '1'
                    && board[i][0] == '1') {
                uf.union(dots[i - 1][0], dots[i][0]);
            }
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (board[i][j] == '1') {
                    if (board[i][j - 1] == '1') {
                        uf.union(dots[i][j - 1], dots[i][j]);
                    }
                    if (board[i - 1][j] == '1') {
                        uf.union(dots[i - 1][j], dots[i][j]);
                    }
                }
            }
        }

        return uf.sets();
    }

    public static class Dot {
    }

    public static class Node<V> {
        V value;

        public Node(V v) {
            value = v;
        }
    }

    public static class UnionFind1<V> {
        public HashMap<V, Node<V>> nodes;
        public HashMap<Node<V>, Node<V>> parents;
        public HashMap<Node<V>, Integer> sizeMap;

        public UnionFind1(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();

            for (V cur : values) {
                Node<V> node = new Node<>(cur);
                nodes.put(cur, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public Node<V> findFather(Node<V> cur) {
            Stack<Node<V>> path = new Stack<>();
            while (cur != parents.get(cur)) {
                path.push(cur);
                cur = parents.get(cur);
            }
            while (!path.isEmpty()) {
                parents.put(path.pop(), cur);
            }
            return cur;
        }

        public void union(V a, V b) {
            Node<V> aHead = findFather(nodes.get(a));
            Node<V> bHead = findFather(nodes.get(b));

            if (aHead != bHead) {
                Integer aSetSize = sizeMap.get(aHead);
                Integer bSetSize = sizeMap.get(bHead);

                Node<V> big = aSetSize >= bSetSize ? aHead : bHead;
                Node<V> small = big == aHead ? bHead : aHead;
                parents.put(small, big);
                sizeMap.put(big, aSetSize + bSetSize);
                sizeMap.remove(small);
            }
        }

        public int sets() {
            return sizeMap.size();
        }
    }

    public static int numIslands3(char[][] board) {
        int row = board.length;
        int col = board[0].length;

        UnionFind2 uf = new UnionFind2(board);

        for (int j = 1; j < col; j++) {
            if (board[0][j - 1] == '1'
                    && board[0][j] == '1') {
                uf.union(0, j - 1, 0, j);
            }
        }

        for (int i = 1; i < row; i++) {
            if (board[i - 1][0] == '1'
                    && board[i][0] == '1') {
                uf.union(i - 1, 0, i, 0);
            }
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (board[i][j] == '1') {
                    if (board[i][j - 1] == '1') {
                        uf.union(i, j - 1, i, j);
                    }
                    if (board[i - 1][j] == '1') {
                        uf.union(i - 1, j, i, j);
                    }
                }
            }
        }

        return uf.sets();
    }

    public static class UnionFind2 {
        private final int[] parent;
        private final int[] size;
        private final int[] help;
        private final int col;
        private int sets;

        public UnionFind2(char[][] board) {
            col = board[0].length;
            sets = 0;
            int row = board.length;
            int len = row * col;

            parent = new int[len];
            size = new int[len];
            help = new int[len];

            for (int r = 0; r < row; r++) {
                for (int c = 0; c < col; c++) {
                    if (board[r][c] == '1') {
                        int i = index(r, c);
                        parent[i] = i;
                        size[i] = i;
                        sets++;
                    }
                }
            }
        }

        private int index(int r, int c) {
            return r * col + c;
        }

        private int find(int i) {
            int hi = 0;
            while (i != parent[i]) {
                help[hi++] = i;
                i = parent[i];
            }

            for (hi--; hi >= 0; hi--)
                parent[help[hi]] = i;

            return i;
        }

        public void union(int r1, int c1, int r2, int c2) {
            int i1 = index(r1, c1);
            int i2 = index(r2, c2);
            int f1 = find(i1);
            int f2 = find(i2);

            if (f2 != f1) {
                if (size[f1] >= size[f2]) {
                    size[f1] += size[f2];
                    parent[f2] = f1;
                } else {
                    size[f2] += size[f1];
                    parent[f1] = f2;
                }
                sets--;
            }

        }

        public int sets() {
            return sets;
        }
    }

    // ????????????
    public static char[][] generateRandomMatrix(int row, int col) {
        char[][] board = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = Math.random() < 0.5 ? '1' : '0';
            }
        }
        return board;
    }

    // ????????????
    public static char[][] copy(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        char[][] ans = new char[row][col];
        for (int i = 0; i < row; i++) {
            System.arraycopy(board[i], 0, ans[i], 0, col);
        }
        return ans;
    }

    // ????????????
    public static void main(String[] args) {
        int row;
        int col;
        char[][] board1;
        char[][] board2;
        char[][] board3;
        long start;
        long end;

        row = 1000;
        col = 1000;
        board1 = generateRandomMatrix(row, col);
        board2 = copy(board1);
        board3 = copy(board1);

        System.out.println("????????????????????????(map??????)????????????(????????????)??????????????????????????????");
        System.out.println("????????????????????????????????? : " + row + " * " + col);

        start = System.currentTimeMillis();
        System.out.println("???????????????????????????: " + numIslands3(board1));
        end = System.currentTimeMillis();
        System.out.println("???????????????????????????: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("?????????(map??????)???????????????: " + numIslands1(board2));
        end = System.currentTimeMillis();
        System.out.println("?????????(map??????)???????????????: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("?????????(????????????)???????????????: " + numIslands2(board3));
        end = System.currentTimeMillis();
        System.out.println("?????????(????????????)???????????????: " + (end - start) + " ms");

        System.out.println();

        row = 10000;
        col = 10000;
        board1 = generateRandomMatrix(row, col);
        board3 = copy(board1);
        System.out.println("????????????????????????(????????????)??????????????????????????????");
        System.out.println("????????????????????????????????? : " + row + " * " + col);

        start = System.currentTimeMillis();
        System.out.println("???????????????????????????: " + numIslands3(board1));
        end = System.currentTimeMillis();
        System.out.println("???????????????????????????: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("?????????(????????????)???????????????: " + numIslands2(board3));
        end = System.currentTimeMillis();
        System.out.println("?????????(????????????)???????????????: " + (end - start) + " ms");
    }
}
