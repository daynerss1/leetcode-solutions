/*
 * LeetCode 2596: Check Knight Tour Configuration
 *
 * Approach:
 * Treat the grid as a fixed traversal order and validate whether that order
 * can be followed by legal knight moves starting from the top-left corner.
 *
 * Starting from (0, 0), perform BFS where the only valid next cell is one that:
 * 1. is a legal knight move away, and
 * 2. contains the next number in the sequence, i.e. current value + 1.
 *
 * If this process reaches every cell exactly once, then the grid represents a
 * valid knight tour configuration. Otherwise, the given numbering cannot be reached in the required order.
 *
 * Time: O(n^2)
 * Space: O(n^2)
 */
class Solution {
    public boolean checkValidGrid(int[][] grid) {
        int n = grid.length;
        int[] dr = new int[]{-2, -1, 1, 2, 2, 1, -1, -2}; // Row offsets for all 8 knight moves
        int[] dc = new int[]{1, 2, 2, 1, -1, -2, -2, -1}; // Column offsets for all 8 knight moves
        boolean[][] vis = new boolean[n][n]; // To keep track of visited cells
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{0, 0}); // Tour begins at cell (0, 0), which should contain 0
        vis[0][0] = true;
        while (!q.isEmpty()) {
            int[] next = q.poll();
            int r = next[0], c = next[1];
            for (int dir = 0; dir < 8; dir++) {
                int nr = r + dr[dir], nc = c + dc[dir];
                if (!(0 <= nr && nr < n && 0 <= nc && nc < n)) continue; // Check if knight goes out of bounds
                if ((grid[nr][nc] == grid[r][c] + 1) && !vis[nr][nc]) { // Extra "+1" condition to check numerical order
                    vis[nr][nc] = true;
                    q.offer(new int[]{nr, nc}); // Only continue along the unique next step required by the grid numbering
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!vis[i][j]) return false; // Any unvisited cell means the numbered tour cannot be followed completely
            }
        }
        return true;
    }
}
