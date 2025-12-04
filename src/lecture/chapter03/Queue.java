package lecture.chapter03;

public class Queue extends DList {

    public void enqueue(Object data) {
        if (tail == null) {
            tail = new Node(null, data, null);
            head = tail;
        } else {
            Node n = new Node(tail, data, null);
            tail.next = n;
            tail = n;
        }
    }

    public Object dequeue() {
        if (head == null) return null;
        Object val = head.data;
        head = head.next;
        if (head != null) head.prev = null;
        else tail = null; 
        return val;
    }

    public Object peek() {
        return (head == null) ? null : head.data;
    }

    public boolean empty() {
        return head == null;
    }
}