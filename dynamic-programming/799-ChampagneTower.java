/*
 * LeetCode 799: Champagne Tower
 *
 * Approach:
 * Use dynamic programming where dp[r][c] represents the total amount of
 * champagne that arrives at glass c in row r before applying the final cap of 1.
 *
 * A glass can hold at most 1 cup. Any excess above 1 overflows equally into
 * the two glasses directly below it:
 *
 * overflow = (currentAmount - 1) / 2
 *
 * We fill the tower row by row from top to bottom. For each glass in the current row:
 * 1. if it lies on the left edge, it can only receive overflow from the glass
 *    directly above it
 * 2. if it lies on the right edge, it can only receive overflow from the
 *    upper-left glass
 * 3. otherwise, it is an interior glass and can receive overflow from both
 *    parents in the row above
 *
 * A parent contributes only if it contains more than 1 cup, because only the
 * excess spills downward. If a parent holds 1 cup or less, it contributes 0.
 *
 * We only need rows up to query_row, so the DP table is sized accordingly.
 * At the end, return min(1, dp[query_row][query_glass]) because the glass
 * itself cannot contain more than 1 cup.
 *
 * Time: O(query_row^2)
 * Space: O(query_row^2)
 */
class Solution {
    public double champagneTower(int poured, int query_row, int query_glass) {
        double[][] dp = new double[query_row+1][query_row+1];
        dp[0][0] = poured; // Entire poured amount starts at the top glass before any overflow happens

        for (int i = 1; i <= query_row; i++) {
            for (int j = 0; j <= i; j++) {
                if (j == 0) {
                    double prev = dp[i-1][0];
                    if (prev > 1) dp[i][j] += (prev-1) / 2; // Left edge receives overflow only from the glass directly above
                } else if (j == i) {
                    double prev = dp[i-1][i-1];
                    if (prev > 1) dp[i][j] += (prev-1) / 2; // Right edge receives overflow only from the upper-left parent
                } else {
                    double prevA = dp[i-1][j-1], prevB = dp[i-1][j];
                    if (prevA > 1) dp[i][j] += (prevA-1) / 2; // Contribution from the upper-left parent
                    if (prevB > 1) dp[i][j] += (prevB-1) / 2; // Contribution from the upper-right parent
                }
            }
        }

        return (dp[query_row][query_glass] > 1) ? 1 : dp[query_row][query_glass]; // A glass can contain at most 1 cup
    }
}
