/*
 * LeetCode 1557: Minimum Number of Vertices to Reach All Nodes
 *
 * Approach:
 * In a directed acyclic graph, every node with indegree greater than 0 can be
 * reached from some other node. Therefore, the only nodes that must be chosen
 * as starting vertices are the ones with indegree 0.
 *
 * Count the indegree of every node, then collect all nodes whose indegree
 * remains 0. That set is the minimum valid answer.
 *
 * Time: O(n + m)
 * Space: O(n)
 */
class Solution {
    public List<Integer> findSmallestSetOfVertices(int n, List<List<Integer>> edges) {
        int[] indeg = new int[n];
        List<Integer> res = new ArrayList<>();

        for (List<Integer> edge : edges) {
            int to = edge.get(1);
            indeg[to]++; // Only incoming edges matter for deciding which nodes need to be starting points
        }
        for (int vtx = 0; vtx < n; vtx++) {
            if (indeg[vtx] == 0) res.add(vtx); // Nodes with indegree 0 cannot be reached from any other node
        }

        return res;
    }
}
