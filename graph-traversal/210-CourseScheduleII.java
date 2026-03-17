/*
 * LeetCode 210: Course Schedule II - Graph Traversal + Topological Sort
 *
 * Approach:
 * We need to order courses such that every prerequisite appears
 * before the course that depends on it. This is exactly the definition
 * of a topological ordering of a directed graph.
 *
 * We use Kahn's algorithm as the main topological-sort routine.
 *
 * Graph construction:
 * For each prerequisite pair [a, b], add the edge b -> a. This means course b
 * must be taken before course a, so a's indegree increases by 1.
 *
 * Kahn's algorithm:
 * 1. Put every course with indegree 0 into a queue. These are the courses that
 *    can be taken immediately because they currently have no unmet prerequisites.
 * 2. Repeatedly remove one course from the queue and append it to the answer.
 * 3. For each neighbour of that course, decrement its indegree because one of
 *    its prerequisites has now been satisfied.
 * 4. If any neighbour's indegree becomes 0, push it into the queue.
 *
 * We keep a 3-state visitation array for bookkeeping:
 * - vis[x] == 0: course x has not yet been discovered
 * - vis[x] == 1: course x has already been placed into the queue
 * - vis[x] == 2: course x has finished processing, and is popped from the queue
 *
 * Cycle detection is done by the final indegree check:
 * if any course still has positive indegree after the queue is exhausted,
 * then not all prerequisites could be resolved, so no valid order exists.
 *
 * Time: O(numCourses + prerequisites.length)
 * Space: O(numCourses + prerequisites.length)
 */
class Solution { // TopoSort + cycle detection - use Kahn's
    ArrayList<ArrayList<Integer>> AL;

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] indeg = new int[numCourses];
        int[] vis = new int[numCourses];
        AL = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            AL.add(new ArrayList<>());
        }
        for (int i = 0; i < prerequisites.length; i++) {
            AL.get(prerequisites[i][1]).add(prerequisites[i][0]); // prerequisite -> dependent course
            indeg[prerequisites[i][0]]++; // Number of prerequisites still required for this course
        }
        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (indeg[i] == 0) {
                q.offer(i); // Courses with indegree 0 are valid starting points
                vis[i] = 1; // Mark as discovered / already placed into the queue
            }
        }
        int[] ans = new int[numCourses];
        if (q.isEmpty()) return new int[0]; // No course can be taken first, so a valid ordering cannot start
        int topoIndex = 0;
        while (!q.isEmpty()) {
            int vtx = q.poll(); // Current course whose prerequisites have all been satisfied
            ans[topoIndex++] = vtx;
            for (int v : AL.get(vtx)) {
                indeg[v]--; // One prerequisite of v has now been completed
                if (indeg[v] == 0) { // Once all prerequisites are removed, v becomes available to take
                    q.offer(v);
                    vis[v] = 1; // Mark as discovered when it enters the queue
                }
                vis[vtx] = 2; // Mark the current course as fully processed
            }
        }
        for (int i = 0; i < numCourses; i++) {
            if (indeg[i] > 0) return new int[0]; // Remaining indegree means some prerequisite dependency was never resolved
        }
        return ans;
    }
}
