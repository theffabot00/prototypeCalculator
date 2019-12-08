import java.util.ArrayList;
import java.util.HashMap;


public class Tree<T> {
  private ArrayList<Node<T>> nodes = new ArrayList<Node<T>>();
  //no root game! just make nodes.get(0)
  /**
   * Construct a tree. Must be initialized with a root node.
   * @param n The root node.
   */
  public Tree(Node<T> n) {
    nodes.add(0,n);
  }
  /**
   * Update the arraylist of nodes.
   */
  public void refreshNodes() {
    Node<T> root = nodes.get(0); 
    nodes = new ArrayList<Node<T>>();
    reloadNode(root);
  }
  /**
   * Helper function for refreshNodes(); adds a given node and its descendants to the arraylist of nodes.
   * @param n A given node to add.
   */
  private void reloadNode(Node<T> n) {
    this.nodes.add(n);
    if (n.hasLeft()) reloadNode(n.getLeft());
    if (n.hasRight()) reloadNode(n.getRight());
  }
  /**
   * Largely unused; checks if a node exists at a given address 
   * @param s An address (starts at root and is written as a chain if ls and rs (lefts and rights) from root)
   * @return A boolean that tells whether or not the node exists.
   */
  public boolean hasNodeAt(String s) {
    Node<T> focus = nodes.get(0);
    for (int n = 0; n != s.length(); n++) {
      if (s.charAt(n) == 'l') {
        if (focus.hasLeft()) {
          focus = focus.getLeft();
        } else {
          return false;
        }
      } else {
        if (focus.hasRight()) {
          focus = focus.getRight();
        } else {
          return false;
        }
      }
    }
    return true;
  }
  //returns a map of path, value
//  public HashMap<String,String> getMap() {
//  }
//  private String getNodeContents(Node<T> n) {
//  }
  /**
   * @return The arraylist of nodes in this tree.
   */
  public ArrayList<Node<T>> getNodeList() {
    return this.nodes;
  }
  /**
   * @return The first node in this tree (usually root).
   */
  public Node<T> getRoot() {
    return nodes.get(0);
  }
  /**
   * @return The tree in a readable format.
   */
  public String toString() {
    return (nodes.get(0)).toString(0);
  }
}
    
    