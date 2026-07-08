import java.util.*;

class Solution {

    static final int MOD = 1_000_000_007;

    static class Node {
        long value;
        int len;

        Node(long value, int len) {
            this.value = value;
            this.len = len;
        }
    }

    Node[] tree;
    long[] pow10;
    int[] prefixSum;
    char[] arr;
    int n;

    public int[] sumAndMultiply(String s, int[][] queries) {
        n = s.length();
        arr = s.toCharArray();

        pow10 = new long[n + 1];
        pow10[0] = 1;
        for (int i = 1; i <= n; i++) {
            pow10[i] = (pow10[i - 1] * 10) % MOD;
        }

        prefixSum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + (arr[i] - '0');
        }

        tree = new Node[4 * n];
        build(1, 0, n - 1);

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int l = queries[i][0];
            int r = queries[i][1];

            Node res = query(1, 0, n - 1, l, r);
            long sum = prefixSum[r + 1] - prefixSum[l];
            ans[i] = (int) ((res.value * sum) % MOD);
        }

        return ans;
    }

    private void build(int idx, int l, int r) {
        if (l == r) {
            int d = arr[l] - '0';
            if (d == 0) {
                tree[idx] = new Node(0, 0);
            } else {
                tree[idx] = new Node(d, 1);
            }
            return;
        }

        int mid = (l + r) / 2;
        build(idx * 2, l, mid);
        build(idx * 2 + 1, mid + 1, r);

        tree[idx] = merge(tree[idx * 2], tree[idx * 2 + 1]);
    }

    private Node query(int idx, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) {
            return tree[idx];
        }

        int mid = (l + r) / 2;

        if (qr <= mid) {
            return query(idx * 2, l, mid, ql, qr);
        }

        if (ql > mid) {
            return query(idx * 2 + 1, mid + 1, r, ql, qr);
        }

        Node left = query(idx * 2, l, mid, ql, qr);
        Node right = query(idx * 2 + 1, mid + 1, r, ql, qr);

        return merge(left, right);
    }

    private Node merge(Node left, Node right) {
        long value = (left.value * pow10[right.len] + right.value) % MOD;
        return new Node(value, left.len + right.len);
    }
}
