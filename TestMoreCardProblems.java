import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.zip.Adler32;

public class TestMoreCardProblems {

    private static final int SEED = 12345;
    private static final int RUNS = 100000;
    private MoreCardProblems mcp = new MoreCardProblems();
    
    private String createHand(Random rng, int len) {
        String result = "", card;
        while(result.length() < 2 * len) {            
            card = "" + "23456789TJQKA".charAt(rng.nextInt(13));
            card += "cdhs".charAt(rng.nextInt(4));
            if(!result.contains(card)) { result += card; }
        }
        return result;
    }
    
    @Test
    public void testCountInternalPairs() {
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        for(int i = 0; i < RUNS; i++) {
            String hand = createHand(rng, 5);
            check.update(mcp.countInternalPairs(hand) * i);
        }
        assertEquals(3358079321L, check.getValue());
    }
    
    @Test
    public void testBridgePointCount() {
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        for(int i = 0; i < RUNS; i++) {
            String hand = createHand(rng, 13);
            check.update(mcp.bridgePointCount(hand));
        }
        assertEquals(1146961819L, check.getValue());
    }
    
    @Test
    public void testBridgeHandShape() {
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        for(int i = 0; i < RUNS; i++) {
            String hand = createHand(rng, 13);
            check.update(mcp.bridgeHandShape(hand).getBytes());
        }
        assertEquals(1679153997L, check.getValue());
    }
    
    @Test
    public void testCountDeadwood() {
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        for(int i = 0; i < RUNS; i++) {
            int len = rng.nextInt(10);
            String hand = createHand(rng, len);
            check.update(mcp.countDeadwood(hand));
        }
        assertEquals(3688288563L, check.getValue());
    }
}