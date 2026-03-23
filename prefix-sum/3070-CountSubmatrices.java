/*
 * LeetCode 3070: Count Submatrices with Top-Left Element and Sum Less Than k
 *
 * Approach:
 * Every valid submatrix in this problem must start at the top-left corner
 * (0, 0). That means each candidate submatrix is completely determined by its
 * bottom-right corner (i, j).
 *
 * So the problem essentially becomes:
 * count how many bottom-right corners (i, j) produce a top-left anchored
 * submatrix whose sum is <= k.
 *
 * To answer each anchored submatrix sum quickly, we convert
 * the grid in-place into a 2D prefix sum table:
 * - first accumulate row prefix sums
 * - then accumulate column prefix sums on top of those row prefixes
 *
 * After that transformation, `grid[i][j]` equals the sum of the submatrix
 * from (0, 0) to (i, j).
 *
 * Counting step:
 * For each row, we want the rightmost column `start` such that
 * prefixSum(row, start) <= k.
 *
 * Because prefix sums are non-decreasing as we move right, once a column is too large for
 * a given row, we can definitively say that every column to its right is also too large.
 * Also, as we move downward to the next row, sums can only stay the same or increase,
 * so we can also definitively say that the valid right boundary never moves right again.
 * We can then do a two-pointer scan over the rows, while decreasing the column pointer.
 *
 * Time: O(m * n)
 * Space: O(1) extra space apart from modifying the input grid
 */
class Solution {
    public int countSubmatrices(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        int start = n-1, sum = 0, res = 0, currRow = 0;
        for (int i = 1; i < n; i++) {
            grid[0][i] += grid[0][i-1];
            if (grid[0][i] > k) start = i - 1; // In the first row, track the last column whose top-row prefix sum is still valid
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                grid[i][j] += grid[i][j-1]; // Row-wise prefix sums
            }
        }
        for (int j = 0; j < n; j++) {
            for (int i = 1; i < m; i++) {
                grid[i][j] += grid[i-1][j]; // Convert row prefixes into full 2D prefix sums
            }
        }
        while (start >= 0 && currRow < m) {
            if (grid[currRow][start] < k) {
                currRow++;
                res += (start + 1); // All columns from 0 to start form valid top-left anchored submatrices in this row
                continue;
            }
            while (start >= 0 && grid[currRow][start] > k) start--; // Shrink the right boundary until the current row becomes valid again
            res += (start + 1); // Count all valid bottom-right corners in this row
            currRow++;
        }
        return res;
    }
}
