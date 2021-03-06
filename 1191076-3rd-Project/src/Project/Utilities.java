/**
 * @autor: Amir Eleyan
 * ID: 1191076
 * At: 5/5/2021 2:10 AM
 */
package Project;

import Lists.*;

public final class Utilities {

    // Attribute
    // AVL tree of babys
    public final static AVL_Tree<Babys> BABYS_AVL_TREE = new AVL_Tree<>();
    public static LinkedList<Integer> years = new LinkedList<>();

    private Utilities() {
    }


    // Search for a specific node and return his linkedList
    public static LinkedList<Frequency> searchForBabys(Babys data) {
        TNode<Babys> searcherForBaby = BABYS_AVL_TREE.search(data);
        if (searcherForBaby != null) {
            return searcherForBaby.getFrequencyLinkedList();
        } else { // node not found
            return null;
        }
    }

    // get average frequency for a specific node
    public static float averageFrequency(Babys data) {
        TNode<Babys> searcherForBaby = BABYS_AVL_TREE.search(data);// log n
        if (searcherForBaby != null) {
            int length = searcherForBaby.getFrequencyLinkedList().length(); // n
            return totalFrequency(searcherForBaby.getFrequencyLinkedList().getHead()) / (float) length; // n
        }
        return -1; // not found
    }

    //  return info for tha baby hsa max frequency over all years
    public static Babys nameOfMaxFrequency(Frequency frequency) {
        TNode<Babys> rootOfTree = BABYS_AVL_TREE.getRoot();
        LinkedQueue<TNode<Babys>> tempQueue = new LinkedQueue<>();
        int max = 0;
        Babys maxBaby = new Babys();
        if (rootOfTree != null) {

            tempQueue.enqueue(rootOfTree);
            while (!tempQueue.isEmpty()) {

                // store first element in the queue and remove it
                TNode<Babys> tempNode = tempQueue.dequeue();

                int tempMax = totalFrequency(tempNode.getFrequencyLinkedList().getHead()); // get total frequency for current node

                if (tempMax > max) { // get the max
                    max = tempMax;
                    maxBaby = tempNode.getDate();
                    frequency.setFrequency(tempMax);
                }

                // insert first.left to tempQueue
                if (tempNode.getLeft() != null) {
                    tempQueue.enqueue(tempNode.getLeft());
                }

                // insert first.right to tempQueue
                if (tempNode.getRight() != null) {
                    tempQueue.enqueue(tempNode.getRight());
                }
            }
            return maxBaby;
        }
        return null; // no data in the tree
    }

    // return total frequency for a specific node
    public static int totalFrequency(Node<Frequency> head) {
        Node<Frequency> current = head;
        int sum = 0;
        while (current != null) {
            sum += current.getData().getFrequency();
            current = current.getNext();
        }
        return sum;
    }

    // get total number of babys in a selected year and travers it
    public static int traverseSelectedYear(int year, LinkedList<BabyForTraverse> babyForTraverseLinkedList) {

        TNode<Babys> rootOfTree = BABYS_AVL_TREE.getRoot();
        LinkedQueue<TNode<Babys>> tempQueue = new LinkedQueue<>();
        int totalBabysFrequency = 0;
        if (rootOfTree != null) {

            tempQueue.enqueue(rootOfTree);
            while (!tempQueue.isEmpty()) {

                // store first element in the queue and remove it
                TNode<Babys> tempNode = tempQueue.dequeue();

                // get frequency for current node in a selected year
                Frequency frequency = tempNode.getFrequencyLinkedList().search(new Frequency(year));

                if (frequency != null) {
                    totalBabysFrequency += frequency.getFrequency();
                    babyForTraverseLinkedList.insertAtLast(new BabyForTraverse(tempNode.getDate().getName(), tempNode.getDate().getGender(), frequency.getFrequency()));

                }

                // insert first.left to tempQueue
                if (tempNode.getLeft() != null) {
                    tempQueue.enqueue(tempNode.getLeft());
                }

                // insert first.right to tempQueue
                if (tempNode.getRight() != null) {
                    tempQueue.enqueue(tempNode.getRight());
                }
            }
            return totalBabysFrequency;
        }
        return -1;

    }


    // get year from file name
    public static int getYearFromFileName(String fileName) {
        int count = 0;
        String year = "";
        for (int i = 0; i < fileName.length(); ++i) {
            if (Character.isDigit(fileName.charAt(i))) {
                year += fileName.charAt(i);
                count++;
                if (count == 4) break;
            } else {
                count = 0;
            }
        }
        if (count == 0) return 0;
        return Integer.parseInt(year.trim());
    }

    /**
     * To check the value of the entered company name that if contain only char ot not
     */
    public static boolean isName(String companyN) {
        return companyN.matches("[a-zA-Z]+");
    }
}
