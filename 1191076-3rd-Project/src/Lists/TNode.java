/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 15/4/2021  3:44 AM
 */
package Lists;

import Project.Frequency;

public class TNode<T extends Comparable<T>> implements Comparable<TNode<T>> {

    /**
     * Attribute
     */
    private T date;
    private TNode<T> left, right;
    private final LinkedList<Frequency> frequencyLinkedList = new LinkedList<>();

    /**
     * No argument constructor
     */
    public TNode() {
    }

    /**
     * constructor with specific date
     */
    public TNode(T date) {
        this.date = date;
    }

    /**
     * Add new frequency obj tp linked list to prevent duplication in the tree
     */
    public void insertToLinkedList(Frequency frequency) {
        this.frequencyLinkedList.addBySort(frequency);
    }

    /**
     * Return the date in this node
     */
    public T getDate() {
        return date;
    }

    /**
     * set a new data fort this data
     */
    public void setDate(T date) {
        this.date = date;
    }

    /**
     * return the left of this node
     */
    public TNode<T> getLeft() {
        return left;
    }

    /**
     * set a new left for this node
     */
    public void setLeft(TNode<T> left) {
        this.left = left;
    }

    /**
     * return the right of this node
     */
    public TNode<T> getRight() {
        return right;
    }

    /**
     * set a new left for this node
     */
    public void setRight(TNode<T> right) {
        this.right = right;
    }

    /**
     * return the linkedList that inside in the node
     */
    public LinkedList<Frequency> getFrequencyLinkedList() {
        return this.frequencyLinkedList;
    }

    /**
     * check if this node is a leaf (doesn't have right and left)
     */
    public boolean isLeaf() {
        return (this.left == null && this.right == null);
    }

    /**
     * check if this node has right node
     */
    public boolean hasRight() {
        return this.right != null;
    }

    /**
     * check if this node has left node
     */
    public boolean hasLeft() {
        return this.left != null;
    }

    /**
     * return tha data in this node as a string
     */
    @Override
    public String toString() {
        return this.date.toString();
    }


    @Override
    public int compareTo(TNode<T> o) {
        return 0;
    }
}

