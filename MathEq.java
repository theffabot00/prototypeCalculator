import java.util.HashMap;
import java.util.ArrayList;
import java.lang.StringBuffer;
import java.util.regex.*;
import java.util.Arrays;

/**
 * The MathEq class exists to store a single equation and do work on the equation it has. The equation is stored in the Tree<String> it extends.
 * @author wgao9961
 */
public class MathEq extends Tree<String> {
    //this is just here for printing purposes
    private String equation;
    /**
     * Default constructor; creates a y=x when there is no other equation given
     */
    public MathEq() {
        super(new MathChar('='));
        this.equation = "y=x";
        try { //still gotta try it even though i know it works
            parseEquation(this.equation);
            refreshNodes();
        } catch (BadEquationException w) { //if this runs im dead
            System.out.println("the end has come");
            System.exit(1);
        }
    }
    /**
     * Constructs a MathEq given a string. 
     * @param eq A string that can be understood by humans as an equation. Currently only accepts y= equations.
     */
    public MathEq(String eq) {
        super(new MathChar('='));
        this.equation = breakNonOp(eq);
        try {
            parseEquation(this.equation);
            refreshNodes();
        } catch (BadEquationException e) {
            System.out.println("FAILED PARSE: " + BadEquationException.errMsg[e.code]);
            System.out.println("EVALUATED TO: y = x");
            this.equation = "y=x";
            try { //still gotta try it even though i know it works
                parseEquation(this.equation);
                refreshNodes();
            } catch (BadEquationException w) { //if this runs im dead
                System.out.println("the end has come");
                System.exit(1);
            }
            
        }
        
    }
    /**
     * "overrides" the default getRoot because it returns type Node<String> and not MathChar.
     * @return The MathChar root for the equation. 
     */
    public MathChar getRoot() {
        return (MathChar)super.getRoot();
    }
    /**
     * Exists to parse the right hand side. The left hand side, as of writing this, only holds a y. 
     * @param eq A string that can be understood by humans as an equation. Currently only accepts y= equations.
     * @throws BadEquationException
     */
    public void parseEquation(String eq) throws BadEquationException{
        //what does hell look like?
        //at this current phase, we only concern ourselves with **rational equations**
        try {
            String expr1 = eq.substring(0,eq.indexOf("="));
            String expr2 = eq.substring(eq.indexOf("=")+1);
            (this.getRoot()).attachLeft(parseExpr(expr1));
            (this.getRoot()).attachRight(parseExpr(expr2));
        } catch (StringIndexOutOfBoundsException e) {
            throw new BadEquationException(0);
        } 
    }
    /**
     * Exists to parse an expression. It returns a node with its descendants being the rest of the expression. The expression is solved from the bottom up.
     * @param ex Some mathematical expression.
     * @return A MathChar that carries the entire expression.
     * @throws BadEquationException
     */
    public MathChar parseExpr(String ex) throws BadEquationException {
        //find the parentheses
        //we can evaluate the parenteeses as their own expressions
        if (ex.indexOf("=") != -1) throw new BadEquationException(3);
        if (ex.equals("")) throw new BadEquationException(0);
        ex = ex.replaceAll("\\s+",""); // thx stackoverflow
        try {
            //keys are stored in floor.code so we can attach it to its end
            //the master head is stored as 0.0
            HashMap<String,MathChar> heads = new HashMap<String,MathChar>();
            ArrayList<MathChar> tails = new ArrayList<MathChar>();
            Tower expr = new Tower(ex,"(",")");
            for(int n = 0; n != expr.size(); n++) { //each floor gets its own tree(s)
                String[] rooms = expr.getFloor(n);
                for (int q = 0 ;q != rooms.length; q++) {
                    MathChar someHead = breakByOperator(rooms[q], n);
                    heads.put(n + "." + q, someHead);
                    ArrayList<MathChar> u = someHead.findAll('\0');
                    
                    for (MathChar v : u) {
                        tails.add(v);
                    }
                }

            }
            
            for (MathChar n : tails) {
                n.replace(heads.get(n.getData()));
            }
            return heads.get("0.0");
            
        } catch (BadTowerException e) {
            throw new BadEquationException(2);
        }


    }
    /**
     * Generic function that breaks up the equation at each basic operator (+,-,*,/).
     * @param eq Some mathematical expression..
     * @return A MathChar of the expression.
     */
    public static MathChar breakByOperator(String eq) {
        return breakByOperator(eq, 0);
    }
    //i see no reason why i should make these two public, so private they are!
    //TAKE NOTE: THE STRING EQ IS GOING TO BE A FLOOR OF A TOWER SO WE WILL ***NEVER*** SEE SOMETHING LIKE X(Y+Z)
    /**
     * A more specific function 
     * @param eq Some mathematical expression.
     * @param floor An integer specifyign how many times the expression has been nested. Used largely for organizing purposes when connecting the nested expressions to the entire expression.
     * @return A MathChar of the expression.
     */
    public static MathChar breakByOperator(String eq, int floor) {
        //simple enough, we recursively go through the string, each function call breaking another operator
        if (eq.equals("")) eq = "0";
        int ofM = -1;// bad variable names, its the index for multiplication 
        int ofD = -1;
        int ofA = -1; //same as top, but adding
        int ofS = -1;
        int ofC = -1; //caret
        for (int n = 0 ;n != eq.length(); n++) {
            char q = eq.charAt(n);
            //no particular order
            if ( ofM == -1 && (q =='*') ){
                ofM = n;
            } else if( ofA == -1 && (q =='+') ) {
                ofA = n;
            } else if (ofD == -1 && q == '/') {
              ofD = n;
            } else if (ofS == -1 && q == '-') {
                ofS = n;
            } else if (ofC == -1 && q == '^') {
                ofC = n;
            }
        }
        MathChar toR;
        if (ofA != -1) { //break on addition
            toR = new MathChar(eq.charAt(ofA));
            MathChar lSide = breakByOperator(eq.substring(0, ofA), floor);
            MathChar rSide = breakByOperator(eq.substring(ofA+1), floor);
            toR.attachLeft(lSide);
            toR.attachRight(rSide);
        } else if(ofS != -1) {
          toR = new MathChar(eq.charAt(ofS));
            MathChar lSide = breakByOperator(eq.substring(0, ofS), floor);
            MathChar rSide = breakByOperator(eq.substring(ofS+1), floor);
            toR.attachLeft(lSide);
            toR.attachRight(rSide);
        }else if (ofM != -1) { //break on multiply
            toR = new MathChar(eq.charAt(ofM));
            MathChar lSide = breakByOperator(eq.substring(0, ofM), floor);
            MathChar rSide = breakByOperator(eq.substring(ofM+1), floor);
            toR.attachLeft(lSide);
            toR.attachRight(rSide);
        }else if (ofD != -1) {
            toR = new MathChar(eq.charAt(ofD));
            MathChar lSide = breakByOperator(eq.substring(0, ofD), floor);
            MathChar rSide = breakByOperator(eq.substring(ofD+1), floor);
            toR.attachLeft(lSide);
            toR.attachRight(rSide);
        } else if (ofC != -1) {
            toR = new MathChar(eq.charAt(ofC));
            MathChar lSide = breakByOperator(eq.substring(0, ofC), floor);
            MathChar rSide = breakByOperator(eq.substring(ofC+1), floor);
            toR.attachLeft(lSide);
            toR.attachRight(rSide);
        }else { //this is run only when theres nothing obvious to break (aka, no more operators) (like x, or 6, OR ##[0]##)
            try {
                double someD = Double.parseDouble(eq);
                toR = new MathChar(someD); //must be number
            } catch (NumberFormatException e) {
                //is it a parenthesesd item?
                if (eq.charAt(0) == '#') {
                    toR = new MathChar(); //must be a staircase
                    toR.setData(floor+1 + "." + eq.substring(3,eq.length()-3) ); //give the nest an ID based on nest layer and nest number in that layer
                } else {
                    toR = new MathChar(eq.charAt(0));
                }
                
            }
        }
        return toR;
    }
    /**
     * Breaks up the equation where multiplication is implied (e.g. 5x, x5, (5)(6), 5(6), etc.)
     * @param eq Some equation.
     * @return The equation with no implied operations.
     */
    private static String breakNonOp(String eq) {


        StringBuffer n = new StringBuffer(eq + "   ");
        //in order, they are
        //9(6)
        //9x
        //xy **note that this one is risky and might break functions if i ever implement them
        //x(7)
        //x7
        //(7)(8)
        //(7)x
        //(7)6
        //-(
        //-x
        String[] patterns1 = new String[]{"\\d\\(", "\\d[a-z]", "[a-z][a-z]", "[a-z]\\(","[a-z]\\d","\\)\\(","\\)[a-z]","\\)\\d", "-\\(","-[a-z]" };
        // String[] patterns2 = new String[]{"####","##[a-z]", "##\\d" };
        for (String p : patterns1) {
            Pattern pat = Pattern.compile(p);
            Matcher m = pat.matcher(n);
            
            //if you can find it x times in m, you can find it x times in q
            while (m.find()) {
                if (n.charAt(m.start()) == '-') {
                    n.insert(m.start()+1, "1*");
                } else {
                    n.insert(m.start()+1, '*');
                }
                

            }
        }
        return n.toString();
    }
    /**
     * @return An arraylsit of MathChars in this MathEq.
     */
    public ArrayList<MathChar> getCharList() {
        ArrayList<Node<String>> s = super.getNodeList();
        ArrayList<MathChar> toR = new ArrayList<MathChar>();
        for (Node<String> q : s) {
            toR.add((MathChar)q);
        }
        return toR;
    }
    /**
     * Checks if two equations are the <b> exact </b> same. 
     * @param e An equation to check against
     * @return a boolean describing their equality.
     */
    public boolean deepEquals(MathEq e) {
        return (e.getRoot()).deepEquals(this.getRoot());
    }
    /**
     * Solves the right hand side of the equation for a specific value of x.
     * @param x some double value to solve the equation at.
     * @return the value of the left hand side at the given x value.
     */
    public Double evaluate(double x) {

        HashMap<Character, Double> rs = new HashMap<Character, Double>();
        rs.put('x',x);
        rs.put('e',2.718); //proof of concept: custom variables and giving custom variables values; could be expanded to make custom "functions" (whats a fuction vs whats a variable is murky to me) (like q=3x doesnt look like a function, but i guess q(x) = 3x is (??))
        try {
            return this.getRoot().getRight().evaluate(rs); //this is mathchar evaluate, not matheq evaluate
        } catch (BadEquationException e) {
            System.out.println(BadEquationException.errMsg[e.code]);
            return null;
        }
    }
    /**
     * Default toString method; it returns itself and all of its descendants in a readable tree format.
     * @return This and its descendants in a readable format.
     */
    public String toString() {
        return this.getRoot().toString();
    }
    /**
     * @return The equation the user originally input, but with all cases of implied multiplication explicitly written out.
     */
    public String toHumanReadableString() {
        return this.equation;
    }
}