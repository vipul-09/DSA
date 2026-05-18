import java.util.*;

class Solution {

    public int minJumps(int[] arr) {

        int n = arr.length;

        // Edge case
        if (n == 1) {
            return 0;
        }

        // value -> indices
        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            map.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);
        }

        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n];

        queue.offer(0);
        visited[0] = true;

        int steps = 0;

        while (!queue.isEmpty()) {

            int size = queue.size();

            for (int s = 0; s < size; s++) {

                int i = queue.poll();

                // reached end
                if (i == n - 1) {
                    return steps;
                }

                // i - 1
                if (i - 1 >= 0 && !visited[i - 1]) {
                    visited[i - 1] = true;
                    queue.offer(i - 1);
                }

                // i + 1
                if (i + 1 < n && !visited[i + 1]) {
                    visited[i + 1] = true;
                    queue.offer(i + 1);
                }

                // same value jumps
                if (map.containsKey(arr[i])) {

                    for (int next : map.get(arr[i])) {

                        if (!visited[next]) {
                            visited[next] = true;
                            queue.offer(next);
                        }
                    }

                    // IMPORTANT optimization
                    map.remove(arr[i]);
                }
            }

            steps++;
        }

        return -1;
    }
}
