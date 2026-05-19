class Solution {

    public int getCommon(int[] nums1, int[] nums2) {

        int i = 0;
        int j = 0;

        while (i < nums1.length && j < nums2.length) {

            // Common element found
            if (nums1[i] == nums2[j]) {
                return nums1[i];
            }

            // Move smaller value pointer
            else if (nums1[i] < nums2[j]) {
                i++;
            }

            else {
                j++;
            }
        }

        return -1;
    }
}
