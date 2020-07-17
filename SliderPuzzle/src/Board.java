import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
  private final int[][] tiles;
  private final int n;
  private final int blankRow;
  private final int blankCol;
  private Board twin;

  // create a board from an n-by-n array of tiles,
  // where tiles[row][col] = tile at (row, col)
  public Board(int[][] tiles) {
    validate(tiles);
    n = tiles.length;
    this.tiles = new int[n][n];
    int row = -1;
    int col = -1;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        this.tiles[i][j] = tiles[i][j];
        if (tiles[i][j] == 0) {
          row = i;
          col = j;
        }
      }
    }
    this.blankRow = row;
    this.blankCol = col;
  }

  private void validate(int[][] squares) {
    if (squares == null) {
      throw new IllegalArgumentException("tiles cannot be null.");
    }
    int width = squares.length;
    for (int[] row : squares) {
      if (row.length != width) {
        throw new IllegalArgumentException("tiles must be n * n.");
      }
    }
  }

  private Board getTwin() {
    int row1 = StdRandom.uniform(n);
    int col1 = StdRandom.uniform(n);
    while (row1 == blankRow && col1 == blankCol) {
      row1 = StdRandom.uniform(n);
      col1 = StdRandom.uniform(n);
    }
    int row2 = StdRandom.uniform(n);
    int col2 = StdRandom.uniform(n);
    while (row2 == blankRow && col2 == blankCol || row2 == row1 && col2 == col1) {
      row2 = StdRandom.uniform(n);
      col2 = StdRandom.uniform(n);
    }
    return exch(row1, col1, row2, col2);
  }

  private Board exch(int row1, int col1, int row2, int col2) {
    assert row1 >= 0 && row1 < n;
    assert col1 >= 0 && col1 < n;
    assert row2 >= 0 && row2 < n;
    assert col2 >= 0 && col2 < n;

    int[][] newTiles = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        newTiles[i][j] = tiles[i][j];
      }
    }

    int temp = newTiles[row1][col1];
    newTiles[row1][col1] = newTiles[row2][col2];
    newTiles[row2][col2] = temp;

    return new Board(newTiles);
  }

  // string representation of this board
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.tiles.length);
    sb.append(System.lineSeparator());
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        sb.append(' ');
        sb.append(tiles[i][j]);
      }
      sb.append(System.lineSeparator());
    }
    return sb.toString();
  }

  // board dimension n
  public int dimension() {
    return n;
  }

  // number of tiles out of place
  public int hamming() {
    int count = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (tiles[i][j] != i * n + j + 1)
          count++;
      }
    }
    count--;
    return count;
  }

  // sum of Manhattan distances between tiles and goal
  public int manhattan() {
    int sum = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        int num = tiles[i][j];
        if (num == 0)
          continue;
        int row = (num - 1) / n;
        int col = (num - 1) % n;
        sum += Math.abs(row - i);
        sum += Math.abs(col - j);
      }
    }
    return sum;
  }

  // is this board the goal board?
  public boolean isGoal() {
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        int num = tiles[i][j];
        if (num == 0) {
          if (i != n - 1 || j != n - 1)
            return false;
          else
            continue;
        }
        int row = (num - 1) / n;
        int col = (num - 1) % n;
        if (row != i || col != j)
          return false;
      }
    }
    return true;
  }

  // does this board equal y?
  public boolean equals(Object y) {
    if (y == null) return false;
    if (y.getClass() != this.getClass()) return false;

    Board that = (Board) y;
    if (that.n != this.n) return false;

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (that.tiles[i][j] != this.tiles[i][j])
          return false;
      }
    }
    return true;
  }

  // all neighboring boards
  public Iterable<Board> neighbors() {
    Stack<Board> stack = new Stack<Board>();
    if (n < 2) return stack;

    if (blankRow > 0)      stack.push(exch(blankRow, blankCol, blankRow - 1, blankCol));
    if (blankRow < n - 1)  stack.push(exch(blankRow, blankCol, blankRow + 1, blankCol));
    if (blankCol > 0)      stack.push(exch(blankRow, blankCol, blankRow, blankCol - 1));
    if (blankCol < n - 1)  stack.push(exch(blankRow, blankCol, blankRow, blankCol + 1));

    return stack;
  }

  // a board that is obtained by exchanging any pair of tiles
  public Board twin() {
    if (twin == null) twin = getTwin();
    return twin;
  }

  // unit testing (not graded)
  public static void main(String[] args) {
    // int[][] tiles = {
    //   { 8, 1, 3 },
    //   { 4, 0, 2 },
    //   { 7, 6, 5 }
    // };
    // int[][] tiles = {
    //   { 1, 0, 3 },
    //   { 4, 2, 5 },
    //   { 7, 8, 6 }
    // };
    int[][] tiles = {
      { 1, 2, 3 },
      { 4, 5, 6 },
      { 7, 8, 0 }
    };

    Board board = new Board(tiles);
    System.out.println(board);
    System.out.println("is goal: " + board.isGoal());
    System.out.println("hamming: " + board.hamming());
    System.out.println("manhattan: " + board.manhattan());

    System.out.println("neighbors:");
    Iterable<Board> neighbors = board.neighbors();
    for (Board neighbor : neighbors) {
      System.out.println(neighbor);
    }

    Board twin = board.twin();
    System.out.println("twin:");
    System.out.println(twin);
  }
}
