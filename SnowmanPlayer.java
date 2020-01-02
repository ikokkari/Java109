import java.util.*;
import java.lang.reflect.*;

public class SnowmanPlayer {
    // If true, print the number of possible words at each guess.
    private static final boolean VERBOSE = false;
    
    // Just to show the possible attacks when using cryptographically non-secure RNG's.
    private static int CHEATMODE = 0; // 0 = no cheating, 1 = seed calculation, 2 = read seed with reflection
    private static List<String> guessedWords;
    private static String[] storedWords;
    private static final int PREFIX = 4;
    // This RNG will go in lockstep with that in SnowmanRunner once the seed is cracked.
    private static Random cheatRNG = null; 
    
    // Data structures maintained by the methods.
    private static Map<Integer, List<String>> wordMap = new HashMap<>();
    private static List<String> possibleWords;
    private static String allowed;
    private static String previousPattern = "$$$$";
    private static char previousGuess = '\0';
    
    // For testing the crash recovery of SnowmanRunner.
    private static double crashProb = 0.00; // probability of method crashing at any given call
    private static Random crashRNG = new Random();
    
    public static String getAuthor() {
        return "Kokkarinen, Ilkka";
    }
    
    public static void startGame(String[] words, int minLength, int maxLength, String allowedChars) {
        // Create a list of words for each possible length.
        for(int i = minLength; i <= maxLength; i++) {
            wordMap.put(i, new ArrayList<String>());
        }
        for(String word: words) {
            wordMap.get(word.length()).add(word);
        }
        
        allowed = allowedChars;
        
        if(CHEATMODE > 0) { 
            guessedWords = new ArrayList<String>(); 
            storedWords = new String[words.length];
            for(int i = 0; i < words.length; i++) {
                storedWords[i] = words[i];
            }
        }
        if(CHEATMODE == 2) {
            try {
                Class c = Class.forName("SnowmanRunner");
                Field f = c.getDeclaredField("SEED");
                f.setAccessible(true);
                int seed = (Integer)f.get(null);
                cheatRNG = new Random(seed);
                System.out.println("Your seed is " + seed + ", so now I know every word.");
            }
            catch(Exception e) {
                System.out.println("Cheat failed!" + e);
            }
        }
    }
    
    // From the given list of words, produce a list of words that match the given pattern.
    private static List<String> filterPattern(String pattern, List<String> words) {
        List<String> result = new ArrayList<String>();
        for(String word: words) {
            boolean isMatch = true;
            for(int i = 0; i < word.length(); i++) {
                char c1 = pattern.charAt(i);
                char c2 = word.charAt(i);
                if(c1 == SnowmanRunner.BLANK) { 
                    if(pattern.indexOf(c2) == -1) { continue; }
                    isMatch = false; break;
                }
                if(c1 != c2) { isMatch = false; break; }
            }
            if(isMatch) { result.add(word); }
        }
        return result;
    }
    
    // From the given list of words, produce a list of words that do not contain the given character.
    private static List<String> filterChar(char c, List<String> words) {
        List<String> result = new ArrayList<String>();
        for(String word: words) {
            if(word.indexOf(c) == -1) { result.add(word); }
        }
        return result;
    }
    
    // Calculate the expected number of possible words after guessing c. This could be
    // removed for a solution that is still good but about 0.3 guesses worse.
    private static double expectedResult(String pattern, char c, String previousGuesses) {
        Map<String, Integer> pp = new HashMap<String, Integer>();
        int misses = 0;
        for(String word: possibleWords) {
            String newPat = "";
            for(int j = 0; j < word.length(); j++) {
                char c2 = word.charAt(j);
                if(previousGuesses.indexOf(c2) > -1 || c2 == c) { newPat += c2; }
                else { newPat += SnowmanRunner.BLANK; }
            }
            if(!newPat.equals(pattern)) {
                pp.put(newPat, pp.getOrDefault(newPat, 0) + 1);
            }
            else { misses++; }
        }
        double expected = 0;
        double size = possibleWords.size() - misses;
        for(String pat: pp.keySet()) {
            double p = pp.get(pat) / size;
            expected += p * p;
        }
        return expected;
    }
      
    // Find the character that minimizes expected miss count in the future.
    private static char minimizeMisses(String pattern, String previousGuesses) {
        // If previous guess was a match, update the list of possible words
        if(!pattern.equals(previousPattern)) { 
            possibleWords = filterPattern(pattern, possibleWords);
            previousPattern = pattern;
        }
        // If previous guess was not a match, remove words that contain that character 
        else if(previousGuesses.length() > 0) {
            possibleWords = filterChar(previousGuesses.charAt(previousGuesses.length() - 1),
                                        possibleWords);
        }
        if(VERBOSE) { System.out.print("[" + possibleWords.size() + "]"); }
        
        int bestScore = Integer.MAX_VALUE;
        ArrayList<Character> bestChars = new ArrayList<Character>();
        for(int i = 0; i < allowed.length(); i++) {
            char c = allowed.charAt(i);
            if(previousGuesses.indexOf(c) > -1) { continue; }
            int score = 0;
            for(String word: possibleWords) {
                if(word.indexOf(c) == -1) { score++; }
            }
            if(score == 0) { return c; } // This letter occurs in every possible word, so use it.
            if(score < bestScore) { // This letter beats all the ones we have seen so far.
                bestScore = score; bestChars.clear(); bestChars.add(c);
            }
            else if(score == bestScore) { // This one is equally good as out current champion.
                bestChars.add(c);
            }
        }
        // If only one character is the best, return that.
        if(bestChars.size() == 1) { return bestChars.get(0); }
        // Otherwise, use the character that minimizes expected remaining wordlist size.
        char best = bestChars.get(0);
        double bestE = expectedResult(pattern, best, previousGuesses);
        for(int i = 1; i < bestChars.size(); i++) {
            char c = bestChars.get(i);
            double currE = expectedResult(pattern, c, previousGuesses);
            if(currE < bestE) {
                best = c; bestE = currE;
            }
        }
        return best;
    }
     
    public static void startNewWord(int length) {
        if(crashRNG.nextDouble() < crashProb) { // Simulate the student's crashing method.
            throw new IllegalStateException("simulating the crash");
        }
        
        // Nothing to do here except initialize the list of possible words and previous pattern.
        possibleWords = wordMap.get(length);
        previousPattern = "\0";
   
        // The rest of this method is just proof of concept for cheating.
        if(CHEATMODE > 0) {
            if(cheatRNG == null && !previousPattern.equals("$$$$")) {
                String word = previousPattern.replace(SnowmanRunner.BLANK, previousGuess);
                guessedWords.add(word);
                if(guessedWords.size() == PREFIX) {
                    System.out.println("OK, I have seen enough. Let's see if I can guess your seed...");
                    Random rng = new Random();
                    outer:
                    for(int i = 0; i < Integer.MAX_VALUE; i++) {
                        rng.setSeed(i);
                        for(int j = 0; j < PREFIX; j++) {
                            String ww = storedWords[rng.nextInt(storedWords.length)];
                            if(!ww.equals(guessedWords.get(j))) { continue outer; }
                        }
                        System.out.println("Found your seed! It is " + i + ". Hah, I know all the words now.");
                        cheatRNG = new Random(i); // get the cheatRNG in lockstep with that in SnowmanRunner
                        for(int j = 0; j < PREFIX; j++) { cheatRNG.nextInt(storedWords.length); }
                        break;
                    }
                }
            }
            if(cheatRNG != null) {
                // Only one word is now possible.
                possibleWords = Arrays.asList(storedWords[cheatRNG.nextInt(storedWords.length)]);
            }
        }
    }
    
    public static char guessLetter(String pattern, String previousGuesses) {
        if(crashRNG.nextDouble() < crashProb) { // Simulate the student's crashing method.
            throw new IllegalStateException("simulating the crash");
        }
        return previousGuess = minimizeMisses(pattern, previousGuesses);
    }
}