
import java.lang.Math;
/**
 * Line objects built from this class are not related to MathEq objects, but they can be converted. The cannot be placed into planes, but can be built from Point objects.
 * @author wgao9961
 */
public class Line {
  Point start;
  Point end;
  double deltaX;
  double deltaY;
  /**
   * Builds a line from two points
   * @param a a valid point object
   * @param b a valid point object that has an x values that differs from teh previous
   */
  public Line(Point a, Point b) {
    start = a;
    end = b;
    deltaX = b.x() - a.x();
    deltaY = b.y() - a.y();
  }
  
  /**
   * @return the slope of the line
   */
  public double slope() {
    if (deltaX == 0) {
      return (Double.POSITIVE_INFINITY);
    }
    return (deltaY / deltaX);
  }
  /**
   * @return The starting point as it was passed in from the constructor
   */
  public Point getStart() {
    return start;
  }
  
  /**
   * Retrieve the equation for the line 
   * @return A string in y=mx+b form
   */
  public String equation() {
    if (Double.compare(this.slope(), 0) == 0) {
      return ("y = " + start.y());
    }
    return ( "y = (" + this.slope() + ")(x - " + start.x() + ") -" + start.y() );
  }
}