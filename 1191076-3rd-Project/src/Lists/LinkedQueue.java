/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At:  8/4/2021 10:15 PM
 */
package Lists;

public class LinkedQueue<T extends Comparable<T>> {

    /**
     * Attribute
     */
    private Node<T> first; // the first node of this queue

    private Node<T> last; // the last node of this queue

    /**
     * Constructor with no arguments
     */
    public LinkedQueue() {
        // create dummy node
        this.first = this.last = new Node<>();
    }

    /**
     * add new element in last of the queue
     */
    public void enqueue(T element) {
        Node<T> newNode = new Node<>(element);
        this.last.setNext(newNode);
        this.last = newNode;
    }

    /**
     * Remove and return the first element in this queue (FIFO,first-in first-out)
     */
    public T dequeue() {

        if (isEmpty()) return null; // no nodes in this queue

        Node<T> deleteNode = this.first.getNext(); // store the first element

        if (this.first.getNext() == this.last) this.last = this.first; // queue has one node

        this.first.setNext(this.first.getNext().getNext());// delete the first node by changing the pointers
        return deleteNode.getData();//return the first node
    }


    /**
     * Clear all node in the queue
     */
    public void clear() {
        this.first = this.last = new Node<>();
    }

    /**
     * Return the length of this queue
     */
    public int length() {
        // No nodes in this queue
        if (isEmpty()) return 0;

        Node<T> current = this.first.getNext();
        int length = 0; // # of nodes
        while (current != null) {
            length++;
            current = current.getNext();
        }
        return length;
    }

    /**
     * To check status of this queue if empty or not
     */
    public boolean isEmpty() {
        return this.first == this.last;
    }

    /**
     * Merge to queue with them
     */
    public void merge(LinkedQueue<T> linkedQueues) {
        if (!linkedQueues.isEmpty()) {
            this.last.setNext(linkedQueues.first.getNext());
            this.last = linkedQueues.last;
        }

    }

    /**
     * Return the data inside this queue as a string
     */
    @Override
    public String toString() {

        if (isEmpty()) return "Empty"; // no elements in this queue
        Node<T> current = this.first.getNext();
        StringBuilder str = new StringBuilder(); // to store the data as a string

        while (current != null) {
            str.append(current).append("\n");
            current = current.getNext();
        }
        return str.toString();
    }

    /**
     * Print data in this queue
     */
    public void displayQueue() {
        System.out.println(this);
    }

    /**
     * Fill this queue from stacks
     */
    public void fillFromStacks(Node<T> topNodeOfStacks) {
        if (topNodeOfStacks == null) {
            clear();
        } else {
            fillFromStacks(topNodeOfStacks.getNext());
            enqueue(topNodeOfStacks.getData());
        }
    }

    /**
     * Return the first element as a node
     */
    public Node<T> getFirst() {
        return this.first.getNext();
    }

    /**
     * Return the first as a element
     */
    public T first() {
        if (!isEmpty())
            return this.first.getNext().getData();
        return null;
    }

    /**
     * Set a new first as a element
     */
    public void setFirst(T element) {
        if (!isEmpty())// because the first is a point to the dummy node
            this.first.getNext().setData(element);
    }

    /**
     * Return the last element as a node
     */
    public Node<T> getLast() {
        return this.last;
    }

    /**
     * Return the last element
     */
    public T last() {
        if (!isEmpty())
            return this.last.getData();
        else return null;
    }

    /**
     * Set a new last as a element
     */
    public void setLast(T element) {
        if (!isEmpty()) // because the last is a point to the dummy node
            this.last.setData(element);
    }
}



