public class RecursionProblems {

    public boolean allEqual(int[] a, int start, int end) {
        if(start >= end) { return true; }
        return a[start] == a[end] && allEqual(a, start+1, end);
    }
    
    public void arraycopy(double[] src, int start, double[] tgt, int start2, int len) {
        if(len < 1) { return; }
        tgt[start2] = src[start];
        arraycopy(src, start + 1, tgt, start2 + 1, len - 1);
    }
    
    public boolean linearSearch(int[] a, int x, int start, int end) {
        if(start > end) { return false; }
        return a[start] == x || linearSearch(a, x, start + 1, end);
    }
    
    public void reverse(int[] a, int start, int end) {
        if(start >= end) { return; }
        int tmp = a[start];
        a[start] = a[end];
        a[end] = tmp;
        reverse(a, start + 1, end - 1);
    }
    
    public void parityPartition(int[] arr, int start, int end) {
        if(start >= end) { return; }
        if(arr[start] % 2 != 0) { parityPartition(arr, start + 1, end); }
        else {    
            int tmp = arr[start]; // Swap the first and last element
            arr[start] = arr[end];
            arr[end] = tmp;
            parityPartition(arr, start, end - 1); // Last element is now even
        }
    }
    
    // A longer solution that makes fewer moves, which was not required here.
    public void parityPartitionFewerMoves(int[] arr, int start, int end) {
        if(start >= end) { return; }
        if(arr[start] % 2 != 0) { parityPartition(arr, start + 1, end); }
        else if(arr[end] % 2 == 0) { parityPartition(arr, start, end - 1); }
        else {
            int tmp = arr[start];
            arr[start] = arr[end];
            arr[end] = tmp;
            parityPartition(arr, start + 1, end - 1);
        }
    }
    
    public int countRuns(int[] arr, int start, int end) {
        if(start > end) { return 0; }
        if(start == end) { return 1; }
        return (arr[start] == arr[start + 1] ? 0 : 1) + countRuns(arr, start + 1, end); 
    }
}