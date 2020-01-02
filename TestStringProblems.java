import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import java.io.*;
import java.util.zip.Adler32;

public class TestStringProblems {

    private static final int RUNS = 100000;
    private static final int SEED = 12345;
    private StringProblems sp = new StringProblems();
    
    @Test
    public void testCountWords() {
        BufferedReader br = null;
        Adler32 check = new Adler32();
        int count = 0;
        try {
            br = new BufferedReader(new FileReader("warandpeace.txt"));
            String line = br.readLine();
            while(line != null) {
                int words = sp.countWords(line.trim());
                count += words;
                check.update(words);
                line = br.readLine(); 
            }
        } catch(IOException e) { System.out.println("Error: " + e); assertTrue(false); }
        finally { try { br.close(); } catch(Exception e) { } }
        assertEquals(562491, count); // number of words in War and Peace
        assertEquals(2309395892L, check.getValue()); // checksum of word counts
    }
    
    @Test
    public void testRemoveDuplicates() {
        Adler32 check = new Adler32();
        java.util.Random rng = new java.util.Random(SEED);
        for(int i = 0; i < RUNS; i++) {
            StringBuilder sb = new StringBuilder();
            int len = rng.nextInt(500);
            for(int j = 0; j < len; j++) {
                char c = (char)(1 + rng.nextInt(50000));
                int rep = rng.nextInt(10) + 1;
                for(int k = 0; k < rep; k++) {
                    sb.append(c);
                }
            }
            check.update(sp.removeDuplicates(sb.toString()).getBytes());
        }
        assertEquals(459133821L, check.getValue());
    }
    
    @Test
    public void testConvertToTitleCase() {
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        String whitespaces = " \t\n";
        for(int i = 0; i < RUNS; i++) {
            StringBuilder sb = new StringBuilder();
            int len = rng.nextInt(500);
            for(int j = 0; j < len; j++) {
                char c = (char)(rng.nextInt(50000));
                int rep = rng.nextInt(10) + 1;
                for(int k = 0; k < rep; k++) {
                    sb.append(c);
                }
                sb.append(whitespaces.charAt(rng.nextInt(3)));
            }
            check.update(sp.convertToTitleCase(sb.toString()).getBytes());
        }
        assertEquals(2451295251L, check.getValue());
    }
  
    private char randomChar(Random rng) {
        return (char)(rng.nextInt(200) + 97);
    }
    
    private String buildString(Random rng, int len) {
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < len; j++) {
            sb.append(randomChar(rng));
        }
        return sb.toString();
    }
    
    @Test
    public void testUniqueCharacters() {
        java.util.Random rng = new java.util.Random(SEED);
        Adler32 check = new Adler32();
        for(int i = 0; i < RUNS; i++) {
            int len = rng.nextInt(100) + (2 << rng.nextInt(5));
            String s = buildString(rng, len);
            String res = sp.uniqueCharacters(s);
            check.update(res.getBytes());
        }    
        assertEquals(464461313L, check.getValue());
    }
}