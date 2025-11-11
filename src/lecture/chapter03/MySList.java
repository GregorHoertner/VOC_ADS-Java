package lecture.chapter03;

public class MySList extends SList {

    // Aufgabe 1: hinten anhängen
    public void append(Object data) {
        Node neu = new Node(data, null);

        if (head == null) {
            head = neu;
            return;
        }

        Node p = head;
        while (p.next != null) {
            p = p.next;
        }
        p.next = neu;
    }

    // Aufgabe 2: hinter bestimmtes Objekt einfügen
    public boolean insert(Object prev, Object data) {
        Node p = head;

        // Element suchen, dessen data == prev ist
        while (p != null && p.data != prev) {
            p = p.next;
        }

        // nicht gefunden
        if (p == null) {
            return false;
        }

        // gefunden -> neues Element hinter p einfügen
        Node neu = new Node(data, p.next);
        p.next = neu;

        return true;
    }

    // Aufgabe 3: alle passenden Elemente in neuer Liste zurückgeben
    public SList searchAll(IKey key) {
        MySList result = new MySList();   
        Node p = head;

        while (p != null) {
            if (key.matches(p.data)) {
                result.append(p.data);    
            }
            p = p.next;
        }

        return result;
    }
}