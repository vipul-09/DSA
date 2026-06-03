import java.util.*;

class Solution {

    public int earliestFinishTime(
            int[] landStartTime,
            int[] landDuration,
            int[] waterStartTime,
            int[] waterDuration) {

        long ans = Long.MAX_VALUE;

        // Land -> Water
        ans = Math.min(ans,
                solve(
                        landStartTime,
                        landDuration,
                        waterStartTime,
                        waterDuration));

        // Water -> Land
        ans = Math.min(ans,
                solve(
                        waterStartTime,
                        waterDuration,
                        landStartTime,
                        landDuration));

        return (int) ans;
    }

    private long solve(
            int[] firstStart,
            int[] firstDuration,
            int[] secondStart,
            int[] secondDuration) {

        int m = secondStart.length;

        int[][] rides = new int[m][2];

        for (int i = 0; i < m; i++) {
            rides[i][0] = secondStart[i];
            rides[i][1] = secondDuration[i];
        }

        Arrays.sort(rides, Comparator.comparingInt(a -> a[0]));

        int[] starts = new int[m];

        long[] prefixMinDuration = new long[m];
        long[] suffixMinStartPlusDuration = new long[m];

        for (int i = 0; i < m; i++) {
            starts[i] = rides[i][0];
        }

        prefixMinDuration[0] = rides[0][1];
        for (int i = 1; i < m; i++) {
            prefixMinDuration[i] =
                    Math.min(prefixMinDuration[i - 1], rides[i][1]);
        }

        suffixMinStartPlusDuration[m - 1] =
                (long) rides[m - 1][0] + rides[m - 1][1];

        for (int i = m - 2; i >= 0; i--) {
            suffixMinStartPlusDuration[i] =
                    Math.min(
                            suffixMinStartPlusDuration[i + 1],
                            (long) rides[i][0] + rides[i][1]);
        }

        long best = Long.MAX_VALUE;

        for (int i = 0; i < firstStart.length; i++) {

            long t = (long) firstStart[i] + firstDuration[i];

            int pos = upperBound(starts, (int) t);

            long cur = Long.MAX_VALUE;

            // start <= t
            if (pos > 0) {
                cur = Math.min(
                        cur,
                        t + prefixMinDuration[pos - 1]);
            }

            // start > t
            if (pos < m) {
                cur = Math.min(
                        cur,
                        suffixMinStartPlusDuration[pos]);
            }

            best = Math.min(best, cur);
        }

        return best;
    }

    private int upperBound(int[] arr, int target) {

        int l = 0;
        int r = arr.length;

        while (l < r) {

            int mid = l + (r - l) / 2;

            if (arr[mid] <= target) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }

        return l;
    }
}
