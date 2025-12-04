package lecture.chapter03;

public class BTreeRemove extends BTreeAppend {

    public Node find(Object value) {
        if (root == null) return null;
        Queue q = new Queue();
        q.enqueue(root);
        while (!q.empty()) {
            Node n = (Node) q.dequeue();
            if (value == null ? n.data == null : value.equals(n.data)) return n;
            if (n.left  != null) q.enqueue(n.left);
            if (n.right != null) q.enqueue(n.right);
        }
        return null;
    }

    public void remove(Object data) {
        Node target = find(data);
        if (target != null) remove(target);
    }
   protected void remove(Node node) {
        if (root == null || node == null) return;

        Queue q = new Queue();
        q.enqueue(root);
        Node last = root;
        while (!q.empty()) {
            last = (Node) q.dequeue();
            if (last.left  != null) q.enqueue(last.left);
            if (last.right != null) q.enqueue(last.right);
        }

        node.data = last.data;

        Node p = last.parent;
        if (p == null) {   
            root = null;
        } else if (p.left == last) {
            p.left = null;
        } else {
            p.right = null;
        }
    }
    
}