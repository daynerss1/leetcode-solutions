/*
 * LeetCode 198: House Robber
 *
 * Approach:
 * If we start from the front of the array, the maximum amount of money that can be
 * robbed depends on the amounts at the back of the array.
 * Therefore, if we know how much we can maximally rob starting from the back,
 * we will know what to do at this current house i. (The decision is either to skip or rob)
 *
 * Working from the back, where i goes from n-1 to 0: (Excluding the first 2 from the back)
 * dp[i] = max(dp[i + 1], nums[i] + dp[i + 2])
 *
 * As mentioned, we start from the back of the array, filling the DP array
 * from right to left so that the subproblems dp[i + 1]
 * and dp[i + 2] are already known when computing dp[i].
 *
 * Base cases:
 * - dp[n - 1] = nums[n - 1], because only the last house is available
 * - dp[n - 2] = max(nums[n - 2], nums[n - 1]), because only one of the last
 *   two adjacent houses can be robbed
 *
 * Time: O(n)
 * Space: O(n)
 */
class Solution {
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];
        int[] dp = new int[n];
        dp[n-1] = nums[n-1]; // Best we can do from the last house is rob it
        dp[n-2] = Math.max(nums[n-1], nums[n-2]); // Between the last two adjacent houses, take the larger value
        for (int i = 2; i < n; i++) {
            dp[n-1-i] = Math.max(dp[n-i], dp[n+1-i] + nums[n-1-i]); // Skip current house or rob it and add the best from two houses ahead
        }
        return dp[0];
    }
}
