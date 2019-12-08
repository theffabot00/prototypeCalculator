
/**
 * Node objects are basic carriers of information. They can store up to one value of any type and can be chained into a tree.Parents and descendants of Node type T must also be of type T.
 * @param <T> Any object with a meaningful toString() method
 * @author wgao9961
 */
public class Node<T> {
  private T data;
  private Node<T> parent;
  private Node<T> lNode;
  private Node<T> rNode;
  /**
   * Create an empty node
   */
  public Node() {
  }
  /**
   * Create a node and immediately load it with some data.
   * @param dat Some object of type T
   */
  public Node(T dat) {
    this.data = dat;
  }
  
  /**
   * @return a boolean indicating whether or not it has a left child.
   */
  public boolean hasLeft() {
    return lNode != null;
  }
  /**
   * @return a boolean indicating whether or not it has a right child.
   */
  public boolean hasRight() {
    return rNode != null;
  }
  /**
   * @return a boolean indicating whether or not it has a parent.
   */
  public boolean hasParent() {
    return parent != null;
  }
  /**
   * @return The data stored in the node. It returns the object itself.
   */
  public T getData() {
    return data;
  }
  /**
   * @return The parent node if it exists. If it doesnt, return null.
   */
  public Node<T> getParent() {
    if (this.hasParent()) return parent;
      return null;
  }
  /**
   * @return The left node if it exists. If it doesnt, return null.
   */
  public Node<T> getLeft() {
    if (this.hasLeft()) return lNode;
    return null;
  }
  /**
   * @return The right node if it exists. If it doesnt, return null.
   */
  public Node<T> getRight() {
    if (this.hasRight()) return rNode;
    return null;
  }
//  public String getPath() {
//  }
//  public String getPath(String addToEnd) {
//    String path = addToEnd;
//    if (this.hasParent()) {
//      
//  }
  /**
   * @param dat Set the data within this node to dat.
   */
  public void setData(T dat) {
    this.data = dat;
  }
  /**
   * The method that should not be used frequently outside of this class unless in conjunction with the attachLeft or attachRight method. Sets this node's parent to another.
   * @param n The node to make this node's parent.
   * @param force A boolean that dictates whether or not the setting should overwrite preexisting relationships.
   * @return A boolean that tells whether or not the setting was successful.
   */
  public boolean setParent(Node<T> n, boolean force) {
    if (this.parent == null || force) {
      this.parent = n;
      return true;
    } else {
      return false;
    }
  }
  /**
   * A method that should be used very frequently.
   * @param n The node to attach to this node's left.
   * @param force A boolean dictating whether or not the attaching should overwrite preexisting relationships.
   * @return A boolean that tells whether or not the attaching was successful.
   */
  public boolean attachLeft(Node<T> n, boolean force) {
    if (force) {
      this.lNode = n;
      n.setParent(this, force);
      return true;
    } else {
      return attachLeft(n);
    }
  }
  /**
   * A method that does not allow forcing relationships.
   * @param n Node to attach to the left.
   * @return A boolean that tells whether or not the attaching was successful.
   */
  public boolean attachLeft(Node<T> n) {
    if (this.hasLeft()) {
      return false;
    } else {
      this.lNode = n;
      n.setParent(this, false);
      return true;
    }
  }
  /**
   * A method that should be used very frequently.
   * @param n The node to attach to this node's right.
   * @param force A boolean dictating whether or not the attaching should overwrite preexisting relationships.
   * @return A boolean that tells whether or not the attaching was successful.
   */
  public boolean attachRight(Node<T> n, boolean force) {
    if (force) {
      this.rNode = n;
      n.setParent(this, force);
      return true;
    } else {
      return attachRight(n);
    }
  }
  /**
   * A method that does not allow forcing relationships.
   * @param n Node to attach to the right.
   * @return A boolean that tells whether or not the attaching was successful.
   */
  public boolean attachRight(Node<T> n) {
    if (this.hasRight()) {
      return false;
    } else {
      this.rNode = n;
      n.setParent(this, false);
      return true;
    }
  }
  /**
   * Replace THIS node and ALL of THIS node's descendants with another node and its descendants.
   * @param n The node to that THIS node's place.
   */
  public void replace(Node<T> n) { //this is a violent method that forcibly removes a node and all of its descendants and replaces it with itself and its descendants
    //its the genghis khan of node methods
    if (this.getParent() == null) {
      System.out.println("ERR: Invoker of this method is a grand dumbass"); //Error case that is unlikely to happen in this project
    }
    if (this.getParent().getLeft() == this) {
      this.getParent().attachLeft(n, true);

    }
    if (this.getParent().getRight() == this) {
      this.getParent().attachRight(n, true);

    }
  }
  /**
   * Default toString method; it returns itself and all of its descendants in a readable tree format.
   * @return This and its descendants in a readable format.
   */
  public String toString() {
    return this.toString(0);
  }
  /**
   * More specific way of printing the tree; it allows for flexiblity in how far right the tree should start. 
   * @param order The number of ancestors it has above it. 
   * @return This and its descendants in a readable format.
   */
  public String toString(int order) {
    String s = "";
    for (int n = 0 ;n != order; n++) {
      s += "| ";
    }
    s += this.data.toString();
    s += "\n";
    if (this.hasLeft() ) s += this.lNode.toString(order + 1); 
    if (this.hasRight()) s += this.rNode.toString(order + 1);
    
    return s;
  }
  /**
   * Check if two nodes are equal by checking their contents. 
   * @param n Another node to check.
   * @return A boolean
   */
  public boolean equals(Node<T> n) {
    return ( n.getData().equals(this.getData()));
  }
}
  