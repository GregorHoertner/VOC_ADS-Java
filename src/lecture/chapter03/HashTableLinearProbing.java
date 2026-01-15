package lecture.chapter03;

/**
 * Hashtable mit Kollisionsauflösung durch Lineares Sondieren (Linear Probing)
 * 
 * Bei einer Kollision wird linear nach dem nächsten freien Platz gesucht.
 * Die Elemente werden direkt im Array gespeichert, keine separate Bucket-Struktur.
 * 
 * Vorteile:
 * - Sehr Cache-freundlich (sequenzieller Speicherzugriff)
 * - Kein Overhead durch zusätzliche Datenstrukturen
 * - Gute Performance bei niedrigem Load-Factor
 * 
 * Nachteile:
 * - Primary Clustering (Bildung von zusammenhängenden belegten Bereichen)
 * - Performance verschlechtert sich bei hohem Load-Factor
 */
public class HashTableLinearProbing {
    protected Entry[] table = null;
    protected int size = 0;
    protected int maxLoad = 0;
    protected int currentLoad = 0;
    
    // Marker für gelöschte Einträge (tombstone)
    private static final Entry DELETED = new Entry(0, "", null);
    
    /**
     * Entry speichert Hash, Key und Data direkt im Array
     */
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
    public HashTableLinearProbing() {
        this(4); // 2^4 = 16 Slots
    }
    
    /**
     * Konstruktor mit Exponent für initiale Größe
     * @param exponent Exponent zur Basis 2 (Größe = 2^exponent)
     */
    public HashTableLinearProbing(int exponent) {
        size = 1 << exponent;
        maxLoad = (int) (size * 0.5); // Bei Linear Probing max 50% Auslastung empfohlen
        table = new Entry[size];
    }
    
    /**
     * Findet den Index für einen gegebenen Key
     * Verwendet lineares Sondieren bei Kollisionen
     * 
     * @param key Der Schlüssel
     * @param forInsertion true wenn für Einfügen, false für Suchen
     * @return Index im Array oder -1 wenn nicht gefunden (nur bei Suche)
     */
    protected int findSlot(String key, boolean forInsertion) {
        long hash = sdbm(key);
        int index = (int) (hash & (size - 1));
        int startIndex = index;
        int firstDeletedIndex = -1;
        
        do {
            Entry entry = table[index];
            
            // Leerer Slot gefunden
            if (entry == null) {
                if (forInsertion) {
                    // Wenn wir ein gelöschtes Slot vorher gesehen haben, verwenden wir das
                    return (firstDeletedIndex != -1) ? firstDeletedIndex : index;
                } else {
                    // Element nicht gefunden
                    return -1;
                }
            }
            
            // Gelöschter Slot
            if (entry == DELETED) {
                if (forInsertion && firstDeletedIndex == -1) {
                    firstDeletedIndex = index;
                }
                // Bei Suche weitermachen
            }
            // Slot mit passendem Key gefunden
            else if (entry.key.equals(key)) {
                return index;
            }
            
            // Nächsten Slot prüfen (lineares Sondieren)
            index = (index + 1) & (size - 1);
            
        } while (index != startIndex); // Bis wir einmal rum sind
        
        // Tabelle ist voll oder Element nicht gefunden
        if (forInsertion && firstDeletedIndex != -1) {
            return firstDeletedIndex;
        }
        return -1;
    }
    
    /**
     * Fügt ein Key-Value-Paar in die Hashtable ein
     * @param key Der Schlüssel
     * @param data Die zu speichernden Daten
     */
    public void insert(String key, Object data) {
        // Erst prüfen ob Key bereits existiert (Update)
        int existingIndex = findSlot(key, false);
        if (existingIndex != -1 && table[existingIndex] != null && table[existingIndex] != DELETED) {
            // Update existing entry
            long hash = sdbm(key);
            table[existingIndex] = new Entry(hash, key, data);
            return;
        }
        
        // Neues Element einfügen
        int index = findSlot(key, true);
        
        if (index == -1) {
            // Sollte nicht passieren, aber sicherheitshalber resize
            resize();
            insert(key, data);
            return;
        }
        
        long hash = sdbm(key);
        Entry oldEntry = table[index];
        table[index] = new Entry(hash, key, data);
        
        // Nur currentLoad erhöhen wenn der Slot vorher leer oder gelöscht war
        if (oldEntry == null || oldEntry == DELETED) {
            currentLoad++;
        }
        
        if (currentLoad >= maxLoad) {
            resize();
        }
    }
    
    /**
     * Vergrößert die Hashtable wenn der Load-Factor überschritten wird
     */
    public void resize() {
        Entry[] oldTable = table;
        int oldSize = size;
        
        size <<= 1;
        maxLoad = (int) (size * 0.5);
        table = new Entry[size];
        currentLoad = 0;
        
        // Alle Elemente neu einfügen
        for (int i = 0; i < oldSize; i++) {
            Entry entry = oldTable[i];
            if (entry != null && entry != DELETED) {
                insert(entry.key, entry.data);
            }
        }
    }
    
    /**
     * Sucht ein Element anhand des Keys
     * @param key Der Schlüssel
     * @return Die gespeicherten Daten oder null
     */
    public Object get(String key) {
        int index = findSlot(key, false);
        
        if (index == -1 || table[index] == null || table[index] == DELETED) {
            return null;
        }
        
        return table[index].data;
    }
    
    /**
     * Entfernt ein Element anhand des Keys
     * @param key Der Schlüssel
     */
    public void remove(String key) {
        int index = findSlot(key, false);
        
        if (index != -1 && table[index] != null && table[index] != DELETED) {
            table[index] = DELETED; // Tombstone setzen
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
     * Gibt die Anzahl der Slots zurück
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Gibt den aktuellen Load-Factor zurück (0.0 bis 1.0)
     */
    public double getLoadFactor() {
        return (double) currentLoad / size;
    }
    
    /**
     * Hilfsmethode für Debugging: Zeigt die Belegung der Tabelle
     */
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
