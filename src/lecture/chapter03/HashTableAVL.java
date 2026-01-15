package lecture.chapter03;

/**
 * Hashtable mit Kollisionsauflösung mittels AVLTrees
 * 
 * Anstatt SLists wie in der Standard-Hashtable verwendet diese Implementierung
 * AVLTrees zur Speicherung von Kollisionen in jedem Bucket.
 * 
 * Vorteile gegenüber SList:
 * - Bessere Suchzeit bei vielen Kollisionen: O(log n) statt O(n)
 * - Automatische Balancierung sorgt für gleichmäßige Performance
 */
public class HashTableAVL {
    protected BucketAVL[] buckets = null;
    protected int size = 0;
    protected int maxLoad = 0;
    protected int currentLoad = 0;
    
    /**
     * Bucket mit AVLTree zur Kollisionsauflösung
     */
    protected static class BucketAVL {
        private AVLTree tree;
        
        public BucketAVL() {
            // AVLTree benötigt einen Comparator für Tuple-Objekte
            tree = new AVLTree(new TupleComparator());
        }
    }
    
    /**
     * Tuple speichert Hash, Key und Data
     */
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
    
    /**
     * Comparator für Tuples - vergleicht nach Key
     */
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
    
    /**
     * Key für die Suche nach Tuples
     */
    protected static class TupleKey implements IKey {
        String key;
        
        public TupleKey(String key) {
            this.key = key;
        }

        public boolean matches(Object data) {
            return key.equals(((Tuple) data).key);
        }
    }
    
    /**
     * SDBM Hash-Funktion
     */
    protected static long sdbm(String s) {
        long hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash = s.charAt(i) + (hash << 6) + (hash << 16) - hash;
        }
        return hash;
    }
    
    /**
     * Standard-Konstruktor mit Default-Größe
     */
    public HashTableAVL() {
        this(4); // 2^4 = 16 Buckets
    }
    
    /**
     * Konstruktor mit Exponent für initiale Größe
     * @param exponent Exponent zur Basis 2 (Größe = 2^exponent)
     */
    public HashTableAVL(int exponent) {
        size = 1 << exponent;
        maxLoad = (int) (size * 0.75);
        buckets = initBuckets(size);
    }
    
    /**
     * Initialisiert die Buckets
     */
    private static BucketAVL[] initBuckets(int size) {
        BucketAVL[] b = new BucketAVL[size];
        for (int i = 0; i < b.length; i++) {
            b[i] = new BucketAVL();
        }
        return b;
    }
    
    /**
     * Fügt ein Key-Value-Paar in die Hashtable ein
     * @param key Der Schlüssel
     * @param data Die zu speichernden Daten
     */
    public void insert(String key, Object data) {
        long hash = sdbm(key);
        Tuple tuple = new Tuple(hash, key, data);
        
        buckets[(int) (hash & (size - 1))].tree.insert(tuple);
        
        currentLoad++;
        if (currentLoad >= maxLoad) {
            resize();
        }
    }
    
    /**
     * Vergrößert die Hashtable wenn der Load-Factor überschritten wird
     */
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
    
    /**
     * Sucht ein Element anhand des Keys
     * @param key Der Schlüssel
     * @return Die gespeicherten Daten oder null
     */
    public Object get(String key) {
        long hash = sdbm(key);
        BucketAVL bucket = buckets[(int) (hash & (size - 1))];
        
        TupleKey tupleKey = new TupleKey(key);
        Tuple entry = (Tuple) bucket.tree.search(tupleKey);
        
        return (entry != null) ? entry.data : null;
    }
    
    /**
     * Entfernt ein Element anhand des Keys
     * @param key Der Schlüssel
     */
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
    
    /**
     * Gibt die aktuelle Anzahl der Elemente zurück
     */
    public int getCurrentLoad() {
        return currentLoad;
    }
    
    /**
     * Gibt die Anzahl der Buckets zurück
     */
    public int getSize() {
        return size;
    }
}
