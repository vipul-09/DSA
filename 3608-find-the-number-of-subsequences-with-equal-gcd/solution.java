import java.util.*;

public class Solution {
    private static final int MOD = 1_000_000_007;
    private static final int MAX_VAL = 200;

    public int subsequencePairCount(int[] nums) {
        // dp[g1][g2] stores the number of ways to get GCD g1 for seq1 and g2 for seq2
        // State 0 represents an empty subsequence
        int[][] dp = new int[MAX_VAL + 1][MAX_VAL + 1];
        dp[0][0] = 1;

        for (int x : nums) {
            int[][] nextDp = new int[MAX_VAL + 1][MAX_VAL + 1];
            
            for (int g1 = 0; g1 <= MAX_VAL; g1++) {
                for (int g2 = 0; g2 <= MAX_VAL; g2++) {
                    if (dp[g1][g2] == 0) continue;

                    long count = dp[g1][g2];

                    // Option 1: Do not include x in either subsequence
                    nextDp[g1][g2] = (int) ((nextDp[g1][g2] + count) % MOD);

                    // Option 2: Include x in seq1
                    int ng1 = gcd(g1, x);
                    nextDp[ng1][g2] = (int) ((nextDp[ng1][g2] + count) % MOD);

                    // Option 3: Include x in seq2
                    int ng2 = gcd(g2, x);
                    nextDp[g1][ng2] = (int) ((nextDp[g1][ng2] + count) % MOD);
                }
            }
            dp = nextDp;
        }

        // Gather all valid pairs where both subsequences are non-empty (g > 0) and g1 == g2
        long totalPairs = 0;
        for (int g = 1; g <= MAX_VAL; g++) {
            totalPairs = (totalPairs + dp[g][g]) % MOD;
        }

        return (int) totalPairs;
    }

    // Helper method to compute GCD
    private int gcd(int a, int b) {
        if (a == 0) return b;
        if (b == 0) return a;
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}

