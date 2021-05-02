package Lists;
/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At:  17/3/2021  9:17 PM
 */

public class Node<T extends Comparable<T>> {


    private T data; // data on this node

    private Node<T> next; // next of this node

    // No argument constructor
    public Node() {
    }

    // Constructor with a specific data
    public Node(T data) {
        this.data = data;
    }

    // Return the data of this node
    public T getData() {
        return this.data;
    }

    // Set a new data for this node
    public void setData(T data) {
        this.data = data;
    }

    // Return the next node
    public Node<T> getNext() {
        return this.next;
    }

    // Set a new next node
    public void setNext(Node<T> next) {
        this.next = next;
    }

    // Return the data of the node as a string
    @Override
    public String toString() {
        if (this.data == null) return "null";
        return this.data.toString();
    }

}




