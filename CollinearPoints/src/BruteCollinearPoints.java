import edu.princeton.cs.algs4.Stack;

import java.util.Arrays;
import java.util.Iterator;

public class BruteCollinearPoints {

  private final Point[] points;
  private LineSegment[] segments;

  public BruteCollinearPoints(Point[] pts) {
    checkNull(pts);
    this.points = Arrays.copyOf(pts, pts.length);
    Arrays.sort(this.points);
    checkDuplicate(this.points);
    this.calcSegments();
  }

  public int numberOfSegments() {
    return this.segments.length;
  }

  public LineSegment[] segments() {
    return Arrays.copyOf(this.segments, this.segments.length);
  }

  private void calcSegments() {
    Stack<LineSegment> segmentStack = new Stack<>();
    for (int i = 0; i < this.points.length - 3; i++) {
      for (int j = i + 1; j < this.points.length - 2; j++) {
        for (int k = j + 1; k < this.points.length - 1; k++) {
          for (int m = k + 1; m < this.points.length; m++) {
            Point p0 = this.points[i];
            Point p1 = this.points[j];
            Point p2 = this.points[k];
            Point p3 = this.points[m];
            if (p0.slopeTo(p1) == p0.slopeTo(p2) && p0.slopeTo(p2) == p0.slopeTo(p3)) {
              segmentStack.push(new LineSegment(p0, p3));
            }
          }
        }
      }
    }
    this.assignSegments(segmentStack);
  }

  private void assignSegments(Stack<LineSegment> stacks) {
    segments = new LineSegment[stacks.size()];
    Iterator<LineSegment> iter = stacks.iterator();
    int n = 0;
    while (iter.hasNext()) {
      this.segments[n++] = iter.next();
    }
  }

  private static void checkDuplicate(Point[] pts) {
    for (int i = 1; i < pts.length; i++) {
      if (pts[i].compareTo(pts[i - 1]) == 0) {
        throw new IllegalArgumentException();
      }
    }
  }

  private static void checkNull(Point[] pts) {
    if (pts == null) {
      throw new IllegalArgumentException();
    }
    for (Point p : pts) {
      if (p == null) {
        throw new IllegalArgumentException();
      }
    }
  }
}
