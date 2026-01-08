package lecture.chapter03;

public class TestPriorityQueue {
    
    public static void main(String[] args) {
        PriorityQueue pq = new PriorityQueue();
        
        System.out.println("=== Priority Queue Test ===\n");
        
        // Test 1: Insert elements with different priorities
        System.out.println("Inserting elements:");
        pq.insert("Task A", 3);
        System.out.println("  Inserted 'Task A' with priority 3");
        
        pq.insert("Task B", 1);
        System.out.println("  Inserted 'Task B' with priority 1");
        
        pq.insert("Task C", 2);
        System.out.println("  Inserted 'Task C' with priority 2");
        
        pq.insert("Task D", 1);
        System.out.println("  Inserted 'Task D' with priority 1 (same as Task B)");
        
        pq.insert("Task E", 5);
        System.out.println("  Inserted 'Task E' with priority 5");
        
        // Test 2: Extract elements (should come out ordered by priority)
        System.out.println("\nExtracting elements:");
        while (!pq.isEmpty()) {
            Object task = pq.extractMin();
            System.out.println("  Extracted: " + task);
        }
        
        // Test 3: FIFO order for same priority
        System.out.println("\n=== Testing FIFO for same priority ===");
        pq.insert("First Priority 2", 2);
        pq.insert("Second Priority 2", 2);
        pq.insert("Third Priority 2", 2);
        
        System.out.println("Extracting (should be in FIFO order):");
        System.out.println("  " + pq.extractMin());
        System.out.println("  " + pq.extractMin());
        System.out.println("  " + pq.extractMin());
        
        // Test 4: Empty queue
        System.out.println("\n=== Testing empty queue ===");
        System.out.println("Extract from empty queue: " + pq.extractMin());
        System.out.println("Is empty: " + pq.isEmpty());
        
        // Test 5: Mixed priorities with FIFO
        System.out.println("\n=== Complex test ===");
        pq.insert("P1-First", 1);
        pq.insert("P3-First", 3);
        pq.insert("P1-Second", 1);
        pq.insert("P2-First", 2);
        pq.insert("P3-Second", 3);
        pq.insert("P2-Second", 2);
        
        System.out.println("Expected order: P1-First, P1-Second, P2-First, P2-Second, P3-First, P3-Second");
        System.out.print("Actual order:   ");
        for (int i = 0; i < 6; i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(pq.extractMin());
        }
        System.out.println("\n");
    }
}
