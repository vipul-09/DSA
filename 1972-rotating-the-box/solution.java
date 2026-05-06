class Solution {
    public char[][] rotateTheBox(char[][] box) {
        int m = box.length;
        int n = box[0].length;

        // Step 1: Apply gravity row by row
        for (int i = 0; i < m; i++) {
            int empty = n - 1; // position to drop stone

            for (int j = n - 1; j >= 0; j--) {
                if (box[i][j] == '*') {
                    empty = j - 1; // reset after obstacle
                } 
                else if (box[i][j] == '#') {
                    // move stone to 'empty'
                    char temp = box[i][empty];
                    box[i][empty] = '#';
                    box[i][j] = temp;
                    empty--;
                }
            }
        }

        // Step 2: Rotate matrix
        char[][] rotated = new char[n][m];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                rotated[j][m - 1 - i] = box[i][j];
            }
        }

        return rotated;
    }
}
