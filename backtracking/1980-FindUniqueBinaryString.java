/*
 * LeetCode 1980: Find Unique Binary String
 * Topic: Backtracking
 *
 * Approach:
 * We need to construct any binary string of length n that does not already
 * appear in the input array.
 *
 * The backtracking idea is to build the answer one character at a time.
 * At each step, we have exactly two choices:
 * 1. append '0'
 * 2. append '1'
 *
 * This forms a binary decision tree of depth n. Every root-to-leaf path
 * represents one candidate binary string of length n.
 *
 * To test whether a completed candidate is valid in O(1) average time,
 * all input strings are stored in a HashSet.
 *
 * Helper function return value:
 * solve(...) returns true if a valid missing binary string has already been
 * found somewhere in the current recursive branch.
 *
 * Why we want to return true:
 * Once a valid answer is found, there is no reason to keep exploring the rest
 * of the search tree. Returning true allows that success to propagate all the
 * way back up the recursive calls immediately, so the search stops early.
 *
 * How the backtracking works:
 * - append one choice to the current string
 * - recurse deeper
 * - if that branch succeeds, immediately return true
 * - otherwise remove the last appended character and try the other choice
 *
 * Time: O(2^n * n) in the worst case, because there are up to 2^n candidates
 * and converting the built string at the leaf costs O(n)
 * Space: O(n) recursion depth, excluding the input set
 */
class Solution {
    String ans = ""; // In global scope so recursive functions can easily update ans

    public String findDifferentBinaryString(String[] nums) {
        int n = nums[0].length();
        HashSet<String> set = new HashSet<>();
        for (String s : nums) set.add(s); // Store all existing strings for O(1) check
        StringBuilder sb = new StringBuilder();
        solve(0, n, sb, set);
        return ans;
    }

    public boolean solve(int len, int maxLen, StringBuilder curr, HashSet<String> s) {
        if (len == maxLen) {
            if (s.contains(curr.toString())) return false; // This completed candidate already exists, so this branch fails
            else {
                ans = curr.toString(); // Found a binary string of length n that is missing from the input
                return true; // Signal success so all earlier recursive calls can stop searching
            }
        }

        curr.append("0"); // Choose '0' for the current position
        if (solve(len + 1, maxLen, curr, s)) return true; // If this branch finds an answer, propagate success upward immediately
        curr.deleteCharAt(curr.length() - 1); // Undo the choice before trying the next possibility

        curr.append("1"); // Choose '1' for the current position
        if (solve(len + 1, maxLen, curr, s)) return true; // Again, stop the entire search as soon as one valid answer is found
        curr.deleteCharAt(curr.length() - 1); // Backtrack to restore curr to its previous state

        return false; // Neither choice produced a valid missing string from this state
    }
}
