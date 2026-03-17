/*
 * LeetCode 756: Pyramid Transition Matrix
 *
 * Approach:
 * Build the pyramid row by row. For the current row `bottom`, the next row is
 * formed by choosing one valid top character for every adjacent pair.
 *
 * When `next` becomes complete, recursion moves upward and treats that newly
 * built row as the next `bottom`.
 *
 * Why the helper returns true:
 * `gonext(...)` returns true if the current row can eventually lead to a valid
 * pyramid all the way up to length 1. As soon as one recursive branch succeeds,
 * returning true propagates that success upward immediately and stops the rest
 * of the search.
 *
 * Other notes:
 * 1. We preprocess `allowed` into a map from 2-character base (key) -> list of valid
 *    top characters (value), so we only try allowed transitions
 * 2. We memoize failed complete-rows in `bad`, so the same impossible row is not
 *    recomputed repeatedly. Importantly, we memoize only complete `bottom` rows,
 *    not partial `next` states. A row should only be marked bad after all
 *    possible ways to build the row above it have been tried and failed.
 */
class Solution {
    boolean ans = false;
    HashMap<String, List<Character>> can = new HashMap<>();
    HashSet<String> bad = new HashSet<>(); // In global scope for easy reference

    public boolean pyramidTransition(String bottom, List<String> allowed) {
        for (String s : allowed) {
            String base = s.substring(0, 2);
            char top = s.charAt(2);
            can.computeIfAbsent(base, k -> new ArrayList<>()).add(top);
        }
        gonext(bottom, new StringBuilder());
        return ans;
    }

    public boolean gonext(String bottom, StringBuilder next) {
        if (bottom.length() == 1) {
            ans = true;
            return true; // Successfully reached the top of the pyramid
        }
        if (next.length() == 0 && bad.contains(bottom)) {
            return false; // This completed row has already been proven impossible, we stop early
        }
        if (next.length() == bottom.length() - 1) { // 'bad' is guaranteed to not contain 'bottom', due to previous check
            return gonext(next.toString(), new StringBuilder()); // Finished one full next row, so recurse upward
        }

        int i = next.length();
        String base = bottom.substring(i, i + 2);
        List<Character> tops = can.get(base);
        if (tops == null) {
            return false; // No valid character can be placed above this adjacent pair
        }

        for (char top : tops) {
            next.append(top); // Choose one allowed character for the current pair
            if (gonext(bottom, next)) return true; // Stop immediately once one valid full construction is found
            next.deleteCharAt(next.length() - 1); // Backtrack and try the next choice
        }

        if (next.length() == 0) {
            bad.add(bottom); // Only mark the whole row as bad after every possible next-row construction has failed
        }
        return false;
    }
}
