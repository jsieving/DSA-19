import java.util.*;

public class MyHashMap<K, V> implements Map<K, V> {

    // average number of entries per bucket before we grow the map
    private static final double ALPHA = 1.0;
    // average number of entries per bucket before we shrink the map
    private static final double BETA = .25;

    // resizing factor: (new size) = (old size) * (resize factor)
    private static final double SHRINK_FACTOR = 0.5, GROWTH_FACTOR = 2.0;

    private static final int MIN_BUCKETS = 16;

    // array of buckets
    protected LinkedList<Entry>[] buckets;
    private int size = 0;
    public int rf = 0;

    public MyHashMap() {
        initBuckets(MIN_BUCKETS);
    }

    public class Entry implements Map.Entry<K, V> {
        private K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V newValue) {
            value = newValue;
            return value;
        }
    }

    /**
     * given a key, return the bucket where the `K, V` pair would be stored if it were in the map.
     */
    private LinkedList<Entry> chooseBucket(Object key) {
        int hashCode = key.hashCode();
        int n = hashCode % buckets.length;
        return buckets[n];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * return true if key is in map
     */
    @Override
    public boolean containsKey(Object key) {
        Entry e = retrieve(key);
        if (e != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * return true if value is in map
     */
    @Override
    public boolean containsValue(Object value) {
        for (LinkedList<Entry> l : buckets) {
            Iterator<Entry> i = l.iterator();
            Entry e = null;
            if (i.hasNext()) {
                e = i.next();
            }
            while (e != null) {
                if (e.value != null && e.value.equals(value)) {
                    return true;
                } else if (value == null) {
                    return true;
                } else if (i.hasNext()) {
                    e = i.next();
                } else {
                    break;
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        Entry e = retrieve(key);
        if (e != null) {
            return e.value;
        } else {
            return null;
        }
    }

    private Entry retrieve(Object key) {
        LinkedList<Entry> l = chooseBucket(key);
        Iterator<Entry> i = l.iterator();
        Entry e = null;
        if (i.hasNext()) {
            e = i.next();
        }
        while (e != null) {
            if (e.key.equals(key)) {
                return e;
            } else if (i.hasNext()) {
                e = i.next();
            } else {
                break;
            }
        }
        return null;
    }

    /**
     * add a new key-value pair to the map. Grow if needed, according to `ALPHA`.
     * @return the value associated with the key if it was previously in the map, otherwise null.
     */
    @Override
    public V put(K key, V value) {
        Entry prev = retrieve(key);
        if (prev != null) {
            V oldVal = prev.value;
            System.out.printf("Replacing: %s = %s <- %s\n", key, oldVal, value);
            prev.value = value;
            return oldVal;
        } else {
            if ((float) (size+1)/ (float) buckets.length > ALPHA) {
                System.out.printf("Growing\n");
                System.out.printf("\tOld length: %d, old contents length: %d\n", buckets.length, size);
                rehash(GROWTH_FACTOR);
                System.out.printf("\tNew length: %d, new contents length: %d\n", buckets.length, size+1);
            }
            Entry e = new Entry(key, value);
            LinkedList<Entry> l = chooseBucket(key);
            l.add(e);
            if (value != null && (int) value > 8500) {
                System.out.printf("Inserting: %s = %s, %b\n", key, value, containsKey(key));
            }
            size++;
            return null;
        }
    }

    /**
     * Remove the key-value pair associated with the given key. Shrink if needed, according to `BETA`.
     * Make sure the number of buckets doesn't go below `MIN_BUCKETS`. Do nothing if the key is not in the map.
     * @return the value associated with the key if it was in the map, otherwise null.
     */
    @Override
    public V remove(Object key) {
        LinkedList<Entry> l = chooseBucket(key);
        if (!containsKey(key)) { return null; }
        int i = 0;
        V retVal;
        for (Entry e : l) {
            if (e.key.equals(key)) {
                retVal = e.value;
                l.remove(i);
                size--;
                if (((float) (size) / (float) buckets.length < BETA) && ((float) ((float) buckets.length * SHRINK_FACTOR) >= (float) MIN_BUCKETS)) {
                    System.out.printf("Shrinking\n");
                    System.out.printf("\tOld length: %d, new contents length: %d\n", buckets.length, size);
                    rehash(SHRINK_FACTOR);
                    System.out.printf("\tNew length: %d, new contents length: %d\n", buckets.length, size);

                }
                return retVal;
            } else {
                i++;
            }
        }
        System.out.printf("Removal failed: %s\n", key);
        rf++;
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Changes the number of buckets and rehashes the existing entries.
     * If growthFactor is 2, the number of buckets doubles. If it is 0.25,
     * the number of buckets is divided by 4.
     */
    private void rehash(double growthFactor) {
        Object[] entries = new Object[size];
        int count = 0;
        for (LinkedList<Entry> l : buckets) {
            Iterator<Entry> i = l.iterator();
            Entry e = null;
            if (i.hasNext()) {
                e = i.next();
            }
            while (e != null) {
                entries[count] = e;
                count++;
                if (i.hasNext()) {
                    e = i.next();
                } else {
                    break;
                }
            }
        }
        initBuckets((int) ((double) buckets.length * growthFactor));
        size = 0;
        for (int i = 0; i < count; i++) {
            Entry e = (Entry) entries[i];
            put(e.key, e.value);
        }
    }

    private void initBuckets(int size) {
        buckets = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    public void clear() {
        initBuckets(buckets.length);
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (Map.Entry<K, V> e : entrySet()) {
            keys.add(e.getKey());
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = new ArrayList<>();
        for (Map.Entry<K, V> e : entrySet()) {
            values.add(e.getValue());
        }
        return values;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>();
        for (LinkedList<Entry> map : buckets) {
            set.addAll(map);
        }
        return set;
    }
}
