package lecture.chapter03;

public class MyHeap extends BTreeAppend {

    @SuppressWarnings("unchecked")

    private int compare(Object a, Object b) {
        if (a == null && b == null) return 0;
        if (a == null) return -1;
        if (b == null) return 1;

        return ((Comparable<Object>) a).compareTo(b);
    }

    private void swapData(Node x, Node y) {
        Object tmp = x.data;
        x.data = y.data;
        y.data = tmp;
    }

    // Aufgabe 1
    private void upHeap(Node node) {
        while (node != null && node.parent != null &&
                compare(node.parent.data, node.data) > 0) {

            swapData(node, node.parent);
            node = node.parent;
        }
    }

    @Override
    public void insert(Object data) {
        Node n = new Node(null, data, null);
        breadthFirstAppend(n); 
        upHeap(n);
    }

    // Aufgabe 2
    private void downHeap(Node node) {
        if (node == null) return;

        boolean done = false;
        while (!done) {
            Node child = node.left;

            if (node.left == null ||
                (node.right != null &&
                 compare(node.left.data, node.right.data) > 0)) {
                child = node.right;
            }

            if (child != null && compare(node.data, child.data) > 0) {
                swapData(node, child);
                node = child;
            } else {
                done = true;
            }
        }
    }

    private Node findNode(Object value) {
        if (root == null) return null;

        Queue q = new Queue();
        q.enqueue(root);

        while (!q.empty()) {
            Node n = (Node) q.dequeue();
            if (value == null ? n.data == null : value.equals(n.data)) {
                return n;
            }
            if (n.left  != null) q.enqueue(n.left);
            if (n.right != null) q.enqueue(n.right);
        }
        return null;
    }

    private Node findLastNode() {
        if (root == null) return null;

        Queue q = new Queue();
        q.enqueue(root);
        Node last = root;

        while (!q.empty()) {
            last = (Node) q.dequeue();
            if (last.left  != null) q.enqueue(last.left);
            if (last.right != null) q.enqueue(last.right);
        }
        return last;
    }

    private void removeNode(Node toRemove) {
        if (toRemove == null || root == null) return;

        Node last = findLastNode();
        if (last == null) return;

        if (toRemove == last) {
            Node p = last.parent;
            if (p == null) {
                root = null;
            } else if (p.left == last) {
                p.left = null;
            } else {
                p.right = null;
            }
            return;
        }

        Object oldData = toRemove.data;
        toRemove.data = last.data;

        Node p = last.parent;
        if (p == null) {
            root = null;
        } else if (p.left == last) {
            p.left = null;
        } else {
            p.right = null;
        }

        if (compare(toRemove.data, oldData) < 0) {
            upHeap(toRemove);
        } else if (compare(toRemove.data, oldData) > 0) {
            downHeap(toRemove);
        }
    }

    public boolean remove(Object data) {
        Node target = findNode(data);
        if (target == null) return false;

        removeNode(target);
        return true;
    }

    public Object extractRoot() {
        if (root == null) return null;

        Object result = root.data;
        removeNode(root);
        return result;
    }
}