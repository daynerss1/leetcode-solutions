/*
 * LeetCode 322: Coin Change
 *
 * Approach:
 * Use top-down dynamic programming with memoization on the remaining amount.
 * [We can also use bottom up tabular DP, but I was learning memoization at this current juncture.]
 *
 * Let sol(amount) be the minimum number of coins needed to make up that
 * amount. For each coin, try taking it once and recursively solve the smaller
 * subproblem amount - coin. The answer for the current amount is the minimum
 * over all valid choices.
 *
 * Memoization ensures each remaining amount is solved at most once, which
 * avoids the exponential blow-up of plain recursion.
 *
 * Base cases:
 * 1. amount == 0: no more coins are needed
 * 2. amount < 0: this path is invalid
 *
 * Time: O(amount * number_of_coins)
 * Space: O(amount)
 */
class Solution {
    public int coinChange(int[] coins, int amount) {
        HashMap<Integer, Integer> memo = new HashMap<>();
        return sol(coins, amount, memo);
    }

    public int sol(int[] coins, int amount, HashMap<Integer, Integer> memo) {
        if (memo.containsKey(amount)) return memo.get(amount); // Reuse previously computed result for this remaining amount
        if (amount == 0) return 0; // Exact match: no additional coins are needed
        if (amount < 0) return -1; // Overshot the target, so this choice of coins is invalid

        int res = Integer.MAX_VALUE;
        for (int coin : coins) {
            int remaining = amount - coin;
            int numNeededForRemaining = sol(coins, remaining, memo);

            if (numNeededForRemaining != -1 && numNeededForRemaining < res) {
                res = 1 + numNeededForRemaining; // Count the current coin plus the best way to form the remainder
            }
        }

        if (res == Integer.MAX_VALUE) {
            memo.put(amount, -1); // No coin choice could form this amount
            return -1;
        }

        memo.put(amount, res);
        return res;
    }
}
