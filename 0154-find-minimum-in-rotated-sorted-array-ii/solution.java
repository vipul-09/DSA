class Solution {

    public int findMin(int[] nums) {

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {

            int mid = left + (right - left) / 2;

            // Minimum in right half
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            }

            // Minimum in left half including mid
            else if (nums[mid] < nums[right]) {
                right = mid;
            }

            // Cannot determine due to duplicates
            else {
                right--;
            }
        }

        return nums[left];
    }
}
