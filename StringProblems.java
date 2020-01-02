import java.util.HashSet;

public class StringProblems {
    
    public String removeDuplicates(String s) {
        StringBuilder result = new StringBuilder();
        char prev = 0;
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c != prev) { result.append(c); }
            prev = c;
        }
        return result.toString();
    }
    
    public String uniqueCharacters(String text) {
        StringBuilder result = new StringBuilder();
        // My private method used a HashSet<Character> to remember what characters
        // have already been added to the result. Otherwise you would have written
        // this as one nested loop.
        HashSet<Character> hc = new HashSet<Character>();
        for(int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if(!hc.contains(c)) {
                hc.add(c);
                result.append(c);
            }
        }
        return result.toString();
    }
        
    public int countWords(String s) {
        int count = 0;
        // Flag to remember whether previous character was a whitespace.
        boolean prevWhite = true;
        for(int i = 0; i < s.length(); i++) {
            if(Character.isWhitespace(s.charAt(i))) {
                // This character is a whitespace, nothing happens...
                prevWhite = true;
            }
            else {
                // A word starts at non-whitespace character preceded by whitespace.
                if(prevWhite) { count++; }
                prevWhite = false;
            }
        }
        return count;
    }
    
    public String convertToTitleCase(String s) {
        StringBuilder result = new StringBuilder();
        boolean prevWhite = true; // same as in previous method
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean currWhite = Character.isWhitespace(c);
            if(prevWhite && !currWhite) {
                result.append(Character.toTitleCase(c));
            }
            else {
                result.append(c);
            }
            prevWhite = currWhite;
        }
        return result.toString();
    }
}