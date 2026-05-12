import java.util.*;

class Solution {

    public int minimumEffort(int[][] tasks) {

        // Sort by (minimum - actual) descending
        Arrays.sort(tasks, (a, b) ->
            (b[1] - b[0]) - (a[1] - a[0])
        );

        int initialEnergy = 0;
        int currentEnergy = 0;

        for (int[] task : tasks) {

            int actual = task[0];
            int minimum = task[1];

            // Need more energy to start task
            if (currentEnergy < minimum) {

                int extra = minimum - currentEnergy;

                initialEnergy += extra;
                currentEnergy += extra;
            }

            // Perform task
            currentEnergy -= actual;
        }

        return initialEnergy;
    }
}
