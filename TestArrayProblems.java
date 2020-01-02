import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.zip.Adler32;

public class TestArrayProblems {

    private static final int SEED = 12345;
    private static final int RUNS = 1000;
    private static final int SIZE = 100;
    private ArrayProblems ap = new ArrayProblems();
  
    @Test
    public void testEveryOther() {
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        for(int i = 0; i < RUNS; i++) {
            int len = rng.nextInt(1000);
            int[] a = new int[len];
            for(int j = 0; j < len; j++) {
                a[j] = rng.nextInt();
            }
            check.update(Arrays.toString(ap.everyOther(a)).getBytes());
        }
        assertEquals(3030668394L, check.getValue());
    }
    
    @Test
    public void testCountInversions() {
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        for(int t = 0; t < RUNS; t++) {
            int[] a = new int[SIZE];
            for(int i = 0; i < a.length; i++) {
                a[i] = rng.nextInt(2000) - 1000;
            }
            int inv = ap.countInversions(a);
            check.update((inv) & 0xFF);
            check.update((inv >> 8) & 0xFF);
            check.update((inv >> 16) & 0xFF);
            check.update((inv >> 24) & 0xFF);
        }
        assertEquals(3397916978L, check.getValue());
    }
    
    @Test
    public void testSqueezeLeft() {
        Random rng = new Random(SEED);
        Adler32 check = new Adler32();
        for(int i = 0; i < RUNS; i++) {
            int len = rng.nextInt(10000);
            int[] a = new int[len];
            for(int j = 0; j < len; j++) {
                if(rng.nextBoolean()) { a[j] = rng.nextInt(); }
            }
            ap.squeezeLeft(a);
            check.update(Arrays.toString(a).getBytes());
        }
        assertEquals(749267552L, check.getValue());
    }
    
    @Test
    public void testRunDecode() {
        Adler32 check = new Adler32();
        Random rng = new Random(SEED);
        for(int i = 0; i < RUNS; i++) {
            int len = 2 * rng.nextInt(30) + 2;
            int[] a = new int[len];
            for(int j = 0; j < len; j += 2) {
                a[j] = rng.nextInt(20);
                a[j + 1] = rng.nextInt(); 
            }
            check.update(Arrays.toString(ap.runDecode(a)).getBytes());
        }
        assertEquals(3417776275L, check.getValue());
    }
}