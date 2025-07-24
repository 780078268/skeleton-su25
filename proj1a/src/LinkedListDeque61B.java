import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T>implements Deque61B<T> {
    Node sentinel;
    int size;

    public LinkedListDeque61B() {
        size = 0;
        sentinel = new Node(null, null, null);
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
        Node newNode = new Node(x, sentinel.next, sentinel);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size++;
    }

    @Override
    public void addLast(T x) {
        Node newNode = new Node(x, sentinel, sentinel.prev);
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
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if(size == 0){
            return null;
        }
        Node current = sentinel.next;
        sentinel.next = current.next;
        sentinel.next.prev = sentinel;
        size--;
        return current.item;
    }

    @Override
    public T removeLast() {
        if(size == 0){
            return null;
        }
        Node current = sentinel.prev;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;
        return current.item;
    }

    @Override
    public T get(int index) {
        Node current = sentinel.next;
        if (index < 0 || index >= size) {
            return null;
        } else {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current.item;
        }
    }
    private T recursive(int index,Node current) {
        current = current.next;
        if (index < 0 || index >= size) {
            return null;
        }
        if(index == 0){
            return current.item;
        }
        else{
            index--;
            return recursive(index,current);
        }

    }
    @Override
    public T getRecursive(int index) {
        return recursive(index,sentinel);
    }
}