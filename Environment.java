import java.util.Scanner;
import java.util.InputMismatchException;
/**
 * The Environment class exists to give the user some ability to input equations and find intersections. 
 * @author wgao9961
 */
public class Environment {
  public static void main(String[] args) {
    //no time to create a menu class, its tuesday 
    //also im not going to use this in the final final final product
    
    CartesianPlane plane = new CartesianPlane();

    //infinite loop; there exists an option to break the loop and exit
    while (true) {
      printIntro(); //print options each time
      int someSelection = getNextInt();
      if (someSelection == 1) { //regurgitate equations
        plane.printEqFromPlane();
      } else if (someSelection == 2){ //create-a-line workshop
        System.out.println("Point 1? (x,y) format only");
        Point fPoint = getNextPoint();
        Point sPoint;
        while (true) {
          System.out.println("Point 2? (x,y) format only");
          sPoint = getNextPoint();
          if (sPoint.x() != fPoint.x()) {
            break;
          } else {
            System.out.println("WOULD NOT CREATE A FUNCTION, TRY AGAIN");
          }
        } 
        MathEq nEq = new MathEq(new Line(fPoint,sPoint).equation());
        boolean b = plane.addEquation(nEq);
        if (b)
          System.out.println("Added " + (new Line(fPoint,sPoint).equation()).trim() + " as index number " + (plane.getEqCount()-1));
        else
          System.out.println("Equation rejected; identical one already exists");
      }else if(someSelection == 3) {
        System.out.println("Add a new equation starting with y=:");
        MathEq eq = getNextEq();
        
        boolean b = plane.addEquation(eq);
        if (b)
          System.out.println("Added " + eq.toHumanReadableString() + " as index number " + (plane.getEqCount()-1));
        else
          System.out.println("Equation rejected; identical one already exists");
        
      } else if (someSelection == 4){ //select two equations and intersect
        int lim = plane.getEqCount()-1;
        if (lim > 0) {
          plane.printEqFromPlane();
          System.out.println("Index for equation 1");
          int selection = getNextInt();
          if (selection > lim || selection < 0) {
            System.out.println("Not found; Selecting 0");
            selection = 0;
          }
          int fEq = selection;
          //duplicated
          System.out.println("Index for equation 2");
          selection = getNextInt();
          if (selection > lim || selection < 0) {
            System.out.println("Not found; Selecting 1");
            selection = 1;
          }
          int sEq = selection;
          
          System.out.println("Please dont enter absurdly large numbers. \nLower bound");
          double lBound = getNextDouble();
          System.out.println("Upper bound");
          double uBound = getNextDouble();

          Point[] ps = plane.findIntersections(fEq, sEq, lBound, uBound);
          System.out.println("found " + ps.length);
          for (Point p : ps) {
            System.out.println(p);
          }
        } else {
          System.out.println("NO EQUATIONS");
        }
      }else if (someSelection == 5){ //reprint intro (?)
        printIntro();
      }else if (someSelection == 6) { //quit
        System.out.println("Aborting Program! ");
        System.exit(0);
      } else {
        System.out.println("No instruction found.");
      }
    }
  }

  /**
   * Displays a menu of options that can be used in main.
   */
  public static void printIntro() {
    System.out.println("\n------------------------------------------------------------");
    System.out.println("Prototype graphing calculator without any of the graphing done! Your options are:");
    System.out.println("1. View active equations");
    System.out.println("2. Craft a line by plotting two points");
    System.out.println("3. Craft a rational function in the form of y= (We support carets AND e, but not functions)");
    System.out.println("4. Find (if it exists) intersections between two equations given their index and bounds to search through");
    System.out.println("5. See this panel again");
    System.out.println("6. Exit");
    System.out.println("------------------------------------------------------------\n");
  }
  
  /**
   * Get a double from System.in
   * @return A double from the user
   */
  public static double getNextDouble() {
    Scanner nIn = new Scanner(System.in);
    double nDoub = -1;
    //BLOCKING CODE, SO I CAN DO THIS W/O HARM
    while (true) {
      if (nIn.hasNextDouble()) {
        nDoub = nIn.nextDouble();
        break;
      }
    }
    return nDoub;
  }
  /**
   * Get an int from System.in
   * @return An int from the user
   */
  public static int getNextInt() {
    Scanner nIn = new Scanner(System.in);
    int nInt = -1;
    //BLOCKING CODE, SO I CAN DO THIS W/O HARM
    while (true) {
      try {
        nInt = nIn.nextInt();
        break;
      } catch (InputMismatchException e) {
        System.out.println("NOT A VALID NUMBER");
        nIn.next();
      }
    }

    return nInt;
  }
  /**
   * Get a point from System.in
   * @return A point from the user
   */
  public static Point getNextPoint() {
    Scanner nIn = new Scanner(System.in);
    Point nPoint = Point.parsePoint(nIn.nextLine());

    return nPoint;
  }
  /**
   * Get an equation from System.in
   * @return A fully parsed MathEq object
   */
  public static MathEq getNextEq() {
    Scanner nIn = new Scanner(System.in);
    MathEq nEq;
    while (true) {     
      nEq = new MathEq(nIn.nextLine());
      try {
        if (nEq.getRoot().getLeft().getData().equals("y")) {
          break;
        } else {
          System.out.println("NOT IN y=");
        }
        //not using assert here so
        nEq.getRoot().getRight().getData(); //this should suffice
      } catch (NullPointerException e) {
        System.out.println("Not Equation");
      }
      nIn.next();
    }
    return nEq;

  }
}