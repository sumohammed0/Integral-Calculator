public class BinTree <Type extends Comparable<Type>> {
    private Node<Type> root = null;
    
    public void setRoot (Node<Type> r) {
        root = r;
    }
    public Node<Type> getRoot () {return root;}
    
    public void insert(Node<Type> n) {
        if (root == null) {
            root = n;
        }
        else insertRecursive(root, n);
    }
    
    public void insertRecursive(Node<Type> parent, Node<Type> nodeToInsert) {
        // if the node's term is less than the root put it on the left
        if (nodeToInsert.compareTo(parent) < 0) {
            if (parent.getLeft() == null) {
                parent.setLeft(nodeToInsert);
            }
            else {
                insertRecursive(parent.getLeft(), nodeToInsert);
            }
        }
        // if the node's term is less than the root put it on the right
        else {
            if (parent.getRight() == null) {
                parent.setRight(nodeToInsert);
            }
            else {
                insertRecursive(parent.getRight(), nodeToInsert);
            }
        }
    }
    
    public void print(Node<Type> n /*, int up, int low*/) {
        if (n == null) {
            return;
        } 
        
        print(n.getRight());
        
        System.out.print(n.toString());
        
        print(n.getLeft());
    }
    
    public Node<Type> search(Node<Type> n) {
        return searchRecursive(this.root, n);
    }
    
    public Node<Type> searchRecursive(Node<Type> node, Node<Type> value) {
        if (node != null) {
            if (value.compareTo(node) == 0) {
                return node;
            }
            else if (value.compareTo(node) < 0) {
                return searchRecursive(node.getLeft(), value);
            }
            else {
                return searchRecursive(node.getRight(), value);
            }
        }
        
        return null;
    }
    
    public Node<Type> getParent(Node<Type> n) {
        return getParentRecursive(this.root, n);
    }
    
    public Node<Type> getParentRecursive(Node<Type> subtreeRoot, Node<Type> n) {
        if (subtreeRoot == null) {
            return null;
        }
        
        if (subtreeRoot.getLeft() == n || subtreeRoot.getRight() == n) {
            return subtreeRoot;
        }
        
        if (n.compareTo(subtreeRoot) < 0) {
            return getParentRecursive(subtreeRoot.getLeft(), n);
        }
        
        return getParentRecursive(subtreeRoot.getRight(), n);
        
    }
    
    
    public void remove(Node<Type> n) {
        Node<Type> node = this.search(n);
        Node<Type> parent = this.getParent(n);
        
    }
    
    public boolean removeNode(Node<Type> parent, Node<Type> n) {
        if (n == null) {
            return false;
        }
        
        // Case 1: Internal node with 2 children
        if (n.getLeft() != null && n.getRight() != null) {
            Node<Type> succNode = n.getRight();
            Node<Type> successorParent = n;
            
            while (succNode.getLeft() != null) {
                successorParent = succNode;
                succNode = succNode.getLeft();
            }
            
            n = succNode;
            
            removeNode(successorParent, succNode);
        }
        
        // Case 2: Root node (with 1 or 0 children)
        else if (n == this.root) {
            if (n.getLeft() != null) {
                this.root = n.getLeft();
            }
            else {
                this.root = n.getRight();
            }
        }
        
        // Case 3: Internal with left child only
        else if (n.getLeft() != null) {
            // Replace node with node's left child
            if (parent.getLeft() == n) {
                parent.setLeft(n.getLeft());
            }
            else {
                parent.setRight(n.getLeft());
            }
        }
        
        // Case 4: Internal with right child only OR leaf
        else {
            // Replace node with node's right child
            if (parent.getLeft() == n) {
                parent.setLeft(n.getRight());
            }
            else {
                parent.setRight(n.getRight());
            }
        }     
        
        return true;
    }
}