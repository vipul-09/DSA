class Solution {
    public int uniqueXorTriplets(int[] nums) {

        final int MAX = 2048;

        boolean[][] dp = new boolean[4][MAX];
        dp[0][0] = true;

        for (int val : nums) {

            for (int cnt = 2; cnt >= 0; cnt--) {
                for (int x = 0; x < MAX; x++) {
                    if (dp[cnt][x]) {
                        dp[cnt + 1][x ^ val] = true;
                    }
                }
            }
        }

        boolean[] seen = new boolean[MAX];

        for (int x = 0; x < MAX; x++) {
            if (dp[1][x] || dp[3][x]) {
                seen[x] = true;
            }
        }

        int ans = 0;

        for (boolean b : seen) {
            if (b) ans++;
        }

        return ans;
    }
}
