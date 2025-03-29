import edu.princeton.cs.algs4.StdOut;

public class HelloGoodbye {
  public static void main(String[] argv) {
    String name1 = argv[0];
    String name2 = argv[1];
    StdOut.println(String.format("Hello %s and %s.", name1, name2));
    StdOut.println(String.format("Goodbye %s and %s.", name2, name1));
  }
}