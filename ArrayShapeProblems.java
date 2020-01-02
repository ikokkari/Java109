
public class ArrayShapeProblems {

    public int countUpsteps(int[] a) {
        int count = 0;
        for(int i = 0; i < a.length-1; i++) {
            if(a[i] < a[i+1]) { count++; }
        }
        return count;
    }
    
    public boolean sameStepShape(int[] a, int[] b) {
        for(int i = 0; i < a.length-1; i++) {
            if(a[i] < a[i+1] && b[i] < b[i+1]) { continue; }
            if(a[i] > a[i+1] && b[i] > b[i+1]) { continue; }
            if(a[i] == a[i+1] && b[i] == b[i+1]) { continue; }
            return false;
        }
        return true;
    }
 
    // A "state machine" spirited solution that can handle the edge cases
    // and the last element of the array in a more elegant fashion.
    public boolean isSawtooth(int[] a) {
        int prev = 0; // The type of the previous step, initially "plateau"
        for(int i = 1; i < a.length; i++) {
            if(a[i] == a[i-1]) { return false; } // Plateau, so that's it
            int step = (a[i] > a[i-1]) ? +1 : -1; // Shape of current step
            if(step == prev) { return false; } // If same as previous step, that's it
            prev = step;
        }
        return true; // We did not need special rules for lengths < 2 or the last element
    }
    
    public boolean isMountain(int[] a) {
        boolean goneDown = false;
        for(int i = 0; i < a.length-1; i++) {
            if(a[i] == a[i+1]) { return false; } // No plateaus allowed
            if(a[i] > a[i+1]) { goneDown = true; } // Once you have gone down...
            else if(goneDown) { return false;} // ... you are not allowed to go up again
        }
        return true;
    }
}