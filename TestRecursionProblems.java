import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import java.util.zip.Adler32;

public class TestRecursionProblems {
    
    private RecursionProblems rp = new RecursionProblems();
    
    private static final int SEED = 12345;
    private static final int RUNS = 500;
    
    @Test
    public void testAllEqual() {
        Random rng = new Random(SEED);
        for(int i = 0; i < RUNS; i++) {
            int[] a = new int[i];
            int v = rng.nextInt();
            for(int j = 0; j < i; j++) {
                a[j] = v;
            }
            assertTrue(rp.allEqual(a, 0, i-1));
            for(int j = 1; j < i; j++) {
                assertTrue(rp.allEqual(a, j, i-1));
                assertTrue(rp.allEqual(a, 0, j));
                a[j] = a[j] - 1;
                assertTrue(rp.allEqual(a, 0, j-1));
                assertTrue(rp.allEqual(a, j + 1, i-1));
                assertFalse(rp.allEqual(a, 0, i-1));
                a[j] = a[j] + 2;
                assertTrue(rp.allEqual(a, 0, j-1));
                assertTrue(rp.allEqual(a, j + 1, i-1));
                assertFalse(rp.allEqual(a, 0, i-1));
                a[j] = a[j] - 1;
            }
        }
    }
    
    private static final int SIZE = 1000;
    @Test
    public void testArrayCopy() {
        Random rng = new Random(SEED);
        double[] a = new double[SIZE];
        double[] b = new double[SIZE];
        double[] c = new double[SIZE];
        int start, start2, len;
        for(int i = 0; i < RUNS; i++) {
            for(int j = 0; j < SIZE; j++) {
                a[j] = rng.nextDouble();
                b[j] = c[j] = rng.nextDouble();
            }
            do {
                start = rng.nextInt(SIZE);
                start2 = rng.nextInt(SIZE);
                len = rng.nextInt(SIZE) / 5;
            } while(start + len >= SIZE || start2 + len >= SIZE);
            // Gold testing, again.
            rp.arraycopy(a, start, b, start2, len);
            System.arraycopy(a, start, c, start2, len);
            assertArrayEquals(b, c, 0);
        }
    }
    
    @Test
    public void testLinearSearch() {
        Random rng = new Random(SEED);
        int[] a = new int[SIZE];
        for(int i = 0; i < RUNS; i++) {
            int x = rng.nextInt();
            for(int j = 0; j < SIZE; j++) {
                do {
                    a[j] = rng.nextInt();
                } while(a[j] == x);
            }
            for(int j = 0; j < SIZE; j += 10) {
                for(int k = j; k < SIZE; k += 10) {
                    assertFalse(rp.linearSearch(a, x, j, k));
                    int tmp = a[k];
                    a[k] = x;
                    assertTrue(rp.linearSearch(a, x, j, SIZE - 1));
                    a[k] = tmp;
                }
            }
        }
    }
    
    private void iterativeReverse(int[] a, int start, int end) {
        while(start < end) {
            int tmp = a[start];
            a[start] = a[end];
            a[end] = tmp;
            start++; end--;
        }
    }
    
    @Test
    public void testReverse() {
        Random rng = new Random(SEED);
        int[] a = new int[SIZE];
        int[] b = new int[SIZE];
        for(int i = 0; i < RUNS; i++) {
            for(int j = 0; j < SIZE; j++) {
                a[j] = b[j] = rng.nextInt();
            }
            for(int j = 0; j < SIZE; j += rng.nextInt(20) + 1) {
                for(int k = j; k < SIZE; k += 10) {
                    rp.reverse(a, j, k);
                    iterativeReverse(b, j, k);
                    assertTrue(Arrays.equals(a, b));
                }
            }
        }
    }
    
    @Test
    public void testParityPartition() {
        Random rng = new Random(SEED);
        int[] a = new int[1000];
        for(int i = 0; i < RUNS; i++) {
            int len = rng.nextInt(1000);
            int sum = 0, evens = 0;
            for(int j = 0; j < len; j++) {
                a[j] = rng.nextInt(100000) - 50000;
                sum += a[j];
                if(a[j] % 2 == 0) { evens++; }
            }
            rp.parityPartition(a, 0, len - 1);
            for(int j = len - 1; j >= 0; j--) {
                sum -= a[j];
                if(evens-- > 0) { assertTrue(a[j] % 2 == 0); }
                else { assertTrue(a[j] % 2 != 0); }
            }
            assertEquals(sum, 0);
        }
    }
    
    @Test
    public void countRuns() {
        Random rng = new Random(SEED);
        Adler32 check = new Adler32();
        int[] a = new int[100];
        for(int i = 0; i < RUNS; i++) {
            int len = rng.nextInt(100);
            for(int j = 0; j < len; j++) {
                a[j] = rng.nextInt(20);
            }
            check.update(rp.countRuns(a, 0, len - 1));
        }
        assertEquals(225598946L, check.getValue());
    }
}