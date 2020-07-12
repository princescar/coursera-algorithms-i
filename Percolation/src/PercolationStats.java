import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private static final double CONFIDENCE_95 = 1.96;
  private final int trails;
  private final double[] pList;

  // perform independent trials on an n-by-n grid
  public PercolationStats(int n, int trials) {
    if (n <= 0) {
      throw new IllegalArgumentException("n cannot be negative or zero.");
    }
    if (trials <= 0) {
      throw new IllegalArgumentException("trials cannot be negative or zero.");
    }

    this.trails = trials;
    this.pList = new double[trials];

    for (int i = 0; i < trials; i++) {
      Percolation percolation = new Percolation(n);
      while (!percolation.percolates()) {
        int row = StdRandom.uniform(n) + 1;
        int col = StdRandom.uniform(n) + 1;
        percolation.open(row, col);
      }
      double p = ((double) percolation.numberOfOpenSites()) / n / n;
      this.pList[i] = p;
    }
  }

  // sample mean of percolation threshold
  public double mean() {
    return StdStats.mean(this.pList);
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return StdStats.stddev(this.pList);
  }

  // low endpoint of 95% confidence interval
  public double confidenceLo() {
    return this.mean() - CONFIDENCE_95 * this.stddev() / Math.sqrt(this.trails);
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return this.mean() + CONFIDENCE_95 * this.stddev() / Math.sqrt(this.trails);
  }

 // test client (see below)
 public static void main(String[] args) {
  int n = Integer.parseInt(args[0]);
  int trials = Integer.parseInt(args[1]);
  PercolationStats ps = new PercolationStats(n, trials);
  double mean = ps.mean();
  double stddev = ps.stddev();
  double confidenceLo = ps.confidenceLo();
  double confidenceHi = ps.confidenceHi();
  StdOut.println("mean\t\t\t= " + mean);
  StdOut.println("stddev\t\t\t= " + stddev);
  StdOut.println("95% confidence interval\t= [" + confidenceLo + ", " + confidenceHi + "]");
 }

}