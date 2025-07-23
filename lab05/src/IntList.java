import java.util.Objects;

/** A data structure to represent a Linked List of Integers.
 * Each IntList represents one node in the overall Linked List.
 */


public class IntList {

    /** The integer stored by this node. */
    public int item;
    /** The next node in this IntList. */
    public IntList next;

    /** Constructs an IntList storing ITEM and next node NEXT. */
    public IntList(int item, IntList next) {
        this.item = item;
        this.next = next;
    }

    /** Constructs an IntList storing ITEM and no next node. */
    public IntList(int item) {
        this(item, null);
    }

    /** Returns an IntList consisting of the elements in ITEMS.
     * IntList L = IntList.list(1, 2, 3);
     * System.out.println(L.toString()) // Prints 1 2 3 */
    public static IntList of(int... items) {
        /** Check for cases when we have no element given. */
        if (items.length == 0) {
            return null;
        }
        /** Create the first element. */
        IntList head = new IntList(items[0]);
        IntList last = head;
        /** Create rest of the list. */
        for (int i = 1; i < items.length; i++) {
            last.next = new IntList(items[i]);
            last = last.next;
        }
        return head;
    }

    /**
     * Returns [position]th item in this list. Throws IllegalArgumentException
     * if index out of bounds.
     *
     * @param position, the position of element.
     * @return The element at [position]
     */
    public int get(int position) {
        int a = 0;
        IntList head = this;
        if (position < 0) {
            throw new IllegalArgumentException();
        }

        for (int i = position; i >= 0; i--) {
            if (head == null) {
                throw new IllegalArgumentException();
            }
            a = head.item;
            head = head.next;
        }
        return a;
    }

    /**
     * Returns the string representation of the list. For the list (1, 2, 3),
     * returns "1 2 3".
     *
     * @return The String representation of the list.
     */
    public String toString() {
        String a ="";
        IntList head = this;
        while(head.next != null) {
            a=a+head.item;
            a=a+" ";
            head = head.next;
        }
        a=a+head.item;
        return a;
    }

    /**
     * Returns whether this and the given list or object are equal.
     *
     * NOTE: A full implementation of equals requires checking if the
     * object passed in is of the correct type, as the parameter is of
     * type Object. This also requires we convert the Object to an
     * IntList, if that is legal. The operation we use to do this is called
     * casting, and it is done by specifying the desired type in
     * parentheses. An example of this is `IntList otherLst = (IntList) obj;`
     * We recommend reviewing the `instanceOf` keyword mentioned in the spec.
     *
     * @param obj, another list (object)
     * @return Whether the two lists are equal.
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof IntList otherList) {
            IntList thisList = this;
            if (Objects.equals(otherList.toString(), thisList.toString())) {
                return true;
            }

        }
        return false;
    }

    /**
     * Adds the given value at the end of the list.
     *
     * @param value, the int to be added.
     */
    public void add(int value) {
        IntList last= new IntList(value,null);
        IntList head = this;
        while(head.next!=null){
            head = head.next;
        }
        head.next=last;
    }

    /**
     * Returns the smallest element in the list.
     *
     * @return smallest element in the list
     */
    public int smallest() {
        IntList head = this;
        int min = head.item;
        while(head.next != null) {
            if(head.item<min) {
                min = head.item;
            }
            head = head.next;
        }
        if(head.item<min) {min = head.item;}
        return min;
    }

    /**
     * Returns the sum of squares of all elements in the list.
     *
     * @return The sum of squares of all elements.
     */
    public int squaredSum() {
        int sum = 0;
        IntList head = this;
        while(head.next != null) {
            sum = sum + head.item*head.item;
            head = head.next;
        }
        sum=sum+head.item*head.item;
        return sum;
    }

    /**
     * Destructively squares each item of the list.
     *
     * @param L list to destructively square.
     */
    public static void dSquareList(IntList L) {
        while (L != null) {
            L.item = L.item * L.item;
            L = L.next;
        }
    }

    /**
     * Returns a list equal to L with all elements squared. Non-destructive.
     *
     * @param L list to non-destructively square.
     * @return the squared list.
     */
    public static IntList squareListIterative(IntList L) {
        if (L == null) {
            return null;
        }
        IntList res = new IntList(L.item * L.item, null);
        IntList ptr = res;
        L = L.next;
        while (L != null) {
            ptr.next = new IntList(L.item * L.item, null);
            L = L.next;
            ptr = ptr.next;
        }
        return res;
    }

    /** Returns a list equal to L with all elements squared. Non-destructive.
     *
     * @param L list to non-destructively square.
     * @return the squared list.
     */
    public static IntList squareListRecursive(IntList L) {
        if (L == null) {
            return null;
        }
        return new IntList(L.item * L.item, squareListRecursive(L.next));
    }

    /**
     * Returns a new IntList consisting of A followed by B,
     * non-destructively (no modifications to A).
     *
     * @param A list to be on the front of the new list.
     * @param B list to be on the back of the new list.
     * @return new list with A followed by B.
     */
    public static IntList catenate(IntList A, IntList B) {
        if(A==null){
            return B;
        }
        if(B==null){
            return A;
        }
        IntList head = new IntList(A.item,null);
        IntList cur = head;
        A=A.next;
        while (A != null) {
            cur.next = new IntList(A.item,null);
            A = A.next;
            cur = cur.next;
        }
        cur.next = B;
        return head;
    }

    /**
     * Returns a new IntList consisting of A followed by B,
     * destructively (make modifications to A).
     *
     * @param A list to be on the front of the new list.
     * @param B list to be on the back of the new list.
     * @return new list with A followed by B.
     */
    public static IntList dcatenate(IntList A, IntList B) {
        IntList currentA= A;
        if(A==null){
            return B;
        }
        if(B==null){
            return A;
        }

        while (currentA.next != null) {
            currentA= currentA.next;
        }
        currentA.next = B;
        return A;
    }
}
