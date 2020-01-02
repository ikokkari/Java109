public class ChessProblems {
    
    private static int[][] dv = {
        {0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {1, -1}, {-1, -1}, {-1, 1}
    };
    
    private static boolean inside(int i, int j, int n) {
        return 0 <= i && i < n && 0 <= j && j < n;
    }
    
    public int countSafeSquaresRooks(int n, boolean[][] rooks) {
        boolean[] unsafeRow = new boolean[n];
        boolean[] unsafeCol = new boolean[n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(rooks[i][j]) { unsafeRow[i] = true; unsafeCol[j] = true; }
            }
        }
        int safeRows = n, safeCols = n;
        for(int i = 0; i < n; i++) {
            if(unsafeRow[i]) { safeRows--; }
            if(unsafeCol[i]) { safeCols--; }
        }
        return safeRows * safeCols;
    }
    
    public int countSafeSquaresQueens(int n, boolean[][] queens) {
        boolean[] unsafeRow = new boolean[n];
        boolean[] unsafeCol = new boolean[n];
        boolean[] unsafeUR = new boolean[2 * n];
        boolean[] unsafeDR = new boolean[2 * n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(queens[i][j]) {
                    unsafeRow[i] = true;
                    unsafeCol[j] = true;
                    unsafeDR[n - i + j] = true;
                    unsafeUR[i + j] = true;
                }
            }
        }
        int count = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(!(unsafeRow[i] || unsafeCol[j] || unsafeDR[n - i + j] || unsafeUR[i + j])) {
                    count++;
                }
            }
        }
        return count;
    }
    
    private static int[][] knightMoves = {
        {2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}
    };
    
    public int countKnightMoves(int n, boolean[][] knights) {
        int count = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(knights[i][j]) {
                    for(int d = 0; d < 8; d++) {
                        int ii = i + knightMoves[d][0];
                        int jj = j + knightMoves[d][1];
                        if(inside(ii, jj, n) && !knights[ii][jj]) { count++; }
                    }
                }
            }
        }
        return count;
    }
    
    public int countPawnMoves(int n, char[][] board) {
        int count = 0;
        for(int d = -1; d <= 1; d += 2) {
            char hero = (d == -1 ? 'w': 'b');
            char oppo = (d == -1 ? 'b': 'w');
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++) {
                    if(board[i][j] == hero) {
                        int ii = i + d;
                        if(inside(ii, j - 1, n) && board[ii][j - 1] == oppo) { count++; }
                        if(inside(ii, j + 1, n) && board[ii][j + 1] == oppo) { count++; }
                        if(inside(ii, j, n) && board[ii][j] == ' ') { count++; }
                    }
                }
            }
            
        }
        return count;
    } 
}