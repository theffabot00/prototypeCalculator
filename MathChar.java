
/**
 * MathChar makes use of nodes and stores mathematical operators or operands in them. As of version 0.1, MathChar
 * only supports basic mathematical operations and operands, but lays foundations for mathematical functions. It 
 * has no foundation for calculus, vectors, or sets. As of writing this class, i am severely unqualified to write
 * viable code, though this may set precedence.
 * 
 * @author wgao9961
 * @version 1.0
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math;
/**
 * MathChars extend nodes and are specialized to do math. They can carry a function, a variable, a real number, or a basic operator.
 * @author wgao9961
 * @version 1.0
 */
public class MathChar extends Node<String>{
  //math things
  //like numbers, variables, operations, functions (n,v,o,f)
  private char type = '\0';
  //for functions operations variables only
  // private boolean neg = false; //on true, negate the output of this
  // private boolean recip = false; //on true, reciprocate the output of this
  //BUILD BUILD BUILD
  public MathChar() { //FOR PLACEHOLDER DEFAULTS
    //to make full use of placeholder defaults, use the node method setData() and set it to something no other node has
    super("tempID");
  }
    
  public MathChar(double n) { //NUMBERS ONLY
    super("" +  n);

    this.type = 'n';


  }
  public MathChar(char o) { //NUM OR OP
    super("" + o);
    if (o < 123 && o > 96) { //if its a letter
      this.type = 'v';
    } else {
      this.type = 'o';
    }
  }
  public MathChar(String f) { //FUNCTIONS: NOTE THAT AS OF SUBMITTING THIS, THIS CONSTRUCTOR IS NEVER TO BE USED
    super(f);
    this.type = 'f';
  }
  public char getType() {
    return this.type;
  }
  // public void negate() {
  //   this.neg = !this.neg;
  // }
  // public void reciprocate() {
  //   this.recip = !this.recip;
  // }
  //math
  // public void formPositiveOp() {
  //   if (this.type == 'o' && this.hasRight() && this.hasLeft()) {
  //     if (this.getData().equals("-")) {
  //       this.setData("+");
  //       switch (this.getRight().getType() ) {
  //         case('n'):
  //           this.getRight().setData(-1 * Double.parseDouble(this.getRight().getData()) + "" );
  //           break;
  //         default:
  //           this.getRight().negate();
  //           break;
  //       }
  //     }
  //     if (this.getData().equals("/")) {
  //       this.setData("*");
  //       switch (this.getRight().getType() ) {
  //         case('n'):
  //           this.getRight().setData(1 / Double.parseDouble(this.getRight().getData()) + "" );
  //           break;
  //         default:
  //           this.getRight().reciprocate();
  //           break;
  //       }
  //     }
  //   }
  // }
  /**
   * Find all descendants who are of a specific type. This can include null values.
   * @param type Accepts characters 'n','v','f','o', and '\0'.
   * @return An arraylist of all the descendant nodes that are of the specified type.
   */
  public ArrayList<MathChar> findAll(char type) {
    ArrayList<MathChar> someChars = new ArrayList<MathChar>();
    if (this.type == type) {
      someChars.add(this);
    }
    if (this.hasLeft()) {
      ArrayList<MathChar> lConts = this.getLeft().findAll(type);
      for (MathChar m : lConts) {
        someChars.add(m);
      }
    }
    if (this.hasRight()) {
      ArrayList<MathChar> rConts = this.getRight().findAll(type);
      for (MathChar m : rConts) {
        someChars.add(m);
      }
    }
    return someChars;
  }
  /**
   * Find all descendants who are of a specific type AND carry a specific value. This can include null values.
   * @param type Accepts characters 'n','v','f','o', and '\0'.
   * @param data Some string value to search for in descendant nodes.
   * @return An arraylist of all the descendant nodes that are of the specified type and carry the specified data.
   */
  public ArrayList<MathChar> findAll(char type, String data) {
    ArrayList<MathChar> someChars = new ArrayList<MathChar>();
    if (this.type == type && this.getData().equals(data)) {
      someChars.add(this);
    }
    if (this.hasLeft()) {
      ArrayList<MathChar> lConts = this.getLeft().findAll(type,data);
      for (MathChar m : lConts) {
        someChars.add(m);
      }
    }
    if (this.hasRight()) {
      ArrayList<MathChar> rConts = this.getRight().findAll(type,data);
      for (MathChar m : rConts) {
        someChars.add(m);
      }
    }
    return someChars;
  }
  // public boolean evaluate() {
  //   if (this.type == 'o' && this.getLeft().getType() == 'n' && this.getRight().getType() == 'n') {
  //     MathChar nChar;
  //     double d1 = Double.parseDouble(this.getLeft().getData());
  //     double d2 = Double.parseDouble(this.getRight().getData());
  //     if (this.getData().equals("/")) {
  //       nChar = new MathChar(d1 / d2 );
  //     } else if (this.getData().equals("*")) {
  //       nChar = new MathChar(d1 * d2);
  //     } else if (this.getData().equals("-")) {
  //       nChar = new MathChar(d1 - d2);
  //     } else if (this.getData().equals("+")) {
  //       nChar = new MathChar(d1 + d2);
  //     } else {
  //       return false;
  //     }
  //     this.replace(nChar);
  //     return true;
  //   }
  //   return false;
  // }
  /**
   * Solve for this node's value given variable values.
   * @param ruleset An arraylist carrying what each variable value should be
   * @return A Double with the numerical value of the solved expression.
   * @throws BadEquationException Throws an "Unidentified Character" upon finding reaching an unlisted operator or a variable with no designated value
   */
  public Double evaluate(HashMap<Character,Double> ruleset) throws BadEquationException{
    if (this.type == 'v') {
      Double toR = ruleset.get(this.getData().charAt(0));
      if (toR == null) throw new BadEquationException(1);
      return toR;
    }
    if (this.type == 'n') {
      return (Double.parseDouble(this.getData()));
    }

    if (this.type == 'o') {
    
      
      Double d1 = this.getLeft().evaluate(ruleset);
      Double d2 = this.getRight().evaluate(ruleset);
      if (this.getData().equals("/")) {
        return(d1 / d2 );
      } else if (this.getData().equals("*")) {
        return(d1 * d2);
      } else if (this.getData().equals("-")) {
        return(d1 - d2);
      } else if (this.getData().equals("+")) {
        return(d1 + d2);
      } else if (this.getData().equals("^")){
        return (Math.pow(d1,d2));
      }else {
        throw new BadEquationException(1);
      }
    }
    return 9999999d;

  }


  //override these
  //inheritance is wild
  public MathChar getParent() {
    if (super.hasParent()) return (MathChar) super.getParent();
      return null;
  }
  public MathChar getLeft() {
    if (super.hasLeft()) return (MathChar) super.getLeft();
    return null;
  }
  public MathChar getRight() {
    if (super.hasRight()) return (MathChar) super.getRight();
    return null;
  }
  /**
   * Checks if two nodes and its descendants carry the same data in the same location.
   * @param n Some MathChar to check against.
   * @return A boolean describing their equality.
   */
  public boolean deepEquals(MathChar n) {
    boolean q = this.equals(n);
    try {
      boolean l = true;
      boolean r = true;
      if (this.hasLeft() || n.hasLeft()) l = this.getLeft().deepEquals(n.getLeft());
      if (this.hasRight() || n.hasRight()) r = this.getRight().deepEquals(n.getRight());
      return (q && l && r);
    } catch (NullPointerException e) {
      return false;
    }
  }
  
  // public static char typeof(String s) {
  //   try {
  //     double val = Double.parseDouble(s);
  //     //if it doesnt get exceptioned here, its a number so
  //     return 'n';
  //   } catch (NumberFormatException e){
  //     //NaN
      
  //   }
  // }
}