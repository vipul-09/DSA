#include <vector>
#include <cmath>
#include <queue>
#include <algorithm>

using namespace std;

class Solution {
private:
    // Sparse Tables for O(1) Range Maximum and Minimum Queries
    vector<vector<int>> st_max;
    vector<vector<int>> st_min;
    vector<int> lg;

    // Precompute logs and populate the Sparse Tables
    void initSparseTable(const vector<int>& nums, int n) {
        int max_log = log2(n) + 1;
        st_max.assign(n, vector<int>(max_log, 0));
        st_min.assign(n, vector<int>(max_log, 0));
        lg.assign(n + 1, 0);

        for (int i = 2; i <= n; ++i) {
            lg[i] = lg[i / 2] + 1;
        }

        for (int i = 0; i < n; ++i) {
            st_max[i][0] = nums[i];
            st_min[i][0] = nums[i];
        }

        for (int j = 1; j < max_log; ++j) {
            for (int i = 0; i + (1 << j) <= n; ++i) {
                st_max[i][j] = max(st_max[i][j - 1], st_max[i + (1 << (j - 1))][j - 1]);
                st_min[i][j] = min(st_min[i][j - 1], st_min[i + (1 << (j - 1))][j - 1]);
            }
        }
    }

    // O(1) helper to compute max(nums[l..r]) - min(nums[l..r])
    long long getSubarrayValue(int l, int r) {
        int k = lg[r - l + 1];
        int mx = max(st_max[l][k], st_max[r - (1 << k) + 1][k]);
        int mn = min(st_min[l][k], st_min[r - (1 << k) + 1][k]);
        return (long long)mx - mn;
    }

    // Structure to represent a heap element
    struct SubarrayState {
        long long value;
        int l;
        int r;

        // Custom comparator for Max-Heap
        bool operator<(const SubarrayState& other) const {
            return value < other.value;
        }
    };

public:
    long long maxTotalValue(vector<int>& nums, int k) {
        int n = nums.size();
        initSparseTable(nums, n);

        // Max-Heap tracking current highest values
        priority_queue<SubarrayState> max_heap;

        // Step 1: Initialize heap with every subarray starting at l and ending at n-1
        for (int l = 0; l < n; ++l) {
            long long val = getSubarrayValue(l, n - 1);
            max_heap.push({val, l, n - 1});
        }

        long long total_value = 0;

        // Step 2: Extract top-k optimal subarray values sequentially
        for (int i = 0; i < k; ++i) {
            auto [val, l, r] = max_heap.top();
            max_heap.pop();

            total_value += val;

            // Step 3: Transition to the next best alternative (shrink window from right)
            if (r > l) {
                long long next_val = getSubarrayValue(l, r - 1);
                max_heap.push({next_val, l, r - 1});
            }
        }

        return total_value;
    }
};
