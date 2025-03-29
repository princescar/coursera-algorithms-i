import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
  public static void main(String[] argv) {
    String champion = null;
    int count = 0;
    while (!StdIn.isEmpty()) {
      ++count;
      String candidate = StdIn.readString();
      boolean win = StdRandom.bernoulli(1.0  / count);
      if (win) {
        champion = candidate;
      }
    }
    StdOut.println(champion);
  }
}