import java.util.*;

public class ArrayProblems {
    
    public int[] everyOther(int[] a) {
        int len = a.length / 2 + (a.length % 2);
        int[] b = new int[len];
        for(int i = 0; i < a.length; i += 2) {
            b[i/2] = a[i];
        }
        return b;
    }
    
    public int countInversions(int[] a) {
        int count = 0;
        for(int i = 0; i < a.length - 1; i++) {
            for(int j = i + 1; j < a.length; j++) {
                if(a[i] > a[j]) count++;
            }
        }
        return count;
    }
        
    // Solution using a second temporary array.
    public void squeezeLeft2(int[] a) {
        int[] b = new int[a.length];
        int loc = 0;
        for(int e: a) {
            if(e != 0) {
                b[loc++] = e;
            }
        }
        System.arraycopy(b, 0, a, 0, a.length);
    }
    
    // The one loop in place challenge.
    public void squeezeLeft(int[] a) {
        int loc = 0;
        for(int i = 0; i < a.length; i++) {
            if(a[i] != 0) {
                if(loc < i) { a[loc] = a[i]; a[i] = 0; }
                loc++;
            }
        }
    }
    
    public int[] runDecode(int[] a) {
        int len = 0, loc = 0;
        for(int i = 0; i < a.length; i += 2) {
            len += a[i];
        }
        int[] b = new int[len];
        for(int i = 0; i < a.length; i += 2) {
            for(int j = 0; j < a[i]; j++) {
                b[loc++] = a[i+1];
            }
        }
        return b;
    }
}