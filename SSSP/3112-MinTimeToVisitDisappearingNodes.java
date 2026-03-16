/*
 * LeetCode 3112: Minimum Time to Visit Disappearing Nodes
 *
 * Approach:
 * Run Dijkstra's algorithm from node 0, but **only allow transitions that reach
 * the next node before that node disappears**.
 *
 * The graph is undirected, so each edge is added in both directions. During
 * relaxation, a path to node v is valid only if:
 * 1. it improves the current shortest known distance to v, and
 * 2. the arrival time is strictly less than disappear[v].
 *
 * After Dijkstra finishes, any node whose shortest distance is still invalid
 * with respect to its disappearance time is reported as -1.
 *
 * Time: O((n + m) log n)
 * Space: O(n + m)
 */
class Solution {
    public int[] minimumTime(int n, int[][] edges, int[] disappear) {
        int[] res = new int[n];
        ArrayList<Integer> dist = new ArrayList<>(Collections.nCopies(n, (int) 1e9));
        ArrayList<ArrayList<int[]>> AL = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            AL.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            AL.get(edge[0]).add(new int[]{edge[2], edge[1]}); // Store as {weight, neighbour}
            AL.get(edge[1]).add(new int[]{edge[2], edge[0]}); // Add reverse direction for undirected graph
        }
        Comparator<int[]> byDist = (a, b) -> Integer.compare(a[0], b[0]);
        Comparator<int[]> byVtx = (a, b) -> Integer.compare(a[1], b[1]);
        PriorityQueue<int[]> pq = new PriorityQueue<>(byDist.thenComparing(byVtx)); // Min-heap ordered by shortest current arrival time
        pq.offer(new int[]{0, 0});
        dist.set(0, 0);
        while (!pq.isEmpty()) {
            int[] next = pq.poll();
            int u = next[1], curr_u = next[0];
            if (dist.get(u) != curr_u) continue; // Skip entries that no longer match the best known distance
            for (int[] w_v : AL.get(u)) {
                int w = w_v[0], v = w_v[1];
                if (dist.get(u) + w < dist.get(v) && dist.get(u) + w < disappear[v]) {
                    dist.set(v, dist.get(u) + w);
                    pq.offer(new int[]{dist.get(u) + w, v}); // Relax only if the new path arrives before node v disappears
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (dist.get(i) < disappear[i]) res[i] = dist.get(i);
            else res[i] = -1; // Unreachable or reachable only at/after disappearance time
        }
        return res;
    }
}
