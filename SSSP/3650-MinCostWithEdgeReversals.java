/*
 * LeetCode 3650: Minimum Cost Path With Edge Reversals
 *
 * Approach:
 * Model the problem as a weighted directed graph and run Dijkstra's algorithm from node 0.
 *
 * For each directed edge u -> v with cost w:
 * 1. keep the original direction u -> v with cost w
 * 2. add the option for the reversed direction v -> u with cost 2 * w
 * Once this is done, it is just a standard shortest-path problem.
 *
 * The minimum total cost to reach node n - 1 is then the shortest distance found by Dijkstra.
 *
 * Time: O((n + m) log n)
 * Space: O(n + m)
 */

class Solution {
    public int minCost(int n, int[][] edges) {
        ArrayList<ArrayList<int[]>> AL = new ArrayList<>();
        for (int i = 0; i < n; i++) AL.add(new ArrayList<>());
        Comparator<int[]> byWeight = (a, b) -> Integer.compare(a[1], b[1]);
        PriorityQueue<int[]> pq = new PriorityQueue<>(byWeight); // Min-heap ordered by current best known distance
        ArrayList<Integer> dist = new ArrayList<>(Collections.nCopies(n, (int) 1e9));
        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            AL.get(edge[0]).add(new int[]{edge[1], edge[2]}); // Traverse the original directed edge at its given cost
            AL.get(edge[1]).add(new int[]{edge[0], 2 * edge[2]}); // Traversing in reverse simulates paying the reversal cost
        }
        dist.set(0, 0);
        pq.offer(new int[]{0, 0});
        while (!pq.isEmpty()) {
            int[] next = pq.poll();
            int u = next[0];
            for (int[] neighbours : AL.get(u)) {
                int v = neighbours[0], w = neighbours[1];
                if (dist.get(u) + w < dist.get(v)) {
                    dist.set(v, dist.get(u) + w);
                    pq.offer(new int[]{v, dist.get(v)}); // Relax the edge and push the improved distance into the heap
                }
            }
        }
        return (dist.get(n-1) != (int) 1e9) ? dist.get(n-1) : -1; // If still unreachable, no valid path exists
    }
}
