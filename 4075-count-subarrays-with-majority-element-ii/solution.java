public class Solution {
    public long countMajoritySubarrays(int[] nums, int target) {
        int n = nums.length;
        // Prefix sums range from -n to n.
        // Shift values by (n + 1) to make all Fenwick Tree indices positive.
        int offset = n + 1;
        int size = 2 * n + 3;
        int[] bit = new int[size];

        // Helper to update the Fenwick Tree
        update(bit, 0 + offset, 1);

        long validSubarraysCount = 0;
        int currentPrefixSum = 0;

        for (int num : nums) {
            // Transform element: +1 if it matches target, -1 otherwise
            if (num == target) {
                currentPrefixSum += 1;
            } else {
                currentPrefixSum -= 1;
            }

            // Query previous prefix sums strictly less than currentPrefixSum
            validSubarraysCount += query(bit, currentPrefixSum + offset - 1);

            // Add the current prefix sum to the Fenwick Tree
            update(bit, currentPrefixSum + offset, 1);
        }

        return validSubarraysCount;
    }

    private void update(int[] bit, int idx, int val) {
        while (idx < bit.length) {
            bit[idx] += val;
            idx += idx & (-idx);
        }
    }

    private int query(int[] bit, int idx) {
        int sum = 0;
        while (idx > 0) {
            sum += bit[idx];
            idx -= idx & (-idx);
        }
        return sum;
    }
}

