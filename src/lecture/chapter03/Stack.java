package lecture.chapter03;

public class Stack extends SList {

    public void push(Object data) {

        head = new Node(data, head);
    }

    public Object pop() {
        if (head == null) return null;
        Object val = head.data;
        head = head.next;
        return val;
    }

    public Object peek() {
        return (head == null) ? null : head.data;
    }

    public boolean empty() {
        return head == null;
    }
}