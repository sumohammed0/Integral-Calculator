public class Node <Type extends Comparable<Type>> implements Comparable <Node<Type>>{
    private Type obj = null;
    private Node<Type> left = null;
    private Node<Type> right = null;
    
    public void setObject (Type t) {obj = t;}
    public Type getObject () {return obj;}
    
    public void setLeft (Node<Type> l) {left = l;}
    public Node<Type> getLeft () {return left;}
    
    public void setRight (Node<Type> r) {right = r;}
    public Node<Type> getRight () {return right;}
    
    @Override
    public int compareTo(Node<Type> other) {
       return (obj.compareTo(other.getObject()));
   }
   
   @Override
    public String toString() {
        return (this.obj).toString();
    }
    
}