package lecture.chapter03;


public class BTreeAppend extends BTree {

    protected void breadthFirstAppend(Node newNode) {
        if (root == null) {           
            root = newNode;
            newNode.parent = null;
            return;
        }

        Queue q = new Queue();
        q.enqueue(root);

        while (!q.empty()) {
            Node cur = (Node) q.dequeue();
            if (cur.left == null) {   
                cur.left = newNode;
                newNode.parent = cur;
                return;
            } else {
                q.enqueue(cur.left);
            }

            if (cur.right == null) {  
                cur.right = newNode;
                newNode.parent = cur;
                return;
            } else {
                q.enqueue(cur.right);
            }
        }
    }

    public void insert(Object data) {
        Node n = new Node(null, data, null); 
        breadthFirstAppend(n);
    }
}