/*
 * LeetCode 200: Number of Islands
 *
 * Approach:
 * Treat the grid as an unweighted graph where each land cell '1' is a node,
 * and edges exist between horizontally or vertically adjacent land cells.
 *
 * The problem then becomes counting how many connected components of land
 * exist in the grid. Each time we find an unvisited land cell, we have found
 * a new island, so we start a BFS from that cell and mark the entire connected
 * component as visited.
 *
 * By the time that BFS finishes, every land cell belonging to that island has
 * been visited, so future scans will not count it again.
 *
 * Time: O(m * n)
 * Space: O(m * n)
 */
class Solution {
    public int numIslands(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] dr = new int[]{0, 1, 0, -1}; // Four-directional movement: right, down, left, up
        int[] dc = new int[]{1, 0, -1, 0};
        Queue<int[]> q = new LinkedList<>();
        boolean[][] vis = new boolean[m][n];
        int numCCs = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1' && !vis[i][j]) {
                    q.offer(new int[]{i, j}); // Found a new unvisited land cell, so this starts a new island
                    vis[i][j] = true;
                    numCCs++; // Count this connected component once, then flood-fill the rest of it
                }
                while (!q.isEmpty()) {
                    int[] next = q.poll();
                    int r = next[0], c = next[1];
                    for (int dir = 0; dir < 4; dir++) {
                        int nr = r + dr[dir], nc = c + dc[dir];
                        if (!(0 <= nr && nr < m && 0 <= nc && nc < n)) continue; // Ignore neighbours outside the grid
                        if (grid[nr][nc] == '1' && !vis[nr][nc]) {
                            vis[nr][nc] = true;
                            q.offer(new int[]{nr, nc}); // Continue BFS through adjacent land in the same island
                        }
                    }
                }
            }
        }
        return numCCs;
    }
}
