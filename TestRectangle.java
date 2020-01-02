import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.zip.Adler32;

public class TestRectangle {
   
    private static final int TRIALS = 100000;
    private int SEED = 54321;
    
    @Test
    public void testAccessors() {
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        for(int i = 0; i < TRIALS; i++) {
            int w = rng.nextInt(100);
            int h = rng.nextInt(100);
            Rectangle r = new Rectangle(w, h);
            check.update(r.getWidth());
            check.update(r.getHeight());
        }
        assertEquals(266466591L, check.getValue()); 
    }
    
    @Test
    public void testArea() {
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        for(int i = 0; i < TRIALS; i++) {
            int w = rng.nextInt(100);
            int h = rng.nextInt(100);
            Rectangle r = new Rectangle(w, h);
            check.update(r.getArea());
        }
        assertEquals(3824146142L, check.getValue());
    }
    
    @Test
    public void testPerimeter() {
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        for(int i = 0; i < TRIALS; i++) {
            int w = rng.nextInt(100);
            int h = rng.nextInt(100);
            Rectangle r = new Rectangle(w, h);
            check.update(r.getPerimeter());
        }
        assertEquals(3207742079L, check.getValue());
    }
    
    @Test
    public void testFlip() {
        java.util.Random rng = new java.util.Random(SEED);
        for(int i = 0; i < TRIALS; i++) {
            int w = rng.nextInt(100);
            int h = rng.nextInt(100);
            Rectangle r = new Rectangle(w, h);
            r.flip();
            assertEquals(w, r.getHeight());
            assertEquals(h, r.getWidth());
        }
    }
}