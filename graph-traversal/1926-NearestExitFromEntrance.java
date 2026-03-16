/*
 * LeetCode 1926: Nearest Exit from Entrance in Maze
 *
 * Approach:
 * Use BFS starting from the entrance. Since every move has the same cost,
 * BFS guarantees that the first exit reached is the nearest one.
 *
 * The maze itself is used to mark visited cells by turning visited '.' cells
 * into '+'. This prevents revisiting cells and avoids needing a separate
 * visited array.
 *
 * For each cell removed from the queue, check its four neighbours:
 * 1. skip out-of-bounds positions
 * 2. if an unvisited neighbour is on the boundary, return its distance
 * 3. otherwise, if it is an open cell, add it to the queue and continue BFS
 *
 * Time: O(m * n)
 * Space: O(m * n)
 */
class Solution {
    public int nearestExit(char[][] maze, int[] entrance) {
        int m = maze.length;
        int n = maze[0].length;
        int[][] dist = new int[m][n]; // dist[r][c] stores the minimum number of steps from the entrance to cell (r, c)
        Queue<int[]> q = new LinkedList();
        q.offer(entrance);
        maze[entrance[0]][entrance[1]] = '+'; // Mark the entrance as visited so it is not treated as an exit later
        int[] dr = new int[]{0, 1, 0, -1}; // Enumerate all 4 directions
        int[] dc = new int[]{1, 0, -1, 0};
        while (!q.isEmpty()) {
            int[] next = q.poll();
            int r = next[0], c = next[1];
            for (int dir = 0; dir < 4; dir++) {
                int nr = r + dr[dir], nc = c + dc[dir];
                if (!(0 <= nr && nr < m && 0 <= nc && nc < n)) continue; // Ignore moves that leave the maze
                if ((nr == 0 || nr == m-1 || nc == 0 || nc == n-1) && (maze[nr][nc] == '.')) {
                    return dist[r][c]+1; // First boundary cell found by BFS is the nearest exit
                } else {
                    if (maze[nr][nc] == '.') {
                        q.offer(new int[]{nr, nc});
                        dist[nr][nc] = dist[r][c] + 1;
                        maze[nr][nc] = '+'; // Mark as visited as soon as it is queued to avoid duplicate work
                    }
                }
            }
        }
        return -1; // If no exit found
    }
}
