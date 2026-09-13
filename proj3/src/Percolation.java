import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // TODO: 添加任何必要的实例变量。
    private int range;
    private boolean[][] grid;
    private WeightedQuickUnionUF uf;
    private int top;
    private int openBottom[];
    private int bottomCount;
    private int OpenCount;

    public Percolation(int N) {
        // TODO: 补全此构造方法。 创建 N×N 网格，所有位置初始均为阻塞状态
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        range = N;
        this.grid = new boolean[N][N];                         //改 + 1
        uf = new WeightedQuickUnionUF(range * range + 2); // 改 range + 1
        this.top = range * range;
        this.openBottom = new int[range];
        this.OpenCount = 0;
        this.bottomCount = 0;
    }

    public void open(int row, int col) {
        // TODO: 补全此方法。 如果位置 (row, col) 尚未开放，则将其开放
        judge(row, col);
        if (grid[row][col]) {
            return;
        }
        grid[row][col] = true;
        OpenCount++;
        int index = getIndex(row, col);
        if (row == 0) {
            uf.union(index, top);
        }
        //上
        if (row > 0 && isOpen(row - 1, col)) {
            uf.union(index, getIndex(row - 1, col));
        }
        //下
        if (row < range - 1 && isOpen(row + 1, col)) {
            uf.union(index, getIndex(row + 1, col));
        }
        //左
        if (col > 0 && isOpen(row, col - 1)) {
            uf.union(index, getIndex(row, col - 1));
        }
        //右
        if (col < range - 1 && isOpen(row, col + 1)) {
            uf.union(index, getIndex(row,col + 1));
        }
        if (row == range - 1) {
            openBottom[bottomCount] = getIndex(row, col);
            bottomCount++;
        }
    }

    public boolean isOpen(int row, int col) {
        // TODO: 补全此方法。 位置 (row, col) 是否为开放状态？
        judge(row, col);
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        // TODO: 补全此方法。 位置 (row, col) 是否为连通满状态？
        judge(row, col);
        if (!grid[row][col]) {
            return false;
        }
        return uf.connected(getIndex(row, col),top);
    }

    public int numberOfOpenSites() {
        // TODO: 补全此方法。 开放位置的数量
        return OpenCount;
    }

    public boolean percolates() {
        // TODO: 补全此方法。 该系统是否发生渗流？
        for (int i = 0; i < bottomCount; i++) {
            if (uf.connected(openBottom[i], top)) {
                return true;
            }
        }
        return false;
    }
    // TODO: 添加任何有用的辅助方法（我们强烈建议这样做！）。
    public void judge(int row, int col) {
        if (row < 0 || row >= range || col < 0 || col >= range) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    public int getIndex(int row, int col) {
        return row * range + col;
    }
    // TODO: 提交前移除所有 TODO 注释。

}