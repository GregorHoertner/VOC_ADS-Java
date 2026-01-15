package lecture.chapter03.tests;

import lecture.chapter03.BacktrackingAndPathfinding;

public class TestUE08 {
    
    public static void main(String[] args) {
        testCheckPosition();
        testPlaceQueen();
        testExistPath();
    }
    
    public static void testCheckPosition() {
        System.out.println("=== Test checkPosition ===");
        boolean[][] board = new boolean[8][8];
        
        board[0][0] = true;
        System.out.println("Dame auf (0,0)");
        System.out.println("Position (1,1) gültig? " + BacktrackingAndPathfinding.checkPosition(board, 1, 1));
        System.out.println("Position (2,1) gültig? " + BacktrackingAndPathfinding.checkPosition(board, 2, 1));
        System.out.println();
    }
    
    public static void testPlaceQueen() {
        System.out.println("=== Test placeQueen (8-Damen Problem) ===");
        boolean[][] board = new boolean[8][8];
        
        boolean success = BacktrackingAndPathfinding.placeQueen(board, 0);
        System.out.println("Lösung gefunden: " + success);
        
        if (success) {
            printBoard(board);
        }
        System.out.println();
    }
    
    public static void testExistPath() {
        System.out.println("=== Test existPath ===");
        int[][] graph = {
            {0, 1, 1, 0, 0},
            {0, 0, 0, 1, 0},
            {0, 0, 0, 1, 1},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0}
        };
        
        System.out.println("Graph mit 5 Knoten:");
        System.out.println("Pfad von 0 nach 3: " + BacktrackingAndPathfinding.existPath(graph, 0, 3));
        System.out.println("Pfad von 0 nach 4: " + BacktrackingAndPathfinding.existPath(graph, 0, 4));
        System.out.println("Pfad von 4 nach 0: " + BacktrackingAndPathfinding.existPath(graph, 4, 0));
        System.out.println();
    }
    
    private static void printBoard(boolean[][] board) {
        int n = board.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(board[i][j] ? "Q " : ". ");
            }
            System.out.println();
        }
    }
}
