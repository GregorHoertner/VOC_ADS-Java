package lecture.chapter03;

public class HashTableAVL {
    protected BucketAVL[] buckets = null;
    protected int size = 0;
    protected int maxLoad = 0;
    protected int currentLoad = 0;
    
    protected static class BucketAVL {
        private AVLTree tree;
        
        public BucketAVL() {
            tree = new AVLTree(new TupleComparator());
        }
    }
    
    protected static class Tuple {
        public long hash;
        public String key;
        public Object data;
        
        public Tuple(long hash, String key, Object data) {
            this.hash = hash;
            this.key = key;
            this.data = data;
        }
    }
    
    protected static class TupleComparator implements IComparator {
        public int compare(Object data1, Object data2) {
            Tuple t1 = (Tuple) data1;
            Tuple t2 = (Tuple) data2;
            return t1.key.compareTo(t2.key);
        }
        
        public int compare(Object data, IKey key) {
            Tuple tuple = (Tuple) data;
            TupleKey tkey = (TupleKey) key;
            return tuple.key.compareTo(tkey.key);
        }
    }
    
    protected static class TupleKey implements IKey {
        String key;
        
        public TupleKey(String key) {
            this.key = key;
        }

        public boolean matches(Object data) {
            return key.equals(((Tuple) data).key);
        }
    }
    
    protected static long sdbm(String s) {
        long hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash = s.charAt(i) + (hash << 6) + (hash << 16) - hash;
        }
        return hash;
    }
    
    public HashTableAVL() {
        this(4);
    }
    
    public HashTableAVL(int exponent) {
        size = 1 << exponent;
        maxLoad = (int) (size * 0.75);
        buckets = initBuckets(size);
    }
    
    private static BucketAVL[] initBuckets(int size) {
        BucketAVL[] b = new BucketAVL[size];
        for (int i = 0; i < b.length; i++) {
            b[i] = new BucketAVL();
        }
        return b;
    }
    
    public void insert(String key, Object data) {
        long hash = sdbm(key);
        Tuple tuple = new Tuple(hash, key, data);
        
        buckets[(int) (hash & (size - 1))].tree.insert(tuple);
        
        currentLoad++;
        if (currentLoad >= maxLoad) {
            resize();
        }
    }
    
    public void resize() {
        BucketAVL[] newBuckets = initBuckets(size << 1);
        
        for (int i = 0; i < size; i++) {
            AVLTree tree = buckets[i].tree;
            IFIterator it = tree.iterator();
            
            while (it.hasNext()) {
                Tuple entry = (Tuple) it.next();
                newBuckets[(int) (entry.hash & ((size << 1) - 1))].tree.insert(entry);
            }
        }
        size <<= 1;
        maxLoad = (int) (size * 0.75);
        buckets = newBuckets;
    }
    
    public Object get(String key) {
        long hash = sdbm(key);
        BucketAVL bucket = buckets[(int) (hash & (size - 1))];
        
        TupleKey tupleKey = new TupleKey(key);
        Tuple entry = (Tuple) bucket.tree.search(tupleKey);
        
        return (entry != null) ? entry.data : null;
    }
    
    public void remove(String key) {
        long hash = sdbm(key);
        BucketAVL bucket = buckets[(int) (hash & (size - 1))];
        
        TupleKey tupleKey = new TupleKey(key);
        Tuple entry = (Tuple) bucket.tree.search(tupleKey);
        
        if (entry != null) {
            bucket.tree.remove(tupleKey);
            currentLoad--;
        }
    }
    
    public int getCurrentLoad() {
        return currentLoad;
    }
    
    public int getSize() {
        return size;
    }
}
