class Solution {

    public int maxActiveSectionsAfterTrade(String s) {

        int ones = 0;
        for (char c : s.toCharArray()) {
            if (c == '1') ones++;
        }

        String t = "1" + s + "1";
        int m = t.length();

        int ans = ones;

        int i = 1;

        while (i < m - 1) {

            if (t.charAt(i) == '1') {

                int l = i;

                while (i < m - 1 && t.charAt(i) == '1') {
                    i++;
                }

                int r = i - 1;

                if (l > 0 && r + 1 < m &&
                    t.charAt(l - 1) == '0' &&
                    t.charAt(r + 1) == '0') {

                    int removed = r - l + 1;

                    int left = l - 1;
                    while (left >= 0 && t.charAt(left) == '0') {
                        left--;
                    }
                    int leftZeros = l - left - 1;

                    int right = r + 1;
                    while (right < m && t.charAt(right) == '0') {
                        right++;
                    }
                    int rightZeros = right - r - 1;

                    int mergedZeros = leftZeros + removed + rightZeros;

                    ans = Math.max(ans, ones - removed + mergedZeros);
                }

            } else {
                i++;
            }
        }

        return ans;
    }
}
