
/**
 * The BadEquationException signals when a parsing error has occured while parsing an equation.
 * @author wgao9961
 */
public class BadEquationException extends Exception {
    public static final long serialVersionUID = 1L;
    public final int code;
    public static final String[] errMsg = new String[]{"Not Equation", "Unrecognized character", "Unbalanced parentheses","Too Mant Equal Signs"};
    public BadEquationException(int n) {
        super(errMsg[n]);
        this.code = n;
    }
}