import java.util.*;

public class Solution {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        // Step 1: Extract and sort unique values from nums
        TreeSet<Integer> uniqueSet = new TreeSet<>();
        for (int num : nums) {
            uniqueSet.add(num);
        }
        
        int m = uniqueSet.size();
        int[] U = new int[m];
        int idx = 0;
        for (int val : uniqueSet) {
            U[idx++] = val;
        }
        
        // Step 2: Build the immediate greedy jump array R
        // R[i] stores the furthest unique value index we can jump to from U[i]
        int[] R = new int[m];
        for (int i = 0; i < m; i++) {
            int target = U[i] + maxDiff;
            // Find the largest index j such that U[j] <= target
            int pos = Arrays.binarySearch(U, target);
            if (pos < 0) {
                pos = -pos - 2; // Conversion logic for insertion point
            }
            R[i] = pos;
        }
        
        // Step 3: Build the Binary Lifting (Sparse) Table
        // LOG = 18 is sufficient since m <= 10^5 (2^17 = 131,072)
        int LOG = 18;
        int[][] dp = new int[m][LOG];
        
        for (int i = 0; i < m; i++) {
            dp[i][0] = R[i];
        }
        
        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < m; i++) {
                dp[i][k] = dp[dp[i][k - 1]][k - 1];
            }
        }
        
        // Step 4: Map values to their indices using a HashMap for O(1) lookups
        Map<Integer, Integer> valToIdx = new HashMap<>();
        for (int i = 0; i < m; i++) {
            valToIdx.put(U[i], i);
        }
        
        // Step 5: Answer each query
        int[] ans = new int[queries.length];
        for (int q = 0; q < queries.length; q++) {
            int u = queries[q][0];
            int v = queries[q][1];
            
            if (u == v) {
                ans[q] = 0;
                continue;
            }
            
            int A = nums[u];
            int B = nums[v];
            
            if (A == B) {
                ans[q] = 1; // Different nodes with identical values are directly connected
                continue;
            }
            
            // Ensure A is always the smaller value to standardize left-to-right jumps
            if (A > B) {
                int temp = A;
                A = B;
                B = temp;
            }
            
            int idxA = valToIdx.get(A);
            int idxB = valToIdx.get(B);
            
            int curr = idxA;
            int jumps = 0;
            
            // Use binary lifting to lift 'curr' right up to the boundary before idxB
            for (int k = LOG - 1; k >= 0; k--) {
                if (dp[curr][k] < idxB) {
                    curr = dp[curr][k];
                    jumps += (1 << k);
                }
            }
            
            // Final check: Can we bridge the remaining gap to idxB with one last step?
            if (dp[curr][0] >= idxB) {
                ans[q] = jumps + 1;
            } else {
                ans[q] = -1;
            }
        }
        
        return ans;
    }
}

