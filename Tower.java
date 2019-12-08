import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Tower class offers a method for reading strings with information nested in them. This class is relevant when the nesting order matters (ex: parentheses in an equation). It splits a string into many segments resembling a tree.
 * @author wgao9961
 */ 
public class Tower {
    private final String delim1;
    private final String delim2;
    private ArrayList<String[]> tow = new ArrayList<String[]>();

    /**
     * Initializes a tower with the given information
     * @param s A string to parse through
     * @param delim1 A string that denotes the beginning of a nest. 
     * @param delim2 A string that denotes the end of a nest.
     * @exception BadTowerException Throws a "Unbalanced Delimiters" when the constructor ends at a nest level inequivalent to the starting nest level.
     */ 
    public Tower (String s, String delim1, String delim2) throws BadTowerException {
        int highestFloor = 0;
        int cFloor = 0;
        this.delim1 = delim1;
        this.delim2 = delim2;

        int lDelim = 0;
        int rDelim = 0;

        for (int n = 0; n != s.length() - delim2.length()+1; n++) {
            if (s.substring(n,n+delim1.length()).equals(delim1)) {
                lDelim++;
                cFloor++;
                if (cFloor > highestFloor) highestFloor++;
            } else if (s.substring(n,n+delim2.length()).equals(delim2)) {
                rDelim++;
                cFloor--;
            } 
        }
        if (lDelim != rDelim) {
            throw new BadTowerException(0);
        }
        for (int n = 0; n != highestFloor + 1; n++) {
            String[] someFloor = parseTower(s,n);
            tow.add(n,someFloor);
        }
        clearEmpty();
    }
    /**
     * @param n A nest level
     * @return An array of expressions that sit at the specified nest level.
     */
    public String[] getFloor(int n) {
        return this.tow.get(n);
    }
    /**
     * @return This tower's relevant data as an arraylist of string arrays
     */
    public ArrayList<String[]> getTower() {
        return this.tow;
    }
    /**
     * @return The number of nest layers.
     */
    public int size() {
        return this.tow.size();
    }
    /**
     * Remove all "empty floors/layers" (e.g. a floor with nothing but one connection is empty)
     */
    private void clearEmpty() {
        for (int n = 0 ;n < this.tow.size(); n++) {
            if (this.tow.get(n).length == 1 && this.tow.get(n)[0].equals("##[0]##") ) {
                this.tow.remove(n);
            }
        }
    }
    /**
     * Traverses the string and extracts portions that are of a specified nest level.
     * @param s A string to traverse.
     * @param floor A nest level to track.
     * @return An array of strings that belong to that nest level.
     */
    private String[] parseTower(String s, int floor) {
        int cFloor = 0;
        ArrayList<String> tempFloor = new ArrayList<String>();
        String temp = "";
        int pathNum = 0;
        for (int n = 0; n != s.length() - delim2.length() + 1; n++) {
            if (s.substring(n,n+delim1.length()).equals(delim1)) {
                cFloor++;
                if (cFloor == floor + 1) {
                    temp += "##[" + pathNum + "]##";
                    pathNum++;
                }
            } else if (s.substring(n,n+delim2.length()).equals(delim2)) {
                cFloor--;
                if (cFloor == floor - 1) {
                    tempFloor.add(temp);
                    temp = "";
                }
            } else {
                if (floor == cFloor) {
                    temp += s.charAt(n);
                } 
            }

            if (floor == 0 && n == s.length() - delim2.length() ) {
                tempFloor.add(temp);
            }

        }
        Object[] tem = tempFloor.toArray();
        String[] toR = new String[tem.length];
        for (int n = 0; n != tem.length; n++) {
            toR[n] = (String) tem[n];
        }
        return toR;
    }
    /**
     * @return A William-readable ASCII diagram of the string.
     */
    public String toString() {
        String nStr = "";
        for (int n =0 ; n != tow.size(); n++) {
            nStr += "FLOOR NUMBER " + n + ": " + Arrays.toString(tow.get(n));
            nStr += "\n";
        }
        return nStr;
    }


}

