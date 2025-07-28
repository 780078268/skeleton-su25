import java.util.ArrayList;
import java.util.List;

/**
 * Represents a set of ints. A simple implementation of a set using a list.
 */
public class ListSet implements SimpleSet {

    List<Integer> elems;

    public ListSet() {
        elems = new ArrayList<Integer>();
    }

    /** Adds k to the set. */
    @Override
    public void add(int k) {
        if(elems.contains(k)){
            return;
        }
        elems.add(k);
    }

    /** Removes k from the set. */
    @Override
    public void remove(int k) {
        // TODO - use the above variable with an appropriate List method.
        if(elems.contains(k)){
            elems.remove((Integer) k);
        }
        // The reason is beyond the scope of this lab, but involves
        // method resolution.
    }

    /** Return true if k is in this set, false otherwise. */
    @Override
    public boolean contains(int k) {
        for(Integer i : elems) {
            if (i == k) {
                return true;
            }
        }
        return false;
    }

    /** Return true if this set is empty, false otherwise. */
    @Override
    public boolean isEmpty() {
      return this.size() == 0;
    }

    /** Returns the number of items in the set. */
    @Override
    public int size() {
        int size = elems.size();
        return size;
    }

    /** Returns an array containing all of the elements in this collection. */
    @Override
    public int[] toIntArray() {
        int[] arr = new int[elems.size()];
        for(int i = 0; i < elems.size(); i++) {
            arr[i] = elems.get(i);
        }
        return arr;
    }
}
