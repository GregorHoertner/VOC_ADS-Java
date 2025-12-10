package lecture.chapter03;

import java.util.Arrays;

public class TestUE5 {

    public static void main(String[] args) {

        // Test 1: MyHeap – insert, extractRoot, remove
        MyHeap heap = new MyHeap();

        int[] values = { 10, 4, 7, 1, 15, 3 };

        System.out.println("=== Test MyHeap ===");
        System.out.println("Einfügen: " + Arrays.toString(values));

        for (int v : values) {
            heap.insert(v);
        }

        System.out.print("Heap (breadth first): ");
        heap.breadthFirst(new IWorker() {
            @Override
            public void work(Object data) {
                System.out.print(data + " ");
            }
        });
        System.out.println();

        System.out.println("Entferne 7 aus dem Heap:");
        heap.remove(7);

        System.out.print("Heap nach remove(7): ");
        heap.breadthFirst(new IWorker() {
            @Override
            public void work(Object data) {
                System.out.print(data + " ");
            }
        });
        System.out.println();

        System.out.print("extractRoot() in Reihenfolge: ");
        Object x;
        while ((x = heap.extractRoot()) != null) {
            System.out.print(x + " ");
        }
        System.out.println();
        System.out.println();

        // Test 2: BTreeArray
        BTreeArray tree = new BTreeArray();

        System.out.println("=== Test BTreeArray ===");
        System.out.println("Einfügen in vollständigen Baum: " + Arrays.toString(values));

        for (int v : values) {
            tree.insert(v);
        }

        System.out.print("Baum (breadth first): ");
        tree.breadthFirst(new IWorker() {
            @Override
            public void work(Object data) {
                System.out.print(data + " ");
            }
        });
        System.out.println();

        Object[] embedded = tree.toArrayEmbedding();
        System.out.println("Array-Einbettung (Heap-Layout):");
        System.out.println(Arrays.toString(embedded));
    }
}