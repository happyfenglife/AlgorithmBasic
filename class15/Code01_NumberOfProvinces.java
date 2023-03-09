package class15;

public class Code01_NumberOfProvinces {
    public int findCircleNum(int[][] isConnected) {
        int M = isConnected.length;

        UnionFind uf = new UnionFind(M);
        for (int i = 0; i < M; i++)
            for (int j = i + 1; j < M; j++)
                if (isConnected[i][j] == 1)
                    uf.union(i, j);

        return uf.sets();
    }

    public static class UnionFind {
        private int[] father;
        private int[] size;
        private int[] help;
        private int sets;

        public UnionFind(int M) {
            father = new int[M];
            size = new int[M];
            help = new int[M];
            sets = M;

            for (int i = 0; i < M; i++) {
                father[i] = i;
                size[i] = 1;
            }
        }

        public int find(int i) {
            int hi = 0;
            while (i != father[i]) {
                help[hi++] = i;
                i = father[i];
            }
            for (hi--; hi >= 0; hi--)
                father[help[hi]] = i;
            return i;
        }

        public void union(int i, int j) {
            int fi = find(i);
            int fj = find(j);

            if (fi != fj) {
                if (size[fi] >= size[fj]) {
                    size[fi] += size[fj];
                    father[fj] = fi;
                } else {
                    size[fj] += size[fi];
                    father[fi] = fj;
                }
                sets--;
            }
        }

        public int sets() {
            return sets;
        }
    }
}
