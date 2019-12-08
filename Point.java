/**
 * The Point class creates points. Points are in the form of (x,y) and store two doubles corresponding to an x and y value.
 * @author wgao9961
 */ 

public class Point{
  private double x = 0;
  private double y = 0;
  /**
   * Default constructor; creates a point as (0,0)
   */
  Point(){
    this.x = 0;
    this.y = 0;
  }
  /**
   * Create a point at specified values
   * @param x An x coordinate for the point
   * @param y A y coordinate for the point
   */ 
  Point(double x, double y) {
    this.x = x;
    this.y = y;
  }
  /**
   * @return the x value for this point
   */ 
  public double x() {
    return this.x;
  }
  /**
   * @return the y value for this point
   */ 
  public double y() {
    return this.y;
  }
  /**
   * @return the coordinate in an easily parsable form
   */ 
  public String toString() {
    return(this.x + "@" + this.y);
  }
  /**
   * Determines whetehr or not two points are the same based on whether ro not their x and y values are the same
   * @param p A point to check agaisnt
   * @return A boolean that describes their equality
   */ 
  public boolean equals(Point p) {
    if (p.x() == this.x &&p.y() == this.y) {
      return true;
    }
    return false;
  }
  /**
   * A static method that parses a string and interprets the string as a point. It is severely restricted.
   * @param someString Some string in the form of (x,y). 
   * @return A point object. It tries to interpret the input string, but upon failure, it returns (0,0)
   */ 
  public static Point parsePoint(String someString) {
    String nStr = "";
    for (int n = 0 ;n != someString.length(); n++) {
      if (someString.charAt(n) != ' ') {
        nStr += someString.charAt(n);
      }
    }
    
    //im going to pull a deltamath here
    if (nStr.charAt(0) == '(' && nStr.charAt(nStr.length() - 1) == ')') {
      nStr = nStr.substring(1,nStr.length() - 1);
    } else {
      System.out.println("BAD POINT! EVALUATED " + someString + " AS (0,0)");
      return new Point();
    }
    
    if (nStr.indexOf(",") == -1) {
      System.out.println("BAD POINT! EVALUATED " + someString + " AS (0,0)");
      return new Point();
    } 
    
    String num1 = nStr.substring(0, nStr.indexOf(","));
    String num2 = nStr.substring(nStr.indexOf(",") + 1);
    try {
      return new Point(Double.parseDouble(num1), Double.parseDouble(num2));
    } catch (NumberFormatException e) {
      System.out.println("BAD POINT! EVALUATED " + someString + " AS (0,0)");
      return new Point();
    }
    
  }
      
}