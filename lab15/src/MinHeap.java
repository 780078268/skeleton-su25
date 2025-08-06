import java.util.ArrayList;

/* A MinHeap class of Comparable elements backed by an ArrayList. */
public class MinHeap<E extends Comparable<E>> {

    /* An ArrayList that stores the elements in this MinHeap. */
    private ArrayList<E> contents;
    private int size;

    /* Initializes an empty MinHeap. */
    public MinHeap() {
        contents = new ArrayList<>();
        contents.add(null);
    }

    /* Returns the element at index INDEX, and null if it is out of bounds. */
     E getElement(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    /* Sets the element at index INDEX to ELEMENT. If the ArrayList is not big
       enough, add elements until it is the right size. */
    private void setElement(int index, E element) {
        while (index >= contents.size()) {
            contents.add(null);
        }
        contents.set(index, element);
    }

    /* Swaps the elements at the two indices. */
    private void swap(int index1, int index2) {
        E element1 = getElement(index1);
        E element2 = getElement(index2);
        setElement(index2, element1);
        setElement(index1, element2);
    }

    /* Prints out the underlying heap sideways. Use for debugging. */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getElement(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = getRightOf(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getElement(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getElement(index) + "\n";
            int leftChild = getLeftOf(index);
            if (getElement(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }

    /* Returns the index of the left child of the element at index INDEX. */
    private int getLeftOf(int index) {
        return index * 2 ;
    }

    /* Returns the index of the right child of the element at index INDEX. */
    private int getRightOf(int index) {
        return index * 2 + 1;
    }

    /* Returns the index of the parent of the element at index INDEX. */
    private int getParentOf(int index) {
        return index / 2;
    }

    /* Returns the index of the smaller element. At least one index has a
       non-null element. If the elements are equal, return either index. */
    private int min(int index1, int index2) {
        int compare = contents.get(index1).compareTo(contents.get(index2));
        if (compare < 0) {
            return index1;
        }
        return index2;
    }

    /* Returns but does not remove the smallest element in the MinHeap. */
    public E findMin() {
        if (size == 0) {
            return null;
        }
        return contents.get(1);
    }

    /* Bubbles up the element currently at index INDEX. */
    void bubbleUp(int index) {
        while (index > 1 && contents.get(index).compareTo(contents.get(getParentOf(index))) < 0) {
            swap(index, getParentOf(index));
            index = getParentOf(index);
        }
    }

    /* Bubbles down the element currently at index INDEX. */
    void bubbleDown(int index) {
        while (getLeftOf(index) <= size) {
            int leftChildIndex = getLeftOf(index);
            int rightChildIndex = getRightOf(index);
            int smallerChildIndex = leftChildIndex;
            if (getElement(rightChildIndex) != null && getElement(rightChildIndex).compareTo(getElement(leftChildIndex)) < 0) {
                smallerChildIndex = rightChildIndex;
            }
            if (getElement(index).compareTo(getElement(smallerChildIndex)) > 0) {
                swap(index, smallerChildIndex);
                index = smallerChildIndex;
            } else {
                break;
            }
        }
    }

    /* Returns the number of elements in the MinHeap. */
    public int size() {
        return size;
    }

    /* Inserts ELEMENT into the MinHeap. If ELEMENT is already in the MinHeap,
       throw an IllegalArgumentException.*/
    public void insert(E element) {
        if(contents.contains(element)) {
            throw new IllegalArgumentException("Element is already in the heap");
        }
        contents.add(element);
        size++;
        bubbleUp(size);
    }

    /* Returns and removes the smallest element in the MinHeap, or null if there are none. */
    public E removeMin() {
        if(size == 0) {
            return null;
        }
        E temp = contents.get(1);
        swap(1, size);
        contents.remove(size);
        size--;
        bubbleDown(1);
        return temp;
    }

    /* Replaces and updates the position of ELEMENT inside the MinHeap, which
       may have been mutated since the initial insert. If a copy of ELEMENT does
       not exist in the MinHeap, throw a NoSuchElementException. Item equality
       should be checked using .equals(), not ==. */
    public void update(E element) throws NoSuchFieldException {
        // TODO: OPTIONAL
        if(!contents.contains(element)) {
            throw new NoSuchFieldException("NO!");
        }else{
            int index = 1;
            for(int i = 0; i < size; i++) {
                if(getElement(i) == element) {
                    setElement(i, element);
                    index = i;
                }
            }
            bubbleUp(index);
        }
    }

    /* Returns true if ELEMENT is contained in the MinHeap. Item equality should
       be checked using .equals(), not ==. */
    public boolean contains(E element) {
        // OPTIONAL: OPTIMIZE THE SPEED OF THIS TO MAKE IT CONSTANT
        for (int i = 1; i < contents.size(); i++) {
            if (element.equals(contents.get(i))) {
                return true;
            }
        }
        return false;
    }
}
