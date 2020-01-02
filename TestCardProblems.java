import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.zip.Adler32;

public class TestCardProblems {

    private static final int SEED = 12345;
    private static final int RUNS = 100000;
    private static final int SIZE = 100;
    private CardProblems cp = new CardProblems();
    
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
    public void testHasFlush() {
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        for(int i = 0; i < RUNS; i++) {
            String hand = createHand(rng, 5);
            check.update(cp.hasFlush(hand) ? i : ~i);
        }
        assertEquals(1990048361L, check.getValue());
    }
    
    @Test
    public void testFourOfAKind() {
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        for(int i = 0; i < RUNS; i++) {
            String hand = createHand(rng, 5);
            check.update(cp.hasFourOfAKind(hand) ? i : ~i);
        }
        assertEquals(3699029383L, check.getValue());
    }
    
    @Test
    public void testGetRank() {
        assertEquals(2, cp.getRank('2'));
        assertEquals(3, cp.getRank('3'));
        assertEquals(4, cp.getRank('4'));
        assertEquals(5, cp.getRank('5'));
        assertEquals(6, cp.getRank('6'));
        assertEquals(7, cp.getRank('7'));
        assertEquals(8, cp.getRank('8'));
        assertEquals(9, cp.getRank('9'));
        assertEquals(10, cp.getRank('T'));
        assertEquals(11, cp.getRank('J'));
        assertEquals(12, cp.getRank('Q'));
        assertEquals(13, cp.getRank('K'));
        assertEquals(14, cp.getRank('A'));
    }
    
    @Test
    public void testHasFourCardBadugi() {
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        for(int i = 0; i < RUNS; i++) {
            String hand = createHand(rng, 4);
            check.update(cp.hasFourCardBadugi(hand) ? i : ~i);
        }
        assertEquals(2525277488L, check.getValue());
    }
}