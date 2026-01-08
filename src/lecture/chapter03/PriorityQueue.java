package lecture.chapter03;

public class PriorityQueue extends DList {
    
    private static class PriorityData {
        Object data;
        int priority;
        
        public PriorityData(Object data, int priority) {
            this.data = data;
            this.priority = priority;
        }
    }
    
    public void insert(Object data, int priority) {
        PriorityData newData = new PriorityData(data, priority);
        
        if (head == null) {
            head = tail = new Node(null, newData, null);
            return;
        }
        
        Node current = head;
        while (current != null) {
            PriorityData currentData = (PriorityData) current.data;
            
            if (priority < currentData.priority) {
                Node newNode = new Node(current.prev, newData, current);
                
                if (current.prev != null) {
                    current.prev.next = newNode;
                } else {
                    head = newNode;
                }
                current.prev = newNode;
                return;
            }
            
            current = current.next;
        }
        
        Node newNode = new Node(tail, newData, null);
        tail.next = newNode;
        tail = newNode;
    }
   
    public Object extractMin() {
        if (head == null) {
            return null;
        }
        
        PriorityData data = (PriorityData) head.data;
        Object result = data.data;
        
        head = head.next;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null; 
        }
        
        return result;
    }

    public Object peek() {
        if (head == null) {
            return null;
        }
        PriorityData data = (PriorityData) head.data;
        return data.data;
    }

    public boolean isEmpty() {
        return head == null;
    }
}
