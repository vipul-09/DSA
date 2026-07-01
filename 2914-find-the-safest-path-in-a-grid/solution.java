import java.util.*;

class Solution {

    public int maximumSafenessFactor(List<List<Integer>> grid) {

        int n = grid.size();

        int[][] dist = new int[n][n];

        for (int[] row : dist) {
            Arrays.fill(row, -1);
        }

        Queue<int[]> queue = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid.get(i).get(j) == 1) {
                    dist[i][j] = 0;
                    queue.offer(new int[]{i, j});
                }
            }
        }

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        // Multi-source BFS
        while (!queue.isEmpty()) {

            int[] cur = queue.poll();

            int r = cur[0];
            int c = cur[1];

            for (int k = 0; k < 4; k++) {

                int nr = r + dr[k];
                int nc = c + dc[k];

                if (nr < 0 || nr >= n || nc < 0 || nc >= n) {
                    continue;
                }

                if (dist[nr][nc] != -1) {
                    continue;
                }

                dist[nr][nc] = dist[r][c] + 1;
                queue.offer(new int[]{nr, nc});
            }
        }

        int low = 0;
        int high = 2 * n;
        int answer = 0;

        while (low <= high) {

            int mid = low + (high - low) / 2;

            if (canReach(dist, mid)) {
                answer = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return answer;
    }

    private boolean canReach(int[][] dist, int limit) {

        int n = dist.length;

        if (dist[0][0] < limit || dist[n - 1][n - 1] < limit) {
            return false;
        }

        Queue<int[]> queue = new LinkedList<>();

        boolean[][] visited = new boolean[n][n];

        queue.offer(new int[]{0, 0});
        visited[0][0] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!queue.isEmpty()) {

            int[] cur = queue.poll();

            int r = cur[0];
            int c = cur[1];

            if (r == n - 1 && c == n - 1) {
                return true;
            }

            for (int k = 0; k < 4; k++) {

                int nr = r + dr[k];
                int nc = c + dc[k];

                if (nr < 0 || nr >= n || nc < 0 || nc >= n) {
                    continue;
                }

                if (visited[nr][nc]) {
                    continue;
                }

                if (dist[nr][nc] < limit) {
                    continue;
                }

                visited[nr][nc] = true;
                queue.offer(new int[]{nr, nc});
            }
        }

        return false;
    }
}
