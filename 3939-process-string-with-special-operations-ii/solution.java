class Solution {

    public char processStr(String s, long k) {

        int n = s.length();

        long[] len = new long[n + 1];

        for (int i = 0; i < n; i++) {

            char ch = s.charAt(i);

            long cur = len[i];

            if (ch >= 'a' && ch <= 'z') {
                len[i + 1] = cur + 1;
            }
            else if (ch == '*') {
                len[i + 1] = Math.max(0, cur - 1);
            }
            else if (ch == '#') {
                len[i + 1] = Math.min((long)1e15, cur * 2);
            }
            else { // %
                len[i + 1] = cur;
            }
        }

        if (k >= len[n]) {
            return '.';
        }

        for (int i = n - 1; i >= 0; i--) {

            char ch = s.charAt(i);

            long before = len[i];
            long after = len[i + 1];

            if (ch >= 'a' && ch <= 'z') {

                if (k == before) {
                    return ch;
                }
            }
            else if (ch == '*') {

                // k unchanged
            }
            else if (ch == '#') {

                if (before > 0) {
                    k %= before;
                }
            }
            else { // %

                if (before > 0) {
                    k = before - 1 - k;
                }
            }
        }

        return '.';
    }
}
