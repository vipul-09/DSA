class Solution {

    public boolean canReach(int[] arr, int start) {

        boolean[] visited = new boolean[arr.length];

        return dfs(arr, start, visited);
    }

    private boolean dfs(int[] arr, int i, boolean[] visited) {

        // Out of bounds
        if (i < 0 || i >= arr.length) {
            return false;
        }

        // Already visited
        if (visited[i]) {
            return false;
        }

        // Found zero
        if (arr[i] == 0) {
            return true;
        }

        visited[i] = true;

        return dfs(arr, i + arr[i], visited) ||
               dfs(arr, i - arr[i], visited);
    }
}
