public class MoreCardProblems {

    private int getRank(char rank) {
        return 2 + "23456789TJQKA".indexOf(rank);
    }
    
    public int countInternalPairs(String hand) {
        int count = 0;
        for(int i = 0; i < 8; i += 2) {
            for(int j = i + 2; j < 10; j += 2) {
                if(hand.charAt(i) == hand.charAt(j)) { count++; }
            }
        }
        return count;
    }
    
    public int bridgePointCount(String hand) {
        String cards = "JQKA";
        int count = 0;
        for(int i = 0; i < 26; i += 2) {
            int idx = cards.indexOf(hand.charAt(i));
            if(idx > -1) { count += (idx + 1); }
        }
        return count;
    }
    
    public String bridgeHandShape(String hand) {
        int spades = 0, hearts = 0, diamonds = 0, clubs = 0;
        for(int i = 1; i < 26; i +=2) {
            char suit = hand.charAt(i);
            if(suit == 's') { spades++; }
            else if(suit == 'h') { hearts++; }
            else if(suit == 'd') { diamonds++; }
            else { clubs++; } // unconditional, since only four different suits exist
        }
        return spades + ", " + hearts + ", " + diamonds + ", " + clubs;
    }
    
    public int countDeadwood(String hand) {
        int count = 0;
        for(int i = 0; i < hand.length(); i += 2) {
            int idx = getRank(hand.charAt(i));
            if(idx == 14) { idx = 1; }
            if(idx > 10) { idx = 10; }
            count += idx;
        }
        return count;
    }
}