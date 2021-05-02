/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At:  18/3/2021  3:40 AM
 */
package Lists;

public class LinkedList<T extends Comparable<T>> {

    private Node<T> head, tail;

    public LinkedList() {
        this.head = this.tail = null;
    }

    public Node<T> getHead() {
        return this.head;
    }

    public Node<T> getTail() {
        return this.tail;
    }

    public void insertAtFirst(T element) {
        Node<T> newNode = new Node<>(element);
        if (isEmpty()) {
            // Head and tail on the same node(list empty)
            this.tail = newNode;
        } else {
            newNode.setNext(this.head); // set the next of newNode to tha head
        }
        this.head = newNode; // head point to the new node
    }


    public T getFirst() {
        if (isEmpty()) { // there is no nodes in list
            System.out.println("The list is empty");
            return null;
        }
        return this.getHead().getData();
    }


    public T removeFirst() {
        if (isEmpty()) {
            System.out.println("List is empty");
            return null;
        }
        // if we not add the previous condition will be thread exception
        // because the head is null thus head.getData thread exception
        return remove(this.head.getData());
    }

    /**
     * Add new element for this list
     */
    public void insertAtLast(T element) {
        Node<T> newNode = new Node<>(element);
        if (isEmpty()) {
            // Head and tail on the same node(list empty)
            this.head = newNode;
        } else {
            // Tail on a specific node
            this.tail.setNext(newNode); // next of the node point to new node
        }
        this.tail = newNode; // Tail point to the new node
    }

    /**
     * Return tha first element for this list
     */
    public T getLast() {
        if (isEmpty()) { // there is no nodes in list
            System.out.println("The list is empty");
            return null;
        }
        return this.getTail().getData();
    }

    /**
     * Remove last element of this list
     */
    public T removeLast() {
        if (isEmpty()) {
            System.out.println("The list is empty");
            return null;
        }
        // if we not add the previous condition will be thread exception
        // because the tail is null thus tail.getData thread exception
        return remove(this.tail.getData());
    }


    /**
     * Insert element with a default sort depend on a compareTo methode implementation
     */
    public void addBySort(T element) {
        if (isEmpty() && element != null) { // list is empty
            insertAtFirst(element);
        } else {
            if (element != null) {
                Node<T> newNode = new Node<>(element);
                Node<T> current = this.head;
                Node<T> previous = null;
                // sort descending
                if (element.compareTo(current.getData()) < 0) { // data for element larger than data of head
                    insertAtFirst(element);
                } else {
                    // data for element less than data of head
                    while ((current != null) && (element.compareTo(current.getData()) >= 0)) {
                        previous = current;
                        current = current.getNext();
                    }
                    //The current reached the end of the list and the element less than current
                    previous.setNext(newNode);
                    if (current == null) this.tail = newNode;
                    newNode.setNext(current);
                }
            }
        }
    }

    /**
     * Inset new element after specific element
     */
    public boolean insertAfter(T element, T afterObject) {
        if (isEmpty()) return false;
        Node<T> current = this.head;
        Node<T> newNode = new Node<>(element);
        while (current != null) {
            if (current.getData().equals(afterObject)) {

                // set next of the new node to the next of the current
                newNode.setNext(current.getNext());

                // list contains one node or current point to last node
                if (current.getNext() == null) this.tail = newNode;

                current.setNext(newNode); // set next of the current to the newNode
                return true;
            }
            current = current.getNext();
        }
        return false;
    }


    /**
     * To remove a specific element in the list
     */
    public T remove(T element) {
        if (isEmpty()) return null;
        Node<T> prevPrevious = null, previous = null, current = this.head;
        if (current.getData().equals(element)) { // if element is a first node
            this.head = head.getNext();
            return current.getData();
        } else {
            boolean ok = false;
            while (current != null) {
                if (ok) prevPrevious = previous;
                else ok = true;

                previous = current;
                current = current.getNext();

                if (current == null && previous.getData().equals(element)) { // if element it is a last node
                    prevPrevious.setNext(null);
                    return previous.getData();
                } else if (current == null && !previous.getData().equals(element)) {//The current reached the end of the list and the element does not exist
                    return null;
                } else if (current.getData().equals(element)) { // the element is found
                    previous.setNext(current.getNext());
                    return current.getData();
                }

            }
            return null;
        }
    }


    /**
     * To search and return a specific element
     */
    public T search(T element) {
        if (isEmpty()) return null; // No node in list
        Node<T> current = this.head;
        while (current != null) {
            if (current.getData().equals(element)) return current.getData();
            current = current.getNext();
        }
        return null;
    }


    /**
     * To merge this list with another list
     */
    public void merge(LinkedList<T> linkedList) {
        if (isEmpty()) this.head = linkedList.getHead();
        else {
            this.tail.setNext(linkedList.getHead());
            this.tail = linkedList.getTail();
        }
    }

    /**
     * Traverse list
     */
    public void printList() {
        if (isEmpty()) System.out.println("List is empty");
        else {
            Node<T> current = this.head;
            while (current != null) {
                System.out.print(current);
                current = current.getNext();
            }
        }
    }

    /**
     * Traverse list reverse
     */
    public void printListReverse(Node<T> current) {
        if (isEmpty()) System.out.println("List is empty");
        else if (current == null) {
        } else {
            printListReverse(current.getNext()); //  calling the function itself(recursion)
            System.out.print(current);
        }
    }

    /**
     * To get the length of the list
     */
    public int length() {
        if (isEmpty()) return 0; // list is empty and return 0
        else {
            Node<T> current = this.head;
            int length = 0; // length of the list
            while (current != null) {
                length++;
                current = current.getNext();
            }
            return length; // return the length if the list is not empty
        }
    }

    /**
     * Set head equal null, thus we cannot access to another nodes,
     * thus JVM convert them to the garbage
     */
    public boolean isEmpty() {
        return (this.head == null);
    }

    /**
     * To set head = null and we can't access to another nodes(clear list(another node will be garbage by java))
     */
    public void clear() {
        this.head = null;
    }


    /**
     * To return tha data in the list as a string
     */
    public String toString() {
        if (isEmpty()) return "The list is empty\n";
        String str = "";
        Node<T> strNode = this.head;
        while (strNode != null) {
            str += strNode.getData()+"\n";
            strNode = strNode.getNext();
        }
        return str;
    }
}


