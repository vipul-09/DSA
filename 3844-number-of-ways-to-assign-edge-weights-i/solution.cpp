#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

class Solution {
public:
    int assignEdgeWeights(vector<vector<int>>& edges) {
        // A valid tree with (n - 1) edges has exactly n nodes
        int n = edges.size() + 1;
        
        // Step 1: Build the adjacency list graph representation
        vector<vector<int>> adj(n + 1);
        for (const auto& edge : edges) {
            adj[edge[0]].push_back(edge[1]);
            adj[edge[1]].push_back(edge[0]);
        }

        // Step 2: Use BFS to calculate the maximum depth of the tree
        queue<int> q;
        vector<bool> visited(n + 1, false);
        
        q.push(1);
        visited[1] = true;
        
        int max_depth = -1; // Root node 1 starts at level 0

        while (!q.empty()) {
            int level_size = q.size();
            max_depth++; // Increments depth level by level
            
            for (int i = 0; i < level_size; i++) {
                int curr = q.front();
                q.pop();
                
                for (int neighbor : adj[curr]) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        q.push(neighbor);
                    }
                }
            }
        }

        // Step 3: Compute (2^(max_depth - 1)) % (10^9 + 7)
        long long ans = 1;
        long long base = 2;
        int exponent = max_depth - 1;
        long long MOD = 1e9 + 7;

        while (exponent > 0) {
            if (exponent % 2 == 1) {
                ans = (ans * base) % MOD;
            }
            base = (base * base) % MOD;
            exponent /= 2;
        }

        return ans;
    }
};

