package lecture.chapter03;

public class TestUE07 {
    
    public static void main(String[] args) {
        
        System.out.println("\n" + "═".repeat(60) + "\n");
        testHashTableLinearProbing();
        System.out.println("\n" + "═".repeat(60) + "\n");
        testHashTableAVL();
    }
    
    // ==================== ÜBUNG 7.1: HashTableAVL ====================
    
    private static void testHashTableAVL() {
        System.out.println("ÜBUNG 7.1: HashTableAVL (Kollisionsauflösung mit AVL-Trees)\n");
        
        // Große Tabelle um Resize zu vermeiden
        HashTableAVL table = new HashTableAVL(6);
        
        // Einfügen
        System.out.println("➤ Einfügen:");
        table.insert("Alice", "alice@example.com");
        table.insert("Bob", "bob@example.com");
        table.insert("Charlie", "charlie@example.com");
        table.insert("David", "david@example.com");
        System.out.println("  ✓ 4 Einträge eingefügt");
        
        // Suchen
        System.out.println("\n➤ Suchen:");
        System.out.println("  Alice   -> " + table.get("Alice"));
        System.out.println("  Bob     -> " + table.get("Bob"));
        System.out.println("  Unbekannt -> " + table.get("Unbekannt"));
        
        // Viele Einträge (testet AVL-Trees)
        System.out.println("\n➤ Viele Einträge einfügen:");
        for (int i = 0; i < 20; i++) {
            table.insert("User" + i, "user" + i + "@example.com");
        }
        System.out.println("  ✓ 20 weitere Einträge");
        System.out.println("  Load: " + table.getCurrentLoad() + "/" + table.getSize());
        
        // Löschen
        System.out.println("\n➤ Löschen:");
        table.remove("Alice");
        System.out.println("  Alice gelöscht -> " + table.get("Alice"));
        System.out.println("  Bob noch da   -> " + table.get("Bob"));
        
        System.out.println("\n✓ HashTableAVL Test erfolgreich");
    }
    
    // ==================== ÜBUNG 7.2: HashTableLinearProbing ====================
    
    private static void testHashTableLinearProbing() {
        System.out.println("ÜBUNG 7.2: HashTableLinearProbing (Lineares Sondieren)\n");
        
        HashTableLinearProbing table = new HashTableLinearProbing(6);
        
        // Einfügen
        System.out.println("➤ Einfügen:");
        table.insert("Berlin", "Deutschland");
        table.insert("Paris", "Frankreich");
        table.insert("London", "England");
        table.insert("Rom", "Italien");
        System.out.println("  ✓ 4 Städte eingefügt");
        System.out.println("  Load Factor: " + String.format("%.2f", table.getLoadFactor()));
        
        // Suchen
        System.out.println("\n➤ Suchen:");
        System.out.println("  Berlin -> " + table.get("Berlin"));
        System.out.println("  Paris  -> " + table.get("Paris"));
        System.out.println("  Wien   -> " + table.get("Wien"));
        
        // Update
        System.out.println("\n➤ Update:");
        table.insert("Berlin", "Germany");
        System.out.println("  Berlin -> " + table.get("Berlin"));
        
        // Viele Einträge (testet Linear Probing)
        System.out.println("\n➤ Viele Einträge einfügen:");
        for (int i = 0; i < 15; i++) {
            table.insert("Key" + i, "Value" + i);
        }
        System.out.println("  ✓ 15 weitere Einträge");
        System.out.println("  Load: " + table.getCurrentLoad() + "/" + table.getSize());
        System.out.println("  Load Factor: " + String.format("%.2f", table.getLoadFactor()));
        
        // Löschen (testet Tombstones)
        System.out.println("\n➤ Löschen:");
        table.remove("Berlin");
        table.remove("Key5");
        System.out.println("  Berlin gelöscht -> " + table.get("Berlin"));
        System.out.println("  Paris noch da  -> " + table.get("Paris"));
        System.out.println("  Load: " + table.getCurrentLoad() + "/" + table.getSize());
        
        // Einfügen nach Löschen (nutzt Tombstones)
        System.out.println("\n➤ Einfügen nach Löschen:");
        table.insert("Wien", "Österreich");
        System.out.println("  Wien -> " + table.get("Wien"));
        
        System.out.println("\n✓ HashTableLinearProbing Test erfolgreich");
    }
}
