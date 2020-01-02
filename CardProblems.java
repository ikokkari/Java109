public class CardProblems {

    public int getRank(char rank) {
        return 2 + "23456789TJQKA".indexOf(rank);
    }
    
    public boolean hasFlush(String hand) {
        for(int i = 3; i <= 9; i += 2) {
            if(hand.charAt(i) != hand.charAt(1)) { return false; }
        }
        return true;
    }
    
    private int countOccurrences(String hand, char c) {
        int sum = 0;
        for(int i = 0; i < hand.length(); i++) {
            if(hand.charAt(i) == c) { sum++; }
        }
        return sum;
    }
    
    public boolean hasFourOfAKind(String hand) {
        return countOccurrences(hand, hand.charAt(0)) == 4 ||
            countOccurrences(hand, hand.charAt(2)) == 4;
    }
    
    public boolean hasFourCardBadugi(String hand) {
        for(int i = 0; i < 8; i++) {
            if(countOccurrences(hand, hand.charAt(i)) > 1) { return false; }
        }
        return true;
    }
}