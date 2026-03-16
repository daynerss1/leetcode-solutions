/*
 * LeetCode 1466: Reorder Routes to Make All Paths Lead to the City Zero
 *
 * Key Approach: (This is so clever!)
 * Build an adjacency list that stores both directions of every road, but use
 * the **sign** of the stored neighbour to remember whether that direction matches
 * the original edge orientation.
 *
 * For an original directed edge u -> v:
 * 1. store +v in u's adjacency list
 * 2. store -u in v's adjacency list
 *
 * Then perform DFS from city 0. Whenever DFS follows a positive neighbour,
 * that means the road currently points away from city 0 and must be reversed.
 * Negative neighbours represent roads that already point toward the current
 * DFS path, so they do not contribute to the answer.
 *
 * Time: O(n)
 * Space: O(n)
 */
class Solution {
    ArrayList<ArrayList<Integer>> AL;
    boolean[] vis;
    int res = 0;

    private void dfs(int u) {
        if (!vis[u]) {
            vis[u] = true;
            for (int v : AL.get(u)) { // Positive value: original edge goes from u to v; negative value: original edge comes into u
                if (vis[Math.abs(v)]) continue; // Recover the actual node index with abs(v) before checking whether it was visited
                if (v > 0) res++; // Following an original outward edge means this road must be reversed to make it lead back to 0
                dfs(Math.abs(v));
            }
        }
    }

    public int minReorder(int n, int[][] connections) {
        vis = new boolean[n];
        AL = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            AL.add(new ArrayList<>());
        }
        for (int[] edge : connections) {
            AL.get(edge[0]).add(edge[1]); // Original direction: travelling this way means the road points away from city 0
            AL.get(edge[1]).add(-edge[0]); // Reverse traversal is recorded as negative to show that the actual road already points correctly
        }

        dfs(0);
        return res;
    }
}
