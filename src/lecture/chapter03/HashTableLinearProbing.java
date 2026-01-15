package lecture.chapter03;

public class HashTableLinearProbing {
    protected Entry[] table = null;
    protected int size = 0;
    protected int maxLoad = 0;
    protected int currentLoad = 0;
    
    private static final Entry DELETED = new Entry(0, "", null);
    
    protected static class Entry {
        public long hash;
        public String key;
        public Object data;
        
        public Entry(long hash, String key, Object data) {
            this.hash = hash;
            this.key = key;
            this.data = data;
        }
    }
    
    protected static long sdbm(String s) {
        long hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash = s.charAt(i) + (hash << 6) + (hash << 16) - hash;
        }
        return hash;
    }
    
    public HashTableLinearProbing() {
        this(4);
    }
    
    public HashTableLinearProbing(int exponent) {
        size = 1 << exponent;
        maxLoad = (int) (size * 0.5);
        table = new Entry[size];
    }
    
    protected int findSlot(String key, boolean forInsertion) {
        long hash = sdbm(key);
        int index = (int) (hash & (size - 1));
        int startIndex = index;
        int firstDeletedIndex = -1;
        
        do {
            Entry entry = table[index];
            
            if (entry == null) {
                if (forInsertion) {
                    return (firstDeletedIndex != -1) ? firstDeletedIndex : index;
                } else {
                    return -1;
                }
            }
            
            if (entry == DELETED) {
                if (forInsertion && firstDeletedIndex == -1) {
                    firstDeletedIndex = index;
                }
            }
            else if (entry.key.equals(key)) {
                return index;
            }
            
            index = (index + 1) & (size - 1);
            
        } while (index != startIndex);
        
        if (forInsertion && firstDeletedIndex != -1) {
            return firstDeletedIndex;
        }
        return -1;
    }
    
    public void insert(String key, Object data) {
        int existingIndex = findSlot(key, false);
        if (existingIndex != -1 && table[existingIndex] != null && table[existingIndex] != DELETED) {
            long hash = sdbm(key);
            table[existingIndex] = new Entry(hash, key, data);
            return;
        }
        
        int index = findSlot(key, true);
        
        if (index == -1) {
            resize();
            insert(key, data);
            return;
        }
        
        long hash = sdbm(key);
        Entry oldEntry = table[index];
        table[index] = new Entry(hash, key, data);
        
        if (oldEntry == null || oldEntry == DELETED) {
            currentLoad++;
        }
        
        if (currentLoad >= maxLoad) {
            resize();
        }
    }
    
    public void resize() {
        Entry[] oldTable = table;
        int oldSize = size;
        
        size <<= 1;
        maxLoad = (int) (size * 0.5);
        table = new Entry[size];
        currentLoad = 0;
        
        for (int i = 0; i < oldSize; i++) {
            Entry entry = oldTable[i];
            if (entry != null && entry != DELETED) {
                insert(entry.key, entry.data);
            }
        }
    }
    
    public Object get(String key) {
        int index = findSlot(key, false);
        
        if (index == -1 || table[index] == null || table[index] == DELETED) {
            return null;
        }
        
        return table[index].data;
    }
    
    public void remove(String key) {
        int index = findSlot(key, false);
        
        if (index != -1 && table[index] != null && table[index] != DELETED) {
            table[index] = DELETED;
            currentLoad--;
        }
    }
    
    public int getCurrentLoad() {
        return currentLoad;
    }
    
    public int getSize() {
        return size;
    }
    
    public double getLoadFactor() {
        return (double) currentLoad / size;
    }
    
    public void printTable() {
        System.out.println("=== HashTable (Size: " + size + ", Load: " + currentLoad + ", Factor: " + 
                          String.format("%.2f", getLoadFactor()) + ") ===");
        for (int i = 0; i < size; i++) {
            Entry entry = table[i];
            if (entry == null) {
                System.out.println("[" + i + "] EMPTY");
            } else if (entry == DELETED) {
                System.out.println("[" + i + "] DELETED");
            } else {
                System.out.println("[" + i + "] " + entry.key + " -> " + entry.data);
            }
        }
    }
}
