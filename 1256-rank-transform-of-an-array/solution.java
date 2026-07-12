import java.util.*;

class Solution {
    public int[] arrayRankTransform(int[] arr) {

        if (arr.length == 0) {
            return new int[0];
        }

        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        Map<Integer, Integer> rank = new HashMap<>();
        int currentRank = 1;

        for (int num : sorted) {
            if (!rank.containsKey(num)) {
                rank.put(num, currentRank++);
            }
        }

        int[] result = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            result[i] = rank.get(arr[i]);
        }

        return result;
    }
}
