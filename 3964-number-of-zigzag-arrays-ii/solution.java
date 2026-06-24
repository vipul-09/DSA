class Solution {

    static final int MOD = 1_000_000_007;

    public int zigZagArrays(int n, int l, int r) {

        int m = r - l + 1;

        int states = m * 2;

        if (n == 1) {
            return m;
        }

        long[][] trans = new long[states][states];

        for (int last = 0; last < m; last++) {

            // previous comparison was increasing
            int fromInc = last;

            for (int next = 0; next < last; next++) {
                int toDec = m + next;
                trans[toDec][fromInc] = 1;
            }

            // previous comparison was decreasing
            int fromDec = m + last;

            for (int next = last + 1; next < m; next++) {
                int toInc = next;
                trans[toInc][fromDec] = 1;
            }
        }

        long[] base = new long[states];

        for (int a = 0; a < m; a++) {

            int incState = a;

            for (int b = 0; b < a; b++) {
                base[incState]++;
            }

            int decState = m + a;

            for (int b = a + 1; b < m; b++) {
                base[decState]++;
            }
        }

        long[][] mat = power(trans, n - 2);

        long answer = 0;

        for (int i = 0; i < states; i++) {

            long ways = 0;

            for (int j = 0; j < states; j++) {
                ways = (ways + mat[i][j] * base[j]) % MOD;
            }

            answer = (answer + ways) % MOD;
        }

        return (int) answer;
    }

    private long[][] power(long[][] mat, long exp) {

        int size = mat.length;

        long[][] result = new long[size][size];

        for (int i = 0; i < size; i++) {
            result[i][i] = 1;
        }

        while (exp > 0) {

            if ((exp & 1) == 1) {
                result = multiply(result, mat);
            }

            mat = multiply(mat, mat);

            exp >>= 1;
        }

        return result;
    }

    private long[][] multiply(long[][] a, long[][] b) {

        int size = a.length;

        long[][] c = new long[size][size];

        for (int i = 0; i < size; i++) {

            for (int k = 0; k < size; k++) {

                if (a[i][k] == 0) {
                    continue;
                }

                for (int j = 0; j < size; j++) {

                    if (b[k][j] == 0) {
                        continue;
                    }

                    c[i][j] =
                            (c[i][j] + a[i][k] * b[k][j]) % MOD;
                }
            }
        }

        return c;
    }
}
