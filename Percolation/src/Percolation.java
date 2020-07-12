import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private final int n;
  private final WeightedQuickUnionUF uf;
  private final int top;
  private final boolean[][] openFlags;
  private int openCellCount;
  private boolean[] connectBottom;
  private boolean connected;

  // creates n-by-n grid, with all sites initially blocked
  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("n cannot be negative or zero.");
    }
    this.n = n;
    this.openFlags = new boolean[n][n];
    // put extra virtual top in the end
    this.uf = new WeightedQuickUnionUF(n * n + 1);
    this.top = n * n;
    this.openCellCount = 0;
    this.connectBottom = new boolean[n * n + 1];
    this.connected = false;
  }

  // opens the site (row, col) if it is not open already
  public void open(int row, int col) {
    this.validate(row, col);

    if (this.isOpen(row, col)) {
      return;
    }
    openFlags[row - 1][col - 1] = true;
    this.openCellCount++;

    int index = this.getIndex(row, col);

    if (col > 1 && this.isOpen(row, col - 1)) {
      // connect with left cell
      this.union(index, index - 1);
    }

    if (col < this.n && this.isOpen(row, col + 1)) {
      // connect with right cell
      this.union(index, index + 1);
    }

    if (row > 1) {
      if (this.isOpen(row - 1, col)) {
        // connect with upper cell
        this.union(index, index - this.n);
      }
    } else {
      // connect with virtual top
      this.union(index, this.top);
    }

    if (row < this.n) {
      if (this.isOpen(row + 1, col)) {
        // connect with lower cell
        this.union(index, index + this.n);
      }
    }

    int root = this.uf.find(index);
    if (row == this.n) {
      this.connectBottom[root] = true;
    }
    boolean full = root == this.uf.find(this.top);
    if (full && this.connectBottom[root]) {
      this.connected = true;
    }
  }

  // is the site (row, col) open?
  public boolean isOpen(int row, int col) {
    this.validate(row, col);

    return this.openFlags[row - 1][col - 1];
  }

  // is the site (row, col) full?
  public boolean isFull(int row, int col) {
    this.validate(row, col);

    return this.uf.find(this.top) == this.uf.find(this.getIndex(row, col));
  }

  // returns the number of open sites
  public int numberOfOpenSites() {
    return this.openCellCount;
  }

  // does the system percolate?
  public boolean percolates() {
    // if the top and bottom is connected
    // return this.uf.find(this.top) == this.uf.find(this.bottom);
    return this.connected;
  }

  // validate that (row, col) is a valid cell
  private void validate(int row, int col) {
    if (row < 1 || row > this.n) {
      throw new IllegalArgumentException("row must be within [1, " + this.n + "]");
    }
    if (col < 1 || col > this.n) {
      throw new IllegalArgumentException("col must be within [1, " + this.n + "]");
    }
  }

  // get index of uf of (row, col)
  private int getIndex(int row, int col) {
    return (row - 1) * this.n + (col - 1);
  }

  private void union(int index1, int index2) {
    int root1 = this.uf.find(index1);
    int root2 = this.uf.find(index2);
    this.uf.union(index1, index2);
    int root = this.uf.find(index1);
    this.connectBottom[root] = this.connectBottom[root1] || this.connectBottom[root2];
  }

  // test client (optional)
  public static void main(String[] args) {
    int n = StdIn.readInt();
    Percolation percolation = new Percolation(n);
    while (!StdIn.isEmpty()) {
      int row = StdIn.readInt();
      int col = StdIn.readInt();
      percolation.open(row, col);
    }
    boolean p = percolation.percolates();
    StdOut.println(p);
  }
}