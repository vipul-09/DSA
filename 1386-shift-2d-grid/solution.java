import java.util.*;

class Solution {
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {

        int m = grid.length;
        int n = grid[0].length;

        while (k-- > 0) {

            int[][] next = new int[m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {

                    int ni = i;
                    int nj = j + 1;

                    if (nj == n) {
                        nj = 0;
                        ni++;
                    }

                    if (ni == m) {
                        ni = 0;
                    }

                    next[ni][nj] = grid[i][j];
                }
            }

            grid = next;
        }

        List<List<Integer>> ans = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                row.add(grid[i][j]);
            }
            ans.add(row);
        }

        return ans;
    }
}
