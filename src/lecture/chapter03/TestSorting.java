package lecture.chapter03;

public class TestSorting {
    
    static class IntegerComparator implements IComparator {
        public int compare(Object data1, Object data2) {
            return ((Integer) data1) - ((Integer) data2);
        }
        
        public int compare(Object data, IKey key) {
            return 0;
        }
    }
    
    public static void main(String[] args) {
        IComparator comparator = new IntegerComparator();
        
        System.out.println("=== BubbleSort ===");
        DListSorting list1 = new DListSorting();
        list1.append(5);
        list1.append(2);
        list1.append(8);
        list1.append(1);
        list1.append(9);
        System.out.print("Vor Sortierung: ");
        list1.print();
        list1.bubbleSort(comparator);
        System.out.print("Nach Sortierung: ");
        list1.print();
        
        System.out.println("\n=== InsertionSort ===");
        DListSorting list2 = new DListSorting();
        list2.append(5);
        list2.append(2);
        list2.append(8);
        list2.append(1);
        list2.append(9);
        System.out.print("Vor Sortierung: ");
        list2.print();
        list2.insertionSort(comparator);
        System.out.print("Nach Sortierung: ");
        list2.print();
        
        System.out.println("\n=== SelectionSort ===");
        DListSorting list3 = new DListSorting();
        list3.append(5);
        list3.append(2);
        list3.append(8);
        list3.append(1);
        list3.append(9);
        System.out.print("Vor Sortierung: ");
        list3.print();
        list3.selectionSort(comparator);
        System.out.print("Nach Sortierung: ");
        list3.print();
    }
}
