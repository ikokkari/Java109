import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.zip.Adler32;

public class TestArrayShapeProblems {

    private static final int SEED = 12345;
    private static final int RUNS = 200;
    private ArrayShapeProblems map = new ArrayShapeProblems();
    
    @Test
    public void testCountUpsteps() {
        Random rng = new Random(SEED);
        Adler32 check = new Adler32();
        for(int i = 1; i < RUNS; i++) {
            int[] a = new int[i];
            for(int j = 0; j < a.length; j++) {
                a[j] = rng.nextInt();
            }
            int res = map.countUpsteps(a);
            check.update((res) & 0xFF);
            check.update((res >> 8) & 0xFF);
            check.update((res >> 16) & 0xFF);
            check.update((res >> 24) & 0xFF);
        }
        assertEquals(3248301701L, check.getValue());
    }
    
    private static int[] signs = { -1, 0, 1 };
    
    @Test
    public void testSameStepShape() {
        Random rng = new Random(SEED);
        for(int i = 1; i < RUNS; i++) {
            int[] a = new int[i];
            int[] b = new int[i];
            for(int j = 0; j < RUNS; j++) {
                for(int l = 1; l < a.length; l++) {
                    int sign = signs[rng.nextInt(3)];
                    a[l] = a[l-1] + sign*(rng.nextInt(20)+10);
                    b[l] = b[l-1] + sign*(rng.nextInt(20)+10);
                }
                assertTrue(map.sameStepShape(a,b));
                for(int l = 1; l < b.length - 1; l++) {
                    if(b[l] != b[l-1]) {
                        int tmp = b[l];
                        b[l] = b[l-1];
                        b[l-1] = tmp;
                        assertTrue(!map.sameStepShape(a,b));
                        tmp = b[l];
                        b[l] = b[l-1];
                        b[l-1] = tmp;
                    }
                }
            }
        }
    }
    
    @Test
    public void testIsSawtooth() {
        Random rng = new Random(SEED);
        for(int i = 1; i < RUNS; i++) {
            int[] a = new int[i]; int tmp;
            for(int j = 0; j < RUNS; j++) {
                a[0] = rng.nextInt(100000) - 50000;
                int sign = rng.nextBoolean() ? +1 : -1;
                for(int l = 1; l < a.length; l++) {
                    a[l] = a[l-1] + sign * (rng.nextInt(10) + 1);
                    sign = -sign; 
                }
                assertTrue(map.isSawtooth(a));
            }
            for(int l = 1; l < a.length-1; l++) {
                tmp = a[l];
                a[l] = a[l-1];
                assertTrue(!map.isSawtooth(a));
                a[l] = tmp;
                tmp = a[l];
                a[l] = (a[l-1]+a[l+1])/2;
                assertTrue(!map.isSawtooth(a));
                a[l] = tmp;
            }
        }
    }
    
    @Test
    public void testIsMountain() {
        Random rng = new Random(SEED);
        for(int i = 5; i < RUNS; i++) {
            int[] a = new int[i];
            for(int j = 0; j < RUNS; j++) {
                a[0] = rng.nextInt(100000) - 50000;
                int k = rng.nextInt(i);
                for(int l = 1; l <= k; l++) {
                    a[l] = a[l-1] + rng.nextInt(10) + 1;
                }
                for(int l = k + 1; l < a.length; l++) {
                    a[l] = a[l-1] - rng.nextInt(10) - 1;
                }
                assertTrue(map.isMountain(a));
                for(int l = 0; l < i - 1; l++) {
                    if(l < k - 1) {
                        a[l] += 20;
                        assertFalse(map.isMountain(a));
                        a[l] -= 40;
                        assertFalse(l > 0 && map.isMountain(a));
                        a[l] += 20;
                    }
                    else if (l == k) {
                        a[l] -= 40;
                        assertFalse(l > 0 && l < a.length - 1 && map.isMountain(a));
                        a[l] += 40;
                    }
                    else if (l > k + 1) {
                        a[l] += 20;
                        assertFalse(map.isMountain(a));
                        a[l] -= 40;
                        assertFalse(l < a.length - 1 && map.isMountain(a));
                        a[l] += 20;
                    }
                }
            }
        }
    }
}