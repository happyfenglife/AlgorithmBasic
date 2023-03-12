package class16;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

public class Code04_KruskalNowCoder {
    public static final int MAXM = 100001;
    public static int[][] edges = new int[MAXM][3];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            int n = (int) in.nval;
            in.nextToken();
            int m = (int) in.nval;

            for (int i = 0; i < m; i++) {
                in.nextToken();
                edges[i][0] = (int) in.nval;
                in.nextToken();
                edges[i][1] = (int) in.nval;
                in.nextToken();
                edges[i][2] = (int) in.nval;
            }

            Arrays.sort(edges, Comparator.comparingInt(a -> a[2]));
            build(n);
            int ans = 0;
            for (int[] edge : edges) {
                if (union(edge[0], edge[1])) {
                    ans += edge[2];
                }
            }

            out.println(ans);
            out.flush();
        }
    }

    public static final int MAXN = 10001;

    public static int[] father = new int[MAXN];
    public static int[] size = new int[MAXN];
    public static int[] help = new int[MAXN];

    public static void build(int n) {
        for (int i = 1; i <= n; i++) {
            father[i] = i;
            size[i] = 1;
        }
    }

    private static int find(int i) {
        int size = 0;
        while (i != father[i]) {
            help[size++] = i;
            i = father[i];
        }
        while (size > 0) {
            father[help[--size]] = i;
        }
        return i;
    }

    public static boolean union(int i, int j) {
        int fi = find(i);
        int fj = find(j);

        if (fi != fj) {
            if (size[fi] >= size[fj]) {
                father[fj] = fi;
                size[fi] += size[fj];
            } else {
                father[fi] = fj;
                size[fj] += size[fi];
            }
            return true;
        }
        return false;
    }
}
