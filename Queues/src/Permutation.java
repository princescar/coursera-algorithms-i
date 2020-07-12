import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
   public static void main(String[] args) {
      int k = Integer.parseInt(args[0]);
      if (k == 0) {
         return;
      }
      RandomizedQueue<String> queue = new RandomizedQueue<String>();
      int i = 0;
      while (!StdIn.isEmpty()) {
         String s = StdIn.readString();
         if (i >= k) {
            int x = StdRandom.uniform(i + 1);
            if (x < k) {
               queue.dequeue();
               queue.enqueue(s);
            }
         } else {
            queue.enqueue(s);
         }
         i++;
      }
      for (String s : queue) {
         StdOut.println(s);
      }
   }
}