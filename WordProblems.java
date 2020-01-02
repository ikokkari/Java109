import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.*;

public class WordProblems {

    // Solution that uses indexOf method to check if character is inside the pattern.
    public ArrayList<String> findMatchingWords(ArrayList<String> words, String pattern) {
        ArrayList<String> result = new ArrayList<>();
        for(String word: words) {
            if(word.length() != pattern.length()) { continue; }
            boolean isGood = true;
            for(int i = 0; i < word.length(); i++) {
                char cw = word.charAt(i);
                char cp = pattern.charAt(i);
                if(cp == '?' && pattern.indexOf(cw) > -1) {
                    isGood = false; break; 
                }
                if(cp != '?' && cp != cw) {
                    isGood = false; break;
                }
            }
            if(isGood) { result.add(word); }
        }
        return result;
    }

    // Another solution that precomputes boolean array of characters that occur in pattern.
    public ArrayList<String> findMatchingWordsArray(ArrayList<String> words, String pattern) {
        boolean[] occur = new boolean[26];
        for(int i = 0; i < pattern.length(); i++) {
            char cp = pattern.charAt(i);
            if(cp != '?') { occur[cp - 'a'] = true; }
        }
        ArrayList<String> result = new ArrayList<>();
        for(String word: words) {
            if(word.length() != pattern.length()) { continue; }
            boolean isGood = true;
            for(int i = 0; i < word.length(); i++) {
                char cw = word.charAt(i);
                char cp = pattern.charAt(i);
                if(cp == '?' && occur[cw - 'a']) {
                    isGood = false; break; 
                }
                if(cp != '?' && cp != cw) {
                    isGood = false; break;
                }
            }
            if(isGood) { result.add(word); }
        }
        return result; 
    }
    
    // Another solution that uses regular expressions not taught in this course.
    public ArrayList<String> findMatchingWordsRegex(ArrayList<String> words, String pattern) {
        String pr = "[^9"; // dummy '9' character guarantees that class is nonempty
        boolean[] occur = new boolean[26];
        for(int i = 0; i < pattern.length(); i++) {
            if(pattern.charAt(i) != '?') { occur[pattern.charAt(i) - 'a'] = true; }
        }
        for(int i = 0; i < 26; i++) {
            if(occur[i]) { pr += (char)('a' + i); }
        }
        pr += "]";
        String pat = "";
        for(int i = 0; i < pattern.length(); i++) {
            if(pattern.charAt(i) == '?') { pat += pr; }
            else { pat += pattern.charAt(i); }
        }
        // When you match against same pattern many times, it is good to compile it.
        Pattern p = Pattern.compile("^" + pat + "$");
        ArrayList<String> result = new ArrayList<>();
        for(String word: words) {
            Matcher m = p.matcher(word);
            if(m.matches()) { result.add(word); }
        }
        return result;
    }

    // First solution, following the hint that uses a counter array.

    public ArrayList<String> findAnagrams(ArrayList<String> words, String word) {
        int[] count = new int[26];
        ArrayList<String> result = new ArrayList<>();
        outer:
        for(String w: words) {
            if(w.length() == word.length()) {
                java.util.Arrays.fill(count, 0);
                for(int i = 0; i < word.length(); i++) {
                    count[word.charAt(i) - 'a']++;
                    count[w.charAt(i) - 'a']--;
                }
                for(int i = 0; i < 26; i++) {
                    if(count[i] != 0) { continue outer; } 
                }
                result.add(w);
            }
        }
        return result;
    }

    // Two words are anagrams if sorting their characters produces the same result.

    public ArrayList<String> findAnagramsSorting(ArrayList<String> words, String word) {
        char[] wc = word.toCharArray();
        java.util.Arrays.sort(wc);
        String sorted = new String(wc);
        ArrayList<String> result = new ArrayList<>();
        for(String w : words) {
            if(word.length() == w.length()) {
                wc = w.toCharArray();
                java.util.Arrays.sort(wc);
                String sw = new String(wc);
                if(sorted.equals(sw)) { result.add(w); }
            }
        }
        return result;
    }

    // A more clever way uses the fact that every integer has a unique prime factorization.
    // Therefore encoding each word from [a-z]+ into this product of primes gives each
    // anagram the same integer code that we can use to quickly compare whether two given
    // words are anagrams.

    // First 26 prime numbers that we use to encode letters a-z
    private static final int[] primes = { 
            2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47,
            53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101
        };

    private static final long godel(String word) {
        long result = 1;
        for(int i = 0; i < word.length(); i++) {
            result *= primes[word.charAt(i) - 'a'];
        }
        return result;
    }

    public ArrayList<String> findAnagramsGodel(ArrayList<String> words, String word) {
        long g = godel(word);
        ArrayList<String> result = new ArrayList<>();
        for(String w2: words) {
            long g2 = godel(w2);
            if(g == g2) { result.add(w2); }
        }
        return result;
    }

    public ArrayList<String> findSemordnilaps(ArrayList<String> words) {
        ArrayList<String> result = new ArrayList<>();
        for(String word: words) {
            String rev = new StringBuilder(word).reverse().toString();
            if(rev.equals(word)) { continue; }
            int idx = Collections.binarySearch(words, rev);
            if(idx > -1 && words.get(idx).equals(rev)) { 
                result.add(rev);
            }
        }
        return result;
    }
    
    public ArrayList<String> findOneLessWords(ArrayList<String> words, String word) {
        ArrayList<String> result = new ArrayList<>();
        for(int i = 0; i < word.length(); i++) {
            String w = word.substring(0, i) + word.substring(i + 1);
            if(Collections.binarySearch(words, w) > -1 && !result.contains(w)) {
                result.add(w);                
            }                            
        }        
        Collections.sort(result);
        return result;
    }
    
}