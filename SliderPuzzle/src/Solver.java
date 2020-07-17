import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
  private final Stack<Board> solution;

  private static class Node implements Comparable<Node> {
    private final Board board;
    private final Node parent;
    private final int moves;
    private final int priority;

    public Node(Board board, Node parent, int moves) {
      this.board = board;
      this.parent = parent;
      this.moves = moves;
      this.priority = board.manhattan() + moves;
    }

    @Override
    public int compareTo(Node that) {
      return this.priority - that.priority;
    }
  }

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {
    validate(initial);
    solution = getSolution(initial);
  }

  private void validate(Board initial) {
    if (initial == null) {
      throw new IllegalArgumentException("initial board should not be null.");
    }
  }

  private Stack<Board> getSolution(Board initial) {
    Board twin = initial.twin();

    MinPQ<Node> pq = new MinPQ<>();
    MinPQ<Node> twinPq = new MinPQ<>();

    pq.insert(new Node(initial, null, 0));
    twinPq.insert(new Node(twin, null, 0));

    boolean turnForTwin = false;
    while (!pq.isEmpty() && !pq.min().board.isGoal()) {
      if (!twinPq.isEmpty() && twinPq.min().board.isGoal()) return null;

      if (!turnForTwin) {
        Node current = pq.delMin();
        for (Board neighbor: current.board.neighbors())
          if (current.parent == null || !neighbor.equals(current.parent.board))
            pq.insert(new Node(neighbor, current, current.moves + 1));
      } else {
        Node current = twinPq.delMin();
        for (Board neighbor: current.board.neighbors())
          if (current.parent == null || !neighbor.equals(current.parent.board))
            twinPq.insert(new Node(neighbor, current, current.moves + 1));
      }
      turnForTwin = !turnForTwin;
    }

    if (pq.isEmpty()) return null;

    Node min = pq.delMin();
    Stack<Board> stack = new Stack<>();
    while (min != null) {
      stack.push(min.board);
      min = min.parent;
    }

    return stack;
  }

  // is the initial board solvable? (see below)
  public boolean isSolvable() {
    return solution != null;
  }

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves() {
    return solution == null ? -1 : solution.size() - 1;
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    return solution;
  }

  // test client (see below) 
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
    // int[][] tiles = {
    //   { 1, 2, 3 },
    //   { 4, 5, 6 },
    //   { 7, 8, 0 }
    // };
    // int[][] tiles = {
    //   { 0, 1, 3 },
    //   { 4, 2, 5 },
    //   { 7, 8, 6 }
    // };
    // int[][] tiles = {
    //   { 1, 2, 3 },
    //   { 4, 5, 6 },
    //   { 8, 7, 0 }
    // };
    int[][] tiles = {
      { 1, 2, 3 },
      { 0, 7, 6 },
      { 5, 4, 8 }
    };

    // solve the slider puzzle
    Board initial = new Board(tiles);
    Solver solver = new Solver(initial);
    System.out.println(solver.moves());
    Iterable<Board> solution = solver.solution();
    if (solution != null) {
      for (Board step : solution) {
        System.out.println(step);
      }
    }
  }
}
