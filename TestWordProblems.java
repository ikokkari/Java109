import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.*;
import java.util.zip.Adler32;

public class TestWordProblems {
    
    private WordProblems wp = new WordProblems();
    private static final int SEED = 12345;
    private static final int MATCHRUNS = 100;
    private static final int ANARUNS = 300;
    private static final int PARTRUNS = 50000;
    private static ArrayList<String> words = new ArrayList<String>();
    
    private static void readWords() {
        if(words.size() > 0) { return; }
        try {
            Scanner s = new Scanner(new File("words_alpha.txt"));
            while(s.hasNextLine()) {
                words.add(s.nextLine());
            }
        } catch(Exception e) {
            System.out.println("Failed to read file 'words.txt': " + e);
        }
        assertTrue(words.size() > 100000);
        System.out.println("Read " + words.size() + " words from words.txt.");
    }
    
    private char randomChar(Random rng, char spec) {
        if(rng.nextDouble() < 0.2) { return spec; }
        else { return (char)('a' + rng.nextInt(26)); }
    }
    
    private String buildString(Random rng, int len, char spec) {
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < len; j++) {
            sb.append(randomChar(rng, spec));
        }
        return sb.toString();
    }
    
    @Test
    public void testFindMatchingWords() {
        readWords();
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        int total = 0;
        for(int i = 0; i < MATCHRUNS; i++) {
            String pat = buildString(rng, 1 + i % 10, '?');
            ArrayList<String> result = wp.findMatchingWords(words, pat);
            total += result.size();
            for(String word: result) {
                check.update(word.getBytes());
            }
        }
        assertEquals(922, total);
        assertEquals(2415131925L, check.getValue());
    }
    
    @Test
    public void testFindAnagrams() {
        readWords();
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        int total = 0;
        for(int i = 0; i < ANARUNS; i++) {
            String pat = words.get(rng.nextInt(words.size()));
            ArrayList<String> result = wp.findAnagrams(words, pat);
            total += result.size();
            for(String word: result) {
                check.update(word.getBytes());
            }
        }    
        assertEquals(429, total);
        assertEquals(3210678597L, check.getValue());
    }
    
    @Test
    public void testFindSemordnilaps() {
        readWords();
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        ArrayList<String> result = wp.findSemordnilaps(words);
        for(String word: result) {
            check.update(word.getBytes());
        }   
        assertEquals(2139, result.size());
        assertEquals(3886907662L, check.getValue());
    }
    
    @Test
    public void testFindOneLessWords() {
        readWords();
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        System.out.println(wp.findOneLessWords(words, "ledger"));
        System.out.println(wp.findOneLessWords(words, "stopper"));
        int total = 0;
        for(int i = 0; i < PARTRUNS; i++) {
            String word = words.get(rng.nextInt(words.size()));
            ArrayList<String> result = wp.findOneLessWords(words, word);
            total += result.size();
            for(String w: result) {
                check.update(w.getBytes());
            }
        } 
        assertEquals(17934, total);
        assertEquals(292781323L, check.getValue());
    }
}