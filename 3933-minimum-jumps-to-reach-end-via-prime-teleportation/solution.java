import java.util.*;

class Solution {

    public int minJumps(int[] nums) {
        int n = nums.length;

        // prime -> indices divisible by prime
        Map<Integer, List<Integer>> map = new HashMap<>();

        // Build map
        for (int i = 0; i < n; i++) {
            List<Integer> factors = getPrimeFactors(nums[i]);

            for (int p : factors) {
                map.computeIfAbsent(p, k -> new ArrayList<>()).add(i);
            }
        }

        Queue<Integer> q = new LinkedList<>();
        boolean[] visited = new boolean[n];

        q.offer(0);
        visited[0] = true;

        int steps = 0;

        while (!q.isEmpty()) {

            int size = q.size();

            for (int s = 0; s < size; s++) {

                int i = q.poll();

                if (i == n - 1) return steps;

                // adjacent left
                if (i - 1 >= 0 && !visited[i - 1]) {
                    visited[i - 1] = true;
                    q.offer(i - 1);
                }

                // adjacent right
                if (i + 1 < n && !visited[i + 1]) {
                    visited[i + 1] = true;
                    q.offer(i + 1);
                }

                int val = nums[i];

                // teleport only if val is prime
                if (isPrime(val)) {

                    if (map.containsKey(val)) {

                        for (int next : map.get(val)) {

                            if (!visited[next]) {
                                visited[next] = true;
                                q.offer(next);
                            }
                        }

                        // VERY IMPORTANT
                        map.remove(val);
                    }
                }
            }

            steps++;
        }

        return -1;
    }

    // Check prime
    private boolean isPrime(int x) {
        if (x < 2) return false;

        for (int i = 2; i * i <= x; i++) {
            if (x % i == 0) return false;
        }

        return true;
    }

    // Prime factorization
    private List<Integer> getPrimeFactors(int x) {

        List<Integer> factors = new ArrayList<>();

        for (int p = 2; p * p <= x; p++) {

            if (x % p == 0) {

                factors.add(p);

                while (x % p == 0) {
                    x /= p;
                }
            }
        }

        if (x > 1) factors.add(x);

        return factors;
    }
}
