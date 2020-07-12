import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
  private final ArrayList<Point[]> lines;
  private final LineSegment[] segments;

  public FastCollinearPoints(Point[] points) {
    checkNull(points);
    Point[] pts = Arrays.copyOf(points, points.length);
    Arrays.sort(pts);
    checkDuplicate(pts);

    this.lines = new ArrayList<>();
    this.fillLines(pts);
    this.segments = this.convert();
  }

  private static void checkNull(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException("points is null.");
    }
    for (Point p : points) {
      if (p == null) {
        throw new IllegalArgumentException("point is null.");
      }
    }
  }

  private static void checkDuplicate(Point[] points) {
    int n = points.length;
    for (int i = 0; i < n - 1; i++) {
      if (points[i].compareTo(points[i + 1]) == 0) {
        throw new IllegalArgumentException("duplicate points.");
      }
    }
  }

  private void fillLines(Point[] points) {
    int n = points.length;
    if (n < 4) return;

    for (int i = 0; i < n - 3; i++) {
      Point p = points[i];
      this.fillPointLines(p, points);
    }
  }

  private void fillPointLines(Point p, Point[] points) {
    Point[] pts = Arrays.copyOf(points, points.length);
    Arrays.sort(pts, p.slopeOrder());

    int count = 1;
    double slope = p.slopeTo(pts[1]);
    for (int i = 2; i < pts.length; i++) {
      double newSlope = p.slopeTo(pts[i]);
      if (newSlope == slope) {
        count++;
      } else {
        if (count > 2 && p.compareTo(pts[i - count]) <= 0) {
          this.lines.add(new Point[] { p, pts[i - 1] });
        }
        count = 1;
        slope = newSlope;
      }
    }
    if (count > 2 && p.compareTo(pts[pts.length - count]) <= 0) {
      this.lines.add(new Point[] { p, pts[pts.length - 1] });
    }
  }

  private LineSegment[] convert() {
    int n = this.lines.size();
    LineSegment[] result = new LineSegment[n];
    for (int i = 0; i < n; i++) {
      Point[] line = this.lines.get(i);
      result[i] = new LineSegment(line[0], line[1]);
    }
    return result;
  }

  public int numberOfSegments() {
    return this.segments.length;
  }

  public LineSegment[] segments() {
    return Arrays.copyOf(this.segments, this.segments.length);
  }
}
