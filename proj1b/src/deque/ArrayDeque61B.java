package deque;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    T[] arr = (T[]) new Object[8];
    int size = 0;
    int nextFirst = 0;
    int nextLast = 0;
    int arrSize = 8;

    public ArrayDeque61B() {
        nextFirst = 4;
        nextLast = 5;
    }

    public int getnext(int index) {
        int i = index;
        if (i == arrSize - 1) {
            i = 0;
        } else {
            i = i + 1;
        }
        return i;
    }

    public int getlast(int index) {
        int i = index;
        if (i == 0) {
            i = arrSize - 1;
        } else {
            i = i - 1;
        }
        return i;
    }

    @Override
    public void addFirst(T x) {
        if (size == arrSize) {
            T[] newArr = (T[]) new Object[arrSize * 2];
            int j = getnext(nextFirst);
            for (int i = 0; i < size; i++) {
                newArr[i] = arr[j];
                j = getnext(j);
            }
            arrSize = arrSize * 2;
            nextFirst = arrSize - 1;
            newArr[nextFirst] = x;
            nextFirst = getlast(nextFirst);
            size++;
            nextLast = size;
            arr = newArr;
        } else {
            arr[nextFirst] = x;
            nextFirst = getlast(nextFirst);
            size++;
        }
    }

    @Override
    public void addLast(T x) {
        if (size == arrSize) {
            T[] newArr = (T[]) new Object[arrSize * 2];
            int j = getnext(nextFirst);
            for (int i = 0; i < size; i++) {
                newArr[i] = arr[j];
                j = getnext(j);
            }
            arrSize = arrSize * 2;
            nextFirst = arrSize - 1;
            nextLast = size;
            newArr[nextLast] = x;
            nextLast = getnext(nextLast);
            size++;
            arr = newArr;
        } else {
            arr[nextLast] = x;
            nextLast = getnext(nextLast);
            size++;
        }
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        int j = nextFirst;
        for (int i = 0; i < size; i++) {
            j = getnext(j);
            returnList.add(arr[j]);
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
        if (isEmpty()) {
            return null;
        }
        if (size <= arrSize / 4) {
            int i = getnext(nextFirst);
            T temp = arr[i];
            arr[i] = null;
            nextFirst = i;
            size--;
            int k = getnext(nextFirst);
            T[] newArr = (T[]) new Object[arrSize / 2];
            for (int j = 0; j < size; j++) {
                newArr[j] = arr[k];
                k = getnext(k);
            }
            arrSize = arrSize / 2;
            nextFirst = arrSize - 1;
            nextLast = size;
            arr = newArr;
            return temp;
        } else {
            int i = getnext(nextFirst);
            T temp = arr[i];
            arr[i] = null;
            nextFirst = i;
            size--;
            return temp;
        }
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (size <= arrSize / 4) {
            int i = getlast(nextLast);
            T temp = arr[i];
            arr[i] = null;
            nextLast = i;
            size--;
            int k = getnext(nextFirst);
            T[] newArr = (T[]) new Object[arrSize / 2];
            for (int j = 0; j < size; j++) {
                newArr[j] = arr[k];
                k = getnext(k);
            }
            arrSize = arrSize / 2;
            nextFirst = arrSize - 1;
            nextLast = size;
            arr = newArr;
            return temp;
        } else {
            int i = getlast(nextLast);
            T temp = arr[i];
            arr[i] = null;
            nextLast = i;
            size--;
            return temp;
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int j = nextFirst;
        for (int i = 0; i <= index; i++) {
            j = getnext(j);
        }
        return arr[j];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    @Override
    public Iterator<T> iterator() {
        return iterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int currentIndex;
        private int itemsReturned;

        public ArrayDequeIterator() {
            currentIndex = getnext(nextFirst);
            itemsReturned = 0;
        }

        @Override
        public boolean hasNext() {
            // 只要已经返回的元素数量小于队列的总大小，就说明还有元素
            return itemsReturned < size;
        }

        // 实现 next() 方法
        @Override
        public T next() {
            // 检查是否还有元素
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("No more elements in the deque.");
            }
            T itemToReturn = arr[currentIndex];
            currentIndex = getnext(currentIndex);
            itemsReturned++;
            return itemToReturn;
        }
    }
    @Override
    public boolean equals(Object other) {
        if(size() != ((ArrayDeque61B<?>)other).size()){
            return false;
        }
        for(int i = 0; i < size(); i++) {
            if(get(i) != ((ArrayDeque61B<?>)other).get(i)){
                return false;
            }
        }
        return true;
    }
    @Override
    public String toString() {
        String returnString = "";
        for(int i = 0; i < size(); i++) {
            returnString = returnString + get(i);
        }
        return returnString;
    }
}