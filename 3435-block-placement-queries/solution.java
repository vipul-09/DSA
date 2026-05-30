import java.util.*;

class Solution {

    class FenwickMax {
        int[] bit;
        int n;

        FenwickMax(int n) {
            this.n = n;
            bit = new int[n + 2];
        }

        void update(int idx, int val) {
            idx++;
            while (idx <= n + 1) {
                bit[idx] = Math.max(bit[idx], val);
                idx += idx & -idx;
            }
        }

        int query(int idx) {
            idx++;
            int res = 0;
            while (idx > 0) {
                res = Math.max(res, bit[idx]);
                idx -= idx & -idx;
            }
            return res;
        }
    }

    public List<Boolean> getResults(int[][] queries) {

        int MAX = 50000;

        TreeSet<Integer> obstacles = new TreeSet<>();
        obstacles.add(0);
        obstacles.add(MAX);

        // Build final obstacle configuration
        for (int[] q : queries) {
            if (q[0] == 1) {
                obstacles.add(q[1]);
            }
        }

        FenwickMax fenwick = new FenwickMax(MAX + 1);

        // Initialize gaps
        Integer prev = null;
        for (int pos : obstacles) {
            if (prev != null) {
                fenwick.update(pos, pos - prev);
            }
            prev = pos;
        }

        List<Boolean> ans = new ArrayList<>();

        // Process queries in reverse
        for (int i = queries.length - 1; i >= 0; i--) {

            int[] q = queries[i];

            if (q[0] == 1) {

                int x = q[1];

                Integer leftObj = obstacles.lower(x);
Integer rightObj = obstacles.higher(x);

if (leftObj == null || rightObj == null) {
    continue;
}

int left = leftObj;
int right = rightObj;

                obstacles.remove(x);

                // Merge the two gaps into one larger gap
                fenwick.update(right, right - left);

            } else {

                int x = q[1];
                int sz = q[2];

                Integer pre = obstacles.floor(x);

                int bestGapBefore = fenwick.query(pre);
                int tailGap = x - pre;

                ans.add(Math.max(bestGapBefore, tailGap) >= sz);
            }
        }

        Collections.reverse(ans);
        return ans;
    }
}
