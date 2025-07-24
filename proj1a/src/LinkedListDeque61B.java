import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T>{
    Node sentinel;
    int size;

    public LinkedListDeque61B() {
        size = 0;
        sentinel = new Node(null,null,null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    public class Node {
        Node prev;
        Node next;
        T item;

        public Node(T item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void addFirst(T x) {
        Node newNode = new Node(x,sentinel.next,sentinel);
        sentinel.next.prev = newNode;
        sentinel.next=newNode;
        size++;
    }

    @Override
    public void addLast(T x) {
     Node newNode = new Node (x,sentinel,sentinel.prev);
     sentinel.prev.next = newNode;
     sentinel.prev = newNode;
     size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node current = sentinel.next;
        while (current != sentinel) {
            returnList.add(current.item);
            current = current.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if (sentinel.next == sentinel) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        int returnSize = 0;
        Node current = sentinel.next;
        for(int i =0;i>=0;i++){
            if(current.next != sentinel){
                returnSize++;
                current = current.next;
            }
            else{
                return returnSize;
            }
        }
        return returnSize;
    }

    @Override
    public T removeFirst() {
        sentinel.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size--;
        return null;
    }

    @Override
    public T removeLast() {
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;
        return null;
    }

    @Override
    public T get(int index) {
        int size = this.size;
        Node current = sentinel.next;
        if(index<0 || index>=size){
            return null;
        }
        for(int i =0;i<index;i++){
            sentinel = sentinel.next;
        }
        return current.item;
    }

    @Override
    public T getRecursive(int index) {
        int size = this.size;
        Node current = sentinel.next;
        for(int i =0;i<index;i++){
            current.next = current.prev;
            current.prev = current;
            current = current.next;
        }
        return null;
    }
}
