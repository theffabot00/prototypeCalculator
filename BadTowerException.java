
/**
 * The BadTowerException class exists solely to signal that the Tower constructor has built an unbalanced tower.
 * @author wgao9961
 */
public class BadTowerException extends Exception {
    public static final long serialVersionUID = 1L;
    public final int code;
    public static final String[] errMsg = new String[]{"Unbalanced Delimiters"};
    public BadTowerException(int n) {
        super(errMsg[n]);
        this.code = n;
    }
}
