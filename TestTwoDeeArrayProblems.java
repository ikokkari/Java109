import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.zip.Adler32;

public class TestTwoDeeArrayProblems {

    private static final int SEED = 12345;
    private static final int RUNS = 1000;
    private static final int SIZE = 100;
    private TwoDeeArrayProblems ap = new TwoDeeArrayProblems();
  
    @Test
    public void testMinValues() {
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        for(int i = 0; i < RUNS; i++) {
            int rows = rng.nextInt(SIZE) + 1;
            double[][] a = new double[rows][1];
            for(int j = 0; j < rows; j++) {
                int cols = rng.nextInt(30);
                a[j] = new double[cols];
                for(int k = 0; k < cols; k++) {
                    a[j][k] = rng.nextDouble() * 100 - 50;
                }
            }
            double[] b = ap.minValues(a);
            assertEquals(rows, b.length);
            for(int j = 0; j < rows; j++) {
                long x = Double.doubleToRawLongBits(b[j]);
                check.update((int)x);
                check.update((int)(x >> 32));
            }
        }
        assertEquals(1342149396L, check.getValue());
    }
    
    @Test
    public void testTranspose() {
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        for(int i = 0; i < RUNS; i++) {
            int rows = rng.nextInt(SIZE) + 1;
            int cols = rng.nextInt(SIZE) + 1;
            double[][] a = new double[rows][cols];
            for(int j = 0; j < rows; j++) {
                for(int k = 0; k < cols; k++) {
                    a[j][k] = rng.nextDouble() * 100 - 50;
                }
            }
            double[][] b = ap.transpose(a);
            assertEquals(cols, b.length);
            for(int j = 0; j < cols; j++) {
                assertEquals(rows, b[j].length);
                for(int k = 0; k < rows; k++) {
                    assertEquals(a[k][j], b[j][k], 0);
                }
            }
        }    
    }
    
    @Test
    public void testZigZag() {
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        for(int i = 0; i < RUNS; i++) {
            int rows = rng.nextInt(SIZE) + 1;
            int cols = rng.nextInt(SIZE) + 1;
            int start = rng.nextInt(1000) - 500;
            int[][] a = ap.zigzag(rows, cols, start);
            assertEquals(rows, a.length);
            for(int j = 0; j < rows; j++) {
                assertEquals(cols, a[j].length);
                for(int k = 0; k < cols; k++) {
                    check.update(a[j][k]);
                }
            }
        }
       assertEquals(704566342L, check.getValue());
    }
    
    @Test
    public void testMaximumDistance() {
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        for(int i = 0; i < RUNS; i++) {
            int n = rng.nextInt(SIZE) + 2;
            double[][] a = new double[n][3];
            for(int j = 0; j < n; j++) {
                for(int k = 0; k < 3; k++) {
                    a[j][k] = 100 * rng.nextDouble() - 50;
                }
            }
            double d = ap.maximumDistance(a);
            long x = Double.doubleToRawLongBits(d);
            check.update((int)x);
            check.update((int)(x >> 32));
        }
        assertEquals(2564291135L, check.getValue());
    }
}
