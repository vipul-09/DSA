#include <algorithm>

using namespace std;

class Solution {
public:
    int maxDepth(TreeNode* root) {
        // Base case: an empty tree has a depth of 0
        if (root == nullptr) {
            return 0;
        }
        
        // Recursively find the depth of left and right subtrees
        int left_depth = maxDepth(root->left);
        int right_depth = maxDepth(root->right);
        
        // The maximum depth is the larger of the two subtrees plus 1 for the root
        return max(left_depth, right_depth) + 1;
    }
};

