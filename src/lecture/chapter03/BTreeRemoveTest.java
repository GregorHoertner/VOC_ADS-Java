package lecture.chapter03;

public class BTreeRemoveTest {
    public static void main(String[] args) {
        BTreeRemove t = new BTreeRemove();     
        int[] vals = {5,3,7,2,4,6,8};
        for (int v : vals) t.insert(v);
        System.out.println("Vor Entfernen:"); 
        t.depthFirstInOrder(data -> System.out.println(data)); 

        t.remove(2); 
        t.remove(7); 
        System.out.println("Nach Entfernen:"); 
        t.depthFirstInOrder(data -> System.out.println(data));
    }
}