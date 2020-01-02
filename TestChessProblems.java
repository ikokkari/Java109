import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.zip.Adler32;

public class TestChessProblems {

    private static final int SEED = 12345;
    private ChessProblems mp = new ChessProblems();
    
    @Test
    public void testCountSafeSquaresRooks() {
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        int total = 0, answer;
        LinkedList<Integer> qxl = new LinkedList<Integer>();
        LinkedList<Integer> qyl = new LinkedList<Integer>();
        for(int n = 3; n < 100; n++) {
            boolean[][] board = new boolean[n][n];
            int count = 1;
            for(int trials = 0; trials < n + 1; trials++) {
                answer = mp.countSafeSquaresRooks(n, board);
                total += answer;
                check.update(answer);
                int nx, ny;
                for(int i = 0; i < count; i++) {
                    do { // find a square that does not have a rook yet
                        nx = rng.nextInt(n);
                        ny = rng.nextInt(n);
                    } while(board[nx][ny]);
                    board[nx][ny] = true;
                    qxl.add(nx);
                    qyl.add(ny);
                    answer = mp.countSafeSquaresRooks(n, board);
                    total += answer;
                    check.update(answer);
                }
                for(int i = 0; i < count - 1; i++) {
                    nx = qxl.removeFirst();
                    ny = qyl.removeFirst();
                    board[nx][ny] = false;
                    answer = mp.countSafeSquaresRooks(n, board);
                    total += answer;
                    check.update(answer);
                }
                count++;
            }
        }
        assertEquals(23172158, total);
        assertEquals(3601595543L, check.getValue());
    }
    
    @Test
    public void testCountSafeSquaresQueens() {
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        int total = 0, answer;
        LinkedList<Integer> qxl = new LinkedList<Integer>();
        LinkedList<Integer> qyl = new LinkedList<Integer>();
        for(int n = 3; n < 30; n++) {
            boolean[][] board = new boolean[n][n];
            int count = 1;
            for(int trials = 0; trials < (n * n) / 20 + 3; trials++) {
                answer = mp.countSafeSquaresQueens(n, board);
                total += answer;
                check.update(answer);
                int nx, ny;
                for(int i = 0; i < count; i++) {
                    do { // find a square that does not have a queen yet
                        nx = rng.nextInt(n);
                        ny = rng.nextInt(n);
                    } while(board[nx][ny]);
                    board[nx][ny] = true;
                    qxl.add(nx);
                    qyl.add(ny);
                    answer = mp.countSafeSquaresQueens(n, board);
                    total += answer;
                    check.update(answer);
                }
                for(int i = 0; i < count - 1; i++) {
                    nx = qxl.removeFirst();
                    ny = qyl.removeFirst();
                    board[nx][ny] = false;
                    answer = mp.countSafeSquaresQueens(n, board);
                    total += answer;
                    check.update(answer);
                }
                count++;
            }
        }
        assertEquals(100838, total);
        assertEquals(3505386471L, check.getValue());
    }
    
    @Test
    public void testCountKnightMoves() {
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        int total = 0, answer;
        LinkedList<Integer> kxl = new LinkedList<Integer>();
        LinkedList<Integer> kyl = new LinkedList<Integer>();
        for(int n = 3; n < 25; n++) {
            boolean[][] board = new boolean[n][n];
            int count = 1;
            for(int trials = 0; trials < (n * n) / 8; trials++) {
                answer = mp.countKnightMoves(n, board);
                total += answer;
                check.update(answer);
                int nx, ny;
                for(int i = 0; i < count; i++) {
                    do { // find a square that does not have a knight yet
                        nx = rng.nextInt(n);
                        ny = rng.nextInt(n);
                    } while(board[nx][ny]);
                    board[nx][ny] = true;
                    kxl.add(nx);
                    kyl.add(ny);
                    answer = mp.countKnightMoves(n, board);
                    total += answer;
                    check.update(answer);
                }
                for(int i = 0; i < count - 1; i++) {
                    nx = kxl.removeFirst();
                    ny = kyl.removeFirst();
                    board[nx][ny] = false;
                    answer = mp.countKnightMoves(n, board);
                    total += answer;
                    check.update(answer);
                }
                count++;
            }
        }
        assertEquals(14630390, total);
        assertEquals(2101292833L, check.getValue());
    }
    
    @Test
    public void testCountPawnMoves() {
        char[] pieces = {'b', 'b', 'w', 'w', ' '};
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        int total = 0, answer;
        for(int n = 3; n < 25; n++) {
            char[][] board = new char[n][n];
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++) {
                    board[i][j] = ' ';
                }
            }
            for(int c = 0; c < n * n * n; c++) {
                int i = rng.nextInt(n);
                int j = rng.nextInt(n);
                board[i][j] = pieces[rng.nextInt(pieces.length)];
                answer = mp.countPawnMoves(n, board);
                total += answer;
                check.update(answer);
            }
        }
        assertEquals(25063550, total);
        assertEquals(1049171076L, check.getValue());
    }
}