package deque;

import deque.ArrayDeque61B;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
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
        if (size == 0) {
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
        if (size == 0) {
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

    private T recursive(int index, Node current) {
        current = current.next;
        if (index < 0 || index >= size) {
            return null;
        }
        if (index == 0) {
            return current.item;
        } else {
            index--;
            return recursive(index, current);
        }
    }

    @Override
    public T getRecursive(int index) {
        return recursive(index, sentinel);
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkListDequeIterator();
    }

    private class LinkListDequeIterator implements Iterator<T> {
        Node current;

        public LinkListDequeIterator() {
            current = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            // 只要已经返回的元素数量小于队列的总大小，就说明还有元素
            return current.next != sentinel;
        }

        // 实现 next() 方法
        @Override
        public T next() {
            // 检查是否还有元素
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("No more elements in the deque.");
            }
            T itemToReturn = current.item;
            current = current.next;
            return itemToReturn;
        }
    }
    @Override
    public boolean equals(Object other) {
        if(size != ((LinkedListDeque61B<?>)other).size()){
            return false;
        }
        Node current = sentinel.next;
        Node otherCurrent = ((LinkedListDeque61B)other).sentinel.next;
        for(int i = 0; i < size; i++) {
            if(current.item != otherCurrent.item){
                return false;
            }
            current = current.next;
            otherCurrent = otherCurrent.next;
        }
        return true;
    }
    @Override
    public String toString() {
        String returnString = "";
        Node current = sentinel.next;
        for(int i = 0; i < size; i++) {
            returnString += current.item;
            current = current.next;
        }
        return returnString;
    }
}
