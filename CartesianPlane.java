import java.util.HashMap;
import java.util.ArrayList;
import java.lang.Math;

/**
 * The CartesianPlane class creates 2D planes. Planes are primarily used to house MathEq objects and do work on MathEq objects in the plane.
 * @author wgao9961
 */
public class CartesianPlane {
  //unused . . . for now!
  private int xBound;
  private int yBound;

  private ArrayList<MathEq> equations = new ArrayList<MathEq>();
  /**
   * Default constructor. Creates a simple plane with no restrictions.
   */
  public CartesianPlane() {
    xBound = Integer.MAX_VALUE;
    yBound = Integer.MAX_VALUE;
  }
  // public CartesianPlane(int x) {
  //   this.xBound = x;
  //   this.yBound = Integer.MAX_VALUE;
  // }
  // public CartesianPlane(int x, int y) {
  //   this.xBound = x;
  //   this.yBound = y;
  // }
  /**
   * 
   * @param inx The index of the equation to retrieve. The method assumes that inx is a valid index to retrieve from.
   * @return The equation at the specified index.
   */
  public MathEq getEq(int inx) {
    return equations.get(inx);
  }
  /**
   * 
   * @return returns the number of equations the plane houses.
   */
  public int getEqCount() {
    return equations.size();
  }
  /**
   * Adds an equation to the plane. The plane will reject an equation if the exact same equation already exists.
   * @param e The equation to add to the plane
   * @return a boolean that tells whether or not the equation was successfully added
   */
  public boolean addEquation(MathEq e) {
    // if ( !e.getRoot().getLeft().equals(new MathChar('y'))) {
    //   return false;
    // }
    for (int n = 0 ;n != equations.size(); n++) {
      if (e.deepEquals(equations.get(n))) {
        return false;
      }
    }
    equations.add(e);
    return true;
  }

  /**
   * Searches for all intersections between two lines within an upper and lower bound.
   * @param ind1 The index of the first equation in this CartesianPlane's list of equations.
   * @param ind2 The index of the second equation in this CartesianPlane's list of equations.
   * @param lower A double that specifies where the search should begin.
   * @param upper A double that specifies where the search should end.
   * @return An array of points denoting where the intersections may exist. 
   */
  public Point[] findIntersections(int ind1, int ind2, double lower, double upper) {
    if (upper < lower) {
      double q = lower;
      lower = upper;
      upper = q;
    }
    ArrayList<Point> hitPoints = new ArrayList<Point>();
    MathEq eq1 = equations.get(ind1);
    MathEq eq2 = equations.get(ind2);
    for (double n = lower-.00001d; n <= upper+.00001d; n+=.00001d) {
      n = Math.round(n*100000)/100000d;
      Double funcVal = eq1.evaluate(n);
      if (funcVal == null){
        return new Point[0];
      }
      double diff = Math.abs(funcVal - eq2.evaluate(n));
      if (diff < .0001) {
        double j = Math.round(n*100) / (double) 100;
        funcVal = Math.round(funcVal*100) / (double) 100;
        
        hitPoints.add(new Point(j, funcVal));
      }
    }
    
    for (int n = 1; n < hitPoints.size(); n++) {
      if (hitPoints.get(n).x() == hitPoints.get(n-1).x() ) {
        hitPoints.remove(n-1);
        n--;
      } 
    }
    Point[] nAr = new Point[hitPoints.size()];
    for (int n = 0 ;n != hitPoints.size(); n++) {

      nAr[n] = hitPoints.get(n);


        
    }
    return nAr;
  }
  /**
   * A void function that prints out the contents of the plane.
   */
  public void printEqFromPlane() {
    System.out.println("Your equations:");
    int count = this.getEqCount();
    for (int n = 0; n != count; n++) {
      System.out.println(n+": " + this.getEq(n).toHumanReadableString());
    }
  }


}

  