package lecture.chapter03;

public class BacktrackingAndPathfinding {
    
    public static boolean checkPosition(boolean[][] array, int column, int line) {
        int n = array.length;
        
        for (int i = 0; i < n; i++) {
            if (array[line][i]) {
                return false;
            }
        }
        
        for (int i = 0; i < n; i++) {
            if (array[i][column]) {
                return false;
            }
        }
        
        for (int i = 0; i < n; i++) {
            int diagLine = line - column + i;
            if (diagLine >= 0 && diagLine < n && array[diagLine][i]) {
                return false;
            }
        }
        
        for (int i = 0; i < n; i++) {
            int diagLine = line + column - i;
            if (diagLine >= 0 && diagLine < n && array[diagLine][i]) {
                return false;
            }
        }
        
        return true;
    }
    
    public static boolean placeQueen(boolean[][] array, int line) {
        int n = array.length;
        
        if (line >= n) {
            return true;
        }
        
        for (int column = 0; column < n; column++) {
            if (checkPosition(array, column, line)) {
                array[line][column] = true;
                
                if (placeQueen(array, line + 1)) {
                    return true;
                }
                
                array[line][column] = false;
            }
        }
        
        return false;
    }
    
    public static boolean existPath(int[][] adjazenzMatrix, int start, int end) {
        boolean[] visited = new boolean[adjazenzMatrix.length];
        return existPathHelper(adjazenzMatrix, start, end, visited);
    }
    
    private static boolean existPathHelper(int[][] adjazenzMatrix, int current, int end, boolean[] visited) {
        if (current == end) {
            return true;
        }
        
        visited[current] = true;
        
        for (int i = 0; i < adjazenzMatrix.length; i++) {
            if (adjazenzMatrix[current][i] != 0 && !visited[i]) {
                if (existPathHelper(adjazenzMatrix, i, end, visited)) {
                    return true;
                }
            }
        }
        
        return false;
    }
}
