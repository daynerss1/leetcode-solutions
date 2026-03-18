class UnionFindDS {
    int[] parent;
    int[] rank;
    int[] setSize;
    int numSets;

    public UnionFindDS(int n) {
        parent = new int[n];
        rank = new int[n];
        setSize = new int[n];
        numSets = n;

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
            setSize[i] = 1;
        }
    }

    public int findSet(int i) {
        if (parent[i] == i) return i;
        parent[i] = findSet(parent[i]); // Path compression flattens the tree for future queries
        return parent[i];
    }

    public void union(int i, int j) {
        int x = findSet(i);
        int y = findSet(j);
        if (x == y) return;

        if (rank[x] < rank[y]) {
            parent[x] = y;
            setSize[y] += setSize[x];
        } else if (rank[y] < rank[x]) {
            parent[y] = x;
            setSize[x] += setSize[y];
        } else {
            parent[y] = x;
            rank[x]++;
            setSize[x] += setSize[y];
        }

        numSets--;
    }

    public boolean isSameSet(int i, int j) {
        return findSet(i) == findSet(j);
    }

    public int numOfSets() {
        return numSets;
    }

    public int sizeOfSet(int i) {
        return setSize[findSet(i)];
    }
}
