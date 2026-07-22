import java.util.*;

class Solution {
    // Structure to represent an enriched internal block of '1's
    static class Block {
        int start, end;
        int zl, zr;
        int length;
        int w; // Constant gain when completely unclipped by query borders

        Block(int start, int end, int zl, int zr) {
            this.start = start;
            this.end = end;
            this.zl = zl;
            this.zr = zr;
            this.length = end - start + 1;
            this.w = zr - zl + 1 - this.length;
        }
    }

    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        int totalOnes = 0;
        
        // 1. Parse initial '1' blocks and count total '1's
        List<int[]> initialBlocks = new ArrayList<>();
        boolean inBlock = false;
        int start = -1;
        
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') {
                totalOnes++;
                if (!inBlock) {
                    start = i;
                    inBlock = true;
                }
            } else {
                if (inBlock) {
                    initialBlocks.add(new int[]{start, i - 1});
                    inBlock = false;
                }
            }
        }
        if (inBlock) {
            initialBlocks.add(new int[]{start, n - 1});
        }

        // 2. Enrich valid internal blocks
        int m = initialBlocks.size();
        List<Block> validBlocks = new ArrayList<>();
        
        for (int x = 0; x < m; x++) {
            int[] curr = initialBlocks.get(x);
            // Ignore blocks that hit the outer edges of the string
            if (curr[0] == 0 || curr[1] == n - 1) {
                continue;
            }
            int zl = (x > 0) ? initialBlocks.get(x - 1)[1] + 1 : 0;
            int zr = (x < m - 1) ? initialBlocks.get(x + 1)[0] - 1 : n - 1;
            validBlocks.add(new Block(curr[0], curr[1], zl, zr));
        }

        int numP = validBlocks.size();
        int maxLog = (numP > 0) ? (int)(Math.log(numP) / Math.log(2)) + 1 : 0;
        int[][] st = new int[numP][maxLog];

        // 3. Build Sparse Table over the constant gains (w)
        if (numP > 0) {
            for (int i = 0; i < numP; i++) {
                st[i][0] = validBlocks.get(i).w;
            }
            for (int j = 1; j < maxLog; j++) {
                for (int i = 0; i + (1 << j) <= numP; i++) {
                    st[i][j] = Math.max(st[i][j - 1], st[i + (1 << (j - 1))][j - 1]);
                }
            }
        }

        // Arrays dedicated to fast binary search access
        int[] pStart = new int[numP];
        int[] pEnd = new int[numP];
        for (int i = 0; i < numP; i++) {
            pStart[i] = validBlocks.get(i).start;
            pEnd[i] = validBlocks.get(i).end;
        }

        List<Integer> answer = new ArrayList<>(queries.length);

        // 4. Evaluate each query
        for (int[] query : queries) {
            int l = query[0];
            int r = query[1];

            // Find first block completely inside: block.start >= l + 1
            int kFirst = lowerBound(pStart, l + 1);
            // Find last block completely inside: block.end <= r - 1
            int kLast = upperBound(pEnd, r - 1) - 1;

            if (kFirst > kLast) {
                answer.add(totalOnes);
            } else {
                int maxBenefit = 0;

                // Check boundary block kFirst
                Block p1 = validBlocks.get(kFirst);
                int b1 = Math.min(r, p1.zr) - Math.max(l, p1.zl) + 1 - p1.length;
                maxBenefit = Math.max(maxBenefit, b1);

                // Check boundary block kLast
                Block p2 = validBlocks.get(kLast);
                int b2 = Math.min(r, p2.zr) - Math.max(l, p2.zl) + 1 - p2.length;
                maxBenefit = Math.max(maxBenefit, b2);

                // Check fully internal blocks using the Sparse Table
                if (kFirst + 1 <= kLast - 1) {
                    int bMid = queryMax(st, kFirst + 1, kLast - 1);
                    maxBenefit = Math.max(maxBenefit, bMid);
                }

                answer.add(totalOnes + maxBenefit);
            }
        }

        return answer;
    }

    // Returns maximum value in range [L, R] via Sparse Table
    private int queryMax(int[][] st, int L, int R) {
        int j = (int)(Math.log(R - L + 1) / Math.log(2));
        return Math.max(st[L][j], st[R - (1 << j) + 1][j]);
    }

    // Binary search: lowest index where arr[i] >= target
    private int lowerBound(int[] arr, int target) {
        int low = 0, high = arr.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] >= target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    // Binary search: lowest index where arr[i] > target
    private int upperBound(int[] arr, int target) {
        int low = 0, high = arr.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] > target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }
}

