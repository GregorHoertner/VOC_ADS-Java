package lecture.chapter03;

public class BTreeArray extends BTreeAppend {

    /**
     * Rekursive Hilfsmethode:
     * bettet den Teilbaum 'current' in das Array 'a' ein,
     * ausgehend vom Index 'index' (Heap-Schema).
     *
     * Indizes:
     *   Wurzel: 0
     *   links:  2*index + 1
     *   rechts: 2*index + 2
     */
    private void embedInArray(Node current, Object[] a, int index) {
        if (current == null) return;
        if (index >= a.length) return; // Sicherheitscheck

        // In-Order wie in depthFirstInOrder:
        // 1. links
        embedInArray(current.left, a, 2 * index + 1);

        // 2. Knoten selbst
        a[index] = current.data;

        // 3. rechts
        embedInArray(current.right, a, 2 * index + 2);
    }

    /**
     * Erzeugt ein Array, das den vollständigen Baum in Heap-Form enthält.
     */
    public Object[] toArrayEmbedding() {
        int h = height(root);          // Methode aus BTree
        int size = (1 << h) - 1;       // max. Knotenzahl bei Höhe h: 2^h - 1

        Object[] result = new Object[size];
        embedInArray(root, result, 0);
        return result;
    }
}