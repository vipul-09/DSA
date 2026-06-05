import java.util.*;

class Solution {

    static class Pair {
        long count;
        long waviness;

        Pair(long count, long waviness) {
            this.count = count;
            this.waviness = waviness;
        }
    }

    private String digits;
    private Pair[][][][][] memo;

    public long totalWaviness(long num1, long num2) {
        return solve(num2) - solve(num1 - 1);
    }

    private long solve(long n) {
        if (n < 0) return 0;

        digits = String.valueOf(n);

        int len = digits.length();

        memo = new Pair[len + 1][11][11][2][2];

        Pair res = dfs(0, 10, 10, 1, 0);

        return res.waviness;
    }

    private Pair dfs(int pos,
                     int prev1,
                     int prev2,
                     int tight,
                     int started) {

        if (pos == digits.length()) {
            return new Pair(1, 0);
        }

        if (memo[pos][prev1][prev2][tight][started] != null) {
            return memo[pos][prev1][prev2][tight][started];
        }

        int limit = tight == 1 ? digits.charAt(pos) - '0' : 9;

        long totalCount = 0;
        long totalWaviness = 0;

        for (int d = 0; d <= limit; d++) {

            int nextTight =
                    (tight == 1 && d == limit) ? 1 : 0;

            if (started == 0 && d == 0) {

                Pair child =
                        dfs(pos + 1,
                            10,
                            10,
                            nextTight,
                            0);

                totalCount += child.count;
                totalWaviness += child.waviness;
            }
            else {

                int newStarted = 1;

                Pair child;

                if (started == 0) {

                    child =
                            dfs(pos + 1,
                                d,
                                10,
                                nextTight,
                                newStarted);

                    totalCount += child.count;
                    totalWaviness += child.waviness;
                }
                else {

                    long add = 0;

                    if (prev2 != 10) {

                        boolean peak =
                                prev1 > prev2 &&
                                prev1 > d;

                        boolean valley =
                                prev1 < prev2 &&
                                prev1 < d;

                        if (peak || valley) {
                            add = 1;
                        }
                    }

                    child =
                            dfs(pos + 1,
                                d,
                                prev1,
                                nextTight,
                                1);

                    totalCount += child.count;

                    totalWaviness +=
                            child.waviness +
                            add * child.count;
                }
            }
        }

        return memo[pos][prev1][prev2][tight][started] =
                new Pair(totalCount, totalWaviness);
    }
}
