/*
 * LeetCode 909: Snakes and Ladders
 *
 * Approach:
 * Treat the board as an unweighted directed graph and run BFS from square 1.
 * Each square has edges to the next 1 to 6 squares, corresponding to the
 * possible die rolls. If the destination square contains a snake or ladder,
 * that move immediately redirects to its endpoint.
 *
 * The main difficulty in this problem is not BFS itself, but converting the
 * 2D board into the square numbering used by the problem statement.
 *
 * Board numbering:
 * The squares are numbered from 1 to n^2 starting from the bottom-left corner.
 * The direction alternates on every row:
 * - bottom row: left to right
 * - next row up: right to left
 * - next row up: left to right
 * - and so on in a zigzag pattern
 *
 * We build a HashMap from square number -> board value by
 * iterating row-by-row from the bottom of the board upward:
 * - i represents the row index in the flattened numbering order, not the
 *   original board row
 * - board[n - i - 1][j] converts that into the actual row in the input board
 * - when i is even, square numbers increase left to right
 * - when i is odd, square numbers increase right to left
 *
 * After this flattening step, BFS becomes straightforward on square numbers
 * from 1 to n^2.
 *
 * Time: O(n^2)
 * Space: O(n^2)
 */
class Solution {
    public int snakesAndLadders(int[][] board) {
        int n = board.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i % 2 == 0) {
                    int square = i * n + j + 1;
                    map.put(square, board[n-i-1][j]); // Even-numbered flattened rows run left to right
                }
                else {
                    int square = (i+1) * n - j;
                    map.put(square, board[n-i-1][j]); // Odd-numbered flattened rows run right to left
                }
            }
        }

        ArrayList<ArrayList<Integer>> AL = new ArrayList<>();
        ArrayList<Integer> dist = new ArrayList<>(Collections.nCopies(n*n+1, -1));

        for (int i = 0; i < n*n+1; i++) {
            AL.add(new ArrayList<>());
        }
        for (int i = 1; i <= n*n; i++) {
            for (int j = 1; j <= 6; j++) {
                if (i + j > n*n) continue; // Cannot move beyond the final square
                if (map.get(i+j) == -1) AL.get(i).add(i+j); // Normal move: land directly on the rolled square
                else AL.get(i).add(map.get(i+j)); // Snake or ladder: move immediately to its destination
            }
        }
        Queue<Integer> q = new LinkedList<>();
        q.offer(1);
        dist.set(1, 0); // Start BFS from square 1 with 0 moves taken
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : AL.get(u)) {
                if (dist.get(v) == -1) {
                    dist.set(v, dist.get(u) + 1); // First time reaching a square gives its minimum number of moves
                    q.offer(v);
                }
            }
        }
        if (dist.get(n*n) == -1) return -1;
        else return dist.get(n*n);
    }
}
