import java.util.Iterator;
import java.util.LinkedList;

public class HashMap<K, V> implements Map61BL<K, V> {
    @Override
    public void clear() {
        buckets = (LinkedList<Entry<K, V>>[]) new LinkedList[buckets.length];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<Entry<K, V>>();
        }
        size = 0;
    }

    public int capacity() {
        return this.buckets.length;
    }


    @Override
    public boolean containsKey(K key) {
        int i = Math.floorMod(key.hashCode(), buckets.length);
        for(Entry<K,V> entry : buckets[i]) {
            if(entry.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        int i = Math.floorMod(key.hashCode(), buckets.length);
        for(Entry<K, V> entry : buckets[i]) {
            if(entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        int i = Math.floorMod(key.hashCode(), buckets.length);
        LinkedList<Entry<K, V>> bucket = buckets[i];
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }
        if ((double)(size + 1) / buckets.length > this.loadFactor) {
            resize();
            i = Math.floorMod(key.hashCode(), buckets.length);
            bucket = buckets[i];
        }
        bucket.addLast(new Entry<>(key, value));
        size++;
    }


    public void resize() {
        LinkedList<Entry<K, V>>[] oldBuckets = this.buckets;
        this.buckets = (LinkedList<Entry<K, V>>[]) new LinkedList[oldBuckets.length * 2];
        for (int i = 0; i < this.buckets.length; i++) {
            this.buckets[i] = new LinkedList<>();
        }
        this.size = 0;
        for (LinkedList<Entry<K, V>> bucket : oldBuckets) {
            for (Entry<K, V> entry : bucket) {
                this.put(entry.key, entry.value);
            }
        }
    }

    @Override
    public V remove(K key) {
        int i = Math.floorMod(key.hashCode(), buckets.length);
        LinkedList<Entry<K, V>> bucket = buckets[i];
        for(int index = 0; index < bucket.size(); index++) {
            if(bucket.get(index).key.equals(key)) {
                V value = bucket.get(index).value;
                bucket.remove(index);
                size--;
                return value;
            }
        }
        return null;
    }

    @Override
    public boolean remove(K key, V value) {
        return remove(key) == value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }

    int initialCapacity = 16;

    private LinkedList<Entry<K, V>>[] buckets;
    int size;
    double loadFactor;
    public HashMap() {
        this.size = 0;
        this.loadFactor = 0.75;
        buckets = new LinkedList[initialCapacity ];
        this.buckets = (LinkedList<Entry<K, V>>[]) new LinkedList[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            this.buckets[i] = new LinkedList<Entry<K, V>>();
        }
    }

    public HashMap(int initialCapacity){
        this.size = 0;
        this.loadFactor = 0.75;
        buckets = new LinkedList[initialCapacity ];
        this.buckets = (LinkedList<Entry<K, V>>[]) new LinkedList[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            this.buckets[i] = new LinkedList<Entry<K, V>>();
        }
    }
    public HashMap(int initialCapacity, double loadFactor){
        this.size = 0;
        this.loadFactor = loadFactor;
        buckets = new LinkedList[initialCapacity ];
        this.buckets = (LinkedList<Entry<K, V>>[]) new LinkedList[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            this.buckets[i] = new LinkedList<Entry<K, V>>();
        }
    }


    private static class Entry<K, V> {
        private K key;
        private V value;
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        public boolean keyEquals(Entry other) {
            return key.equals(other.key);
        }

        /* Returns true if both the KEY and the VALUE match. */
        @Override
        public boolean equals(Object other) {
            return (other instanceof Entry && key.equals(((Entry) other).key) && value.equals(((Entry) other).value));
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }
}

