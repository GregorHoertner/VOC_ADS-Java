package lecture.chapter03;

public class DListSorting extends DList {
    
    public void bubbleSort(IComparator comparator) {
        if (head == null || head.next == null) {
            return;
        }
        
        boolean swapped;
        do {
            swapped = false;
            Node current = head;
            
            while (current != null && current.next != null) {
                if (comparator.compare(current.data, current.next.data) > 0) {
                    Object temp = current.data;
                    current.data = current.next.data;
                    current.next.data = temp;
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);
    }
    
    public void insertionSort(IComparator comparator) {
        if (head == null || head.next == null) {
            return;
        }
        
        Node current = head.next;
        
        while (current != null) {
            Node next = current.next;
            Node insertPos = head;
            
            while (insertPos != current && comparator.compare(insertPos.data, current.data) <= 0) {
                insertPos = insertPos.next;
            }
            
            if (insertPos != current) {
                removeNode(current);
                insertBefore(insertPos, current);
            }
            
            current = next;
        }
    }
    
    public void selectionSort(IComparator comparator) {
        if (head == null || head.next == null) {
            return;
        }
        
        Node current = head;
        
        while (current != null) {
            Node minNode = current;
            Node temp = current.next;
            
            while (temp != null) {
                if (comparator.compare(temp.data, minNode.data) < 0) {
                    minNode = temp;
                }
                temp = temp.next;
            }
            
            if (minNode != current) {
                Object tempData = current.data;
                current.data = minNode.data;
                minNode.data = tempData;
            }
            
            current = current.next;
        }
    }
    
    private void removeNode(Node toRemove) {
        if (toRemove != null) {
            if (toRemove == head) {
                head = toRemove.next;
            } else {
                toRemove.prev.next = toRemove.next;
            }
            if (toRemove == tail) {
                tail = toRemove.prev;
            } else {
                toRemove.next.prev = toRemove.prev;
            }
        }
    }
    
    private void insertBefore(Node target, Node toInsert) {
        if (target == head) {
            toInsert.next = head;
            toInsert.prev = null;
            head.prev = toInsert;
            head = toInsert;
        } else {
            toInsert.prev = target.prev;
            toInsert.next = target;
            target.prev.next = toInsert;
            target.prev = toInsert;
        }
    }
    
    public void print() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }
}
