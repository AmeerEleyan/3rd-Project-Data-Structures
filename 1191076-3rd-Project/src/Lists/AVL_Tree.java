/**
 * @autor: Amir Eleyan
 * ID: 1191076
 * At: 2/5/2021  3:20 AM
 */
package Lists;

public class AVL_Tree<T extends Comparable<T>> {
    /**
     * Attribute
     */
    private TNode<T> root;
    private int size = 0;

    // return the root of this tree
    public TNode<T> getRoot() {
        return root;
    }

    // set new root for this tree
    public void setRoot(TNode<T> root) {
        this.root = root;
    }

    // check status of the tree, is full or not
    public boolean isEmpty() {
        return this.root == null;
    }

    // Clear data in the tree
    public void clear() {
        this.root = null;
    }

    // return the size of the tree
    public int size() {
        return this.size;
    }

    // return the height of this tree
    public int height() {
        return this.height(this.root);
    }

    /**
     * To search a specific element in the tree and return it
     * O(log n)
     */
    public T search(T data) {
        TNode<T> searcher = this.root;
        while (searcher != null) {
            int compare = data.compareTo(searcher.getDate());
            if (compare == 0) return searcher.getDate();
            else if (compare > 0) searcher = searcher.getRight();
            else searcher = searcher.getLeft();
        }
        return null;
    }

    /**
     * To get the maximum element in the tree
     * O(log n)
     */
    public T maximum() {
        TNode<T> node = this.root;
        while (node != null) {
            if (node.hasRight()) node = node.getRight();
            else return node.getDate();
        }
        return null;
    }

    /**
     * To get the minimum element in the tree
     * O(log n)
     */
    public T minimum() {
        TNode<T> node = this.root;
        while (node != null) {
            if (node.hasLeft()) node = node.getLeft();
            else return node.getDate();
        }
        return null;
    }

    // left-left addition)
    private TNode<T> rotateRight(TNode<T> tNode) {
        TNode<T> leftChild = tNode.getLeft();
        tNode.setLeft(leftChild.getRight());
        leftChild.setRight(tNode);
        return leftChild;
    }

    // right-right addition
    private TNode<T> rotateLeft(TNode<T> tNode) {
        TNode<T> rightChild = tNode.getRight();
        tNode.setRight(rightChild.getLeft());
        rightChild.setLeft(tNode);
        return rightChild;
    }

    // right-left addition
    private TNode<T> rotateRightLeft(TNode<T> tNode) {
        tNode.setRight(this.rotateRight(tNode.getRight()));
        return this.rotateLeft(tNode);
    }

    // left-right addition
    private TNode<T> rotateLeftRight(TNode<T> tNode) {
        tNode.setLeft(this.rotateLeft(tNode.getLeft()));
        return this.rotateRight(tNode);
    }

    /**
     * height of subtree
     * O(n)
     */
    private int height(TNode<T> TNode) {
        if (TNode == null) return 0;
        int left = height(TNode.getLeft());
        int right = height(TNode.getRight());
        return (left > right) ? (left + 1) : (right + 1);
    }

    // left - right
    private int heightDifference(TNode<T> tNode) {
        int left = this.height(tNode.getLeft());
        int right = this.height(tNode.getRight());
        return left - right;
    }

    /**
     * Set sub tree rebalance
     * O(n)
     */
    private TNode<T> rebalance(TNode<T> TNode) {
        int left_difference_right = this.heightDifference(TNode);
        if (left_difference_right > 1) {// addition was in node's left subtree

            if (heightDifference(TNode.getLeft()) > 0) // left-left-addition
                TNode = this.rotateRight(TNode);
            else // left-right-addition
                TNode = this.rotateLeftRight(TNode);

        } else if (left_difference_right < -1) { // addition was in node's right subtree

            if (heightDifference(TNode.getRight()) < 0) { // right-right-addition
                TNode = this.rotateLeft(TNode);
            } else { // right-left-addition
                TNode = this.rotateRightLeft(TNode);
            }
        }
        return TNode;
    }

    /**
     * Insert new element to the tree and set tree rebalance
     * O(n) ...O(n)+O(log n)
     */
    public void insert(T data) {
        if (isEmpty()) {
            this.root = new TNode<>(data);
        } else {
            TNode<T> tempRoot = this.root;
            this.add(data, tempRoot);
            this.root = this.rebalance(tempRoot);
            this.size++;
        }
    }

    // Add new element to the tree recursively and set tree rebalance
    private void add(T data, TNode<T> root) {
        if (data.compareTo(root.getDate()) < 0) { // element less than current (add to left)
            if (!root.hasLeft()) { // current does not have left child
                root.setLeft(new TNode<>(data));
            } else {// current has left child
                TNode<T> leftChild = root.getLeft();
                add(data, leftChild);//O(log n)
                root.setLeft(rebalance(leftChild));//O(1)
            }

        } else if (data.compareTo(root.getDate()) > 0) { // element larger then current (add to right)
            if (!root.hasRight()) { // current does not have right child
                root.setRight(new TNode<>(data));
            } else {// current has right child
                TNode<T> rightChild = root.getRight();
                add(data, rightChild);//O(log n)
                root.setRight(rebalance(rightChild));//O(1)
            }
        }
    }

    /**
     * Delete a specific element if exist
     */
    public T delete(T data) {
        TNode<T> temp = remove(data, this.root);
        if (temp != null) {
            TNode<T> rootTNode = root;
            root = rebalance(rootTNode);
            this.size--;
            return temp.getDate();
        }
        return null;
    }

    // Return and remove specific element
    private TNode<T> remove(T data, TNode<T> root) {
        TNode<T> parent = root;
        TNode<T> current = root;
        boolean isLeft = false;
        int compare;

        // No nodes in the tree
        if (isEmpty()) return null;

        // to search for the element
        while (current != null) {

            if (current.getDate().equals(data)) break;
            parent = current;
            compare = current.getDate().compareTo(data);

            if (compare > 0) { // data in the left of the current
                current = current.getLeft();
                isLeft = true;
            } else { // data in the right of the current
                current = current.getRight();
                isLeft = false;
            }
        }

        // element not found
        if (current == null) return null;

        // element found and does not have left and right child
        if (!current.hasRight() && !current.hasLeft()) {

            // data in the root of the tree
            if (current == this.root) this.root = null;
            else if (isLeft) parent.setLeft(null);// // element in the left of the parent
            else parent.setRight(null); // element in the right of the parent

        } else if (current.hasLeft() && !current.hasRight()) { // element has an only left child

            // data in the root and have only left child
            if (current == root) this.root = current.getLeft();
            else if (isLeft) parent.setLeft(current.getLeft());// // element in the left of the parent
            else parent.setRight(current.getLeft()); // element in the right of the parent

        } else if (!current.hasLeft() && current.hasRight()) { // element has an only right child

            // element in the root
            if (current == root) this.root = current.getRight();
            else if (isLeft) parent.setLeft(current.getRight()); // element in the left of the parent
            else parent.setRight(current.getRight()); // element in the right of the parent

        } else { // element has two child

            TNode<T> successor = this.getSuccessor(current);
            if (current == this.root) this.root = successor; // element in the root
            else if (isLeft) parent.setLeft(successor); // element in the left of the parent
            else parent.setRight(successor);// element in the right of the parent

            successor.setLeft(current.getLeft());
        }
        return current;
    }


    // Get the minimum starting from the right of the current node
    private TNode<T> getSuccessor(TNode<T> node) {
        TNode<T> parentOfSuccessor = node;
        TNode<T> successor = node;
        TNode<T> current = node.getRight();
        while (current != null) {
            parentOfSuccessor = successor;
            successor = current;
            current = current.getLeft();
        }
        if (successor != node.getRight()) {
            parentOfSuccessor.setLeft(successor.getRight());
            successor.setRight(node.getRight());
        }
        return successor;
    }

    /**
     * Display Tree level by level
     */
    public void traverseLevelOrder() {
        LinkedQueue<TNode<T>> linkedQueue = new LinkedQueue<>();
        LinkedQueue<T> level = new LinkedQueue<>();
        traverseLevelOrder(this.root, linkedQueue, level);
        System.out.println(level);
    }

    // traverse tree level by level using queue
    private void traverseLevelOrder(TNode<T> rootOfTree, LinkedQueue<TNode<T>> tempQueue, LinkedQueue<T> level) {
        // insert the  root of the tree
        if (rootOfTree != null) {

            tempQueue.enqueue(rootOfTree);
            while (!tempQueue.isEmpty()) {
                // store first element in the queue and remove it
                TNode<T> tempNode = tempQueue.dequeue();

                // insert first element that was stored to level queue
                level.enqueue(tempNode.getDate());

                // insert first.left to tempQueue
                if (tempNode.getLeft() != null) {
                    tempQueue.enqueue(tempNode.getLeft());
                }

                // insert first.right to tempQueue
                if (tempNode.getRight() != null) {
                    tempQueue.enqueue(tempNode.getRight());
                }
            }
        }
    }
}

