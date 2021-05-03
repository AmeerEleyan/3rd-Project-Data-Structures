package project3;

import Lists.*;

public final class Utilities {

    // Attribute
    // AVL tree of babys
    private final static AVL_Tree<Babys> BABYS_AVL_TREE = new AVL_Tree<>();
    public static LinkedList<Integer> years = new LinkedList<>();

    private Utilities() {
    }


    //Q1
    public static LinkedList<Frequency> searchForBabys(Babys data) {
        TNode<Babys> searcherForBaby = BABYS_AVL_TREE.search(data);
        if (searcherForBaby != null) {
            System.out.println(searcherForBaby.getDate() + "\n");
            // returnBaby = ;
            System.out.println(searcherForBaby.getFrequencyLinkedList());
            return searcherForBaby.getFrequencyLinkedList();
        } else {
            return null;
        }

    }

    //Q3
    public static Babys nameOfMaxFrequency() {
        TNode<Babys> rootOfTree = BABYS_AVL_TREE.getRoot();
        LinkedQueue<TNode<Babys>> tempQueue = new LinkedQueue<>();
        int max = 0;
        Babys maxBaby = new Babys();
        if (rootOfTree != null) {

            tempQueue.enqueue(rootOfTree);
            while (!tempQueue.isEmpty()) {

                // store first element in the queue and remove it
                TNode<Babys> tempNode = tempQueue.dequeue();

                int tempMax = totalFrequency(tempNode.getFrequencyLinkedList().getHead());
                if (tempMax > max) {
                    max = tempMax;
                    maxBaby = tempNode.getDate();
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
        return null;
    }

    //Q3
    private static int totalFrequency(Node<Frequency> head) {
        Node<Frequency> current = head;
        int sum = 0;
        while (current != null) {
            sum += current.getData().getFrequency();
            current = current.getNext();
        }
        return sum;
    }


    //Q4
    public static int totalNumberOfBabysInSelectedYear(int year) {
        TNode<Babys> rootOfTree = BABYS_AVL_TREE.getRoot();
        LinkedQueue<TNode<Babys>> tempQueue = new LinkedQueue<>();
        int totalBabys = 0;
        if (rootOfTree != null) {

            tempQueue.enqueue(rootOfTree);
            while (!tempQueue.isEmpty()) {

                // store first element in the queue and remove it
                TNode<Babys> tempNode = tempQueue.dequeue();

                int tempTotal = getYearFromLinkedList(tempNode.getFrequencyLinkedList(), year);
                if (tempTotal != -1)
                    totalBabys += tempTotal;

                // insert first.left to tempQueue
                if (tempNode.getLeft() != null) {
                    tempQueue.enqueue(tempNode.getLeft());
                }

                // insert first.right to tempQueue
                if (tempNode.getRight() != null) {
                    tempQueue.enqueue(tempNode.getRight());
                }
            }
            return totalBabys;
        }
        return 0;
    }

    //Q4
    private static int getYearFromLinkedList(LinkedList<Frequency> frequencyLinkedList, int year) {
        Frequency searcherForYear = frequencyLinkedList.search(new Frequency(year));
        if (searcherForYear == null)
            return -1;
        return searcherForYear.getFrequency();
    }


    //Q5
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
}
