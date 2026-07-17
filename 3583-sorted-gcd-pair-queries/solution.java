import java.util.*;

class Solution {

    public int[] gcdValues(int[] nums, long[] queries) {

        int max = 0;
        for (int x : nums) {
            max = Math.max(max, x);
        }

        int[] freq = new int[max + 1];

        for (int x : nums) {
            freq[x]++;
        }

        long[] pairs = new long[max + 1];

        // Count pairs divisible by d
        for (int d = 1; d <= max; d++) {

            long cnt = 0;

            for (int m = d; m <= max; m += d) {
                cnt += freq[m];
            }

            pairs[d] = cnt * (cnt - 1) / 2;
        }

        // NEW CHANGE: Inclusion-Exclusion
        for (int d = max; d >= 1; d--) {
            for (int m = d + d; m <= max; m += d) {
                pairs[d] -= pairs[m];
            }
        }

        long[] prefix = new long[max + 1];

        for (int i = 1; i <= max; i++) {
            prefix[i] = prefix[i - 1] + pairs[i];
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {

            long target = queries[i] + 1;

            int lo = 1;
            int hi = max;

            while (lo < hi) {

                int mid = lo + (hi - lo) / 2;

                if (prefix[mid] >= target)
                    hi = mid;
                else
                    lo = mid + 1;
            }

            ans[i] = lo;
        }

        return ans;
    }
}
