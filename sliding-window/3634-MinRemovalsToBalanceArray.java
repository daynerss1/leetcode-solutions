/*
 * LeetCode 3634: Minimum Removals to Balance Array
 *
 * Approach:
 * Sort the array first. After sorting, if we keep some elements and remove the
 * rest, the kept elements will form a contiguous subarray in sorted order.
 *
 * So the problem becomes:
 * find the longest sorted window [i, j] such that
 * nums[j] <= nums[i] * k
 *
 * If this condition holds, then the smallest and largest values in the kept
 * window satisfy the balance requirement, and therefore the whole window is
 * valid. Any elements outside this window would need to be removed.
 *
 * Sliding-window invariant:
 * - j expands the right end of the window. i is always the left end of the window.
 * - if the current window becomes invalid, move i right until it becomes valid
 * - track the maximum valid window length
 *
 * The final answer is:
 * total length - longest valid window length
 *
 * Time: O(n log n) due to sorting
 * Space: O(1) extra space apart from the sort
 */
class Solution {
    public int minRemoval(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        int len = 0, i = 0;
        for (int j = 0; j < n; j++) {
            while ((long)nums[i] * k < (long)nums[j]) i++; // Shrink from the left until min * k >= max for the window
            len = Math.max(len, j - i + 1); // Record the longest balanced window seen so far
        }
        return n-len; // Remove everything outside the best valid window
    }
}
