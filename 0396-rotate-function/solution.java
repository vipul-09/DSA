import java.util.*;

class Solution {
    public static int maxRotateFunction(int[] nums) {
        int n = nums.length;
        int sum = 0;
        int f = 0;

        // Compute initial sum and F(0)
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            f += i * nums[i];
        }

        int max = f;

        // Compute remaining rotations using formula
        for (int k = 1; k < n; k++) {
            f = f + sum - n * nums[n - k];
            max = Math.max(max, f);
        }

        return max;
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the length of array:");
        int n = sc.nextInt();

        int[] nums = new int[n];
        for(int i = 0; i < n; i++){
            System.out.println("Enter the element:");
            nums[i] = sc.nextInt();
        }

        System.out.println(maxRotateFunction(nums));
    }
}
