import java.util.*;

class Solution {

    static final long MOD = 1_000_000_007L;

    List<Integer>[] tree;
    int[][] up;
    int[] depth;
    int LOG;
    long[] pow2;

    public int[] assignEdgeWeights(int[][] edges, int[][] queries) {

        int n = edges.length + 1;

        LOG = 17;
        while ((1 << LOG) <= n) LOG++;

        tree = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            int u = e[0];
            int v = e[1];

            tree[u].add(v);
            tree[v].add(u);
        }

        up = new int[n + 1][LOG];
        depth = new int[n + 1];

        dfs(1, 1);

        for (int j = 1; j < LOG; j++) {
            for (int i = 1; i <= n; i++) {
                up[i][j] = up[up[i][j - 1]][j - 1];
            }
        }

        pow2 = new long[n + 1];
        pow2[0] = 1;

        for (int i = 1; i <= n; i++) {
            pow2[i] = (pow2[i - 1] * 2) % MOD;
        }

        int m = queries.length;
        int[] ans = new int[m];

        for (int i = 0; i < m; i++) {

            int u = queries[i][0];
            int v = queries[i][1];

            int lca = lca(u, v);

            int len = depth[u] + depth[v] - 2 * depth[lca];

            if (len == 0) {
                ans[i] = 0;
            } else {
                ans[i] = (int) pow2[len - 1];
            }
        }

        return ans;
    }

    private void dfs(int node, int parent) {

        up[node][0] = parent;

        for (int nei : tree[node]) {

            if (nei == parent) continue;

            depth[nei] = depth[node] + 1;

            dfs(nei, node);
        }
    }

    private int lca(int a, int b) {

        if (depth[a] < depth[b]) {
            int temp = a;
            a = b;
            b = temp;
        }

        int diff = depth[a] - depth[b];

        for (int j = LOG - 1; j >= 0; j--) {
            if (((diff >> j) & 1) == 1) {
                a = up[a][j];
            }
        }

        if (a == b) return a;

        for (int j = LOG - 1; j >= 0; j--) {

            if (up[a][j] != up[b][j]) {
                a = up[a][j];
                b = up[b][j];
            }
        }

        return up[a][0];
    }
}
