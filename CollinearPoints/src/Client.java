import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.awt.*;

public class Client {
    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.setCanvasSize(800, 800);
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-5000, 37768);
        StdDraw.setYscale(-5000, 37768);
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(Color.RED);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        Font font = StdDraw.getFont();
        Font newFont = font.deriveFont(0, 10);
        StdDraw.setFont(newFont);
        StdDraw.setPenColor(Color.GRAY);
        for (Point p : points) {
            drawLabel(p);
        }
        StdDraw.show();

        // print and draw the line segments
        StdDraw.setPenColor(Color.BLUE);
        StdDraw.setPenRadius(0.005);
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    private static void drawLabel(Point p) {
        String str = p.toString();
        String xy = str.substring(1, str.length() - 1);
        String[] nums = xy.split(",");
        int x = Integer.parseInt(nums[0].trim());
        int y = Integer.parseInt(nums[1].trim());
        StdDraw.text(x, y, str);
    }
}