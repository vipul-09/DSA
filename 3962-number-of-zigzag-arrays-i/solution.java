class Solution {

    static final int MOD = 1_000_000_007;

    public int zigZagArrays(int n, int l, int r) {

        int m = r - l + 1;

        long[] inc = new long[m];
        long[] dec = new long[m];

        // length = 2
        for (int i = 0; i < m; i++) {

            inc[i] = i;

            dec[i] = m - 1 - i;
        }

        if (n == 2) {

            long ans = 0;

            for (int i = 0; i < m; i++) {
                ans = (ans + inc[i] + dec[i]) % MOD;
            }

            return (int) ans;
        }

        for (int len = 3; len <= n; len++) {

            long[] newInc = new long[m];
            long[] newDec = new long[m];

            long[] prefixInc = new long[m];
            long[] prefixDec = new long[m];

            prefixInc[0] = inc[0];
            prefixDec[0] = dec[0];

            for (int i = 1; i < m; i++) {

                prefixInc[i] = (prefixInc[i - 1] + inc[i]) % MOD;

                prefixDec[i] = (prefixDec[i - 1] + dec[i]) % MOD;
            }

            for (int i = 0; i < m; i++) {

                // previous comparison was decreasing
                // current must increase

                if (i > 0) {
                    newInc[i] = prefixDec[i - 1];
                }

                // previous comparison was increasing
                // current must decrease

                if (i < m - 1) {

                    newDec[i] =
                            (prefixInc[m - 1] - prefixInc[i] + MOD) % MOD;
                }
            }

            inc = newInc;
            dec = newDec;
        }

        long answer = 0;

        for (int i = 0; i < m; i++) {

            answer = (answer + inc[i]) % MOD;
            answer = (answer + dec[i]) % MOD;
        }

        return (int) answer;
    }
}
