public class TwoDeeArrayProblems {
    
    public double[] minValues(double[][] a) {
        double[] b = new double[a.length];
        for(int i = 0; i < b.length; i++) {
            if(a[i].length > 0) {
                double min = a[i][0];
                for(int j = 1; j < a[i].length; j++) {
                    if(a[i][j] < min) { min = a[i][j]; }
                }
                b[i] = min;
            }
        }
        return b;
    }
    
    public double[][] transpose(double[][] a) {
        double[][] b = new double[a[0].length][a.length];
        for(int i = 0; i < b.length; i++) {
            for(int j = 0; j < b[i].length; j++) {
                b[i][j] = a[j][i];
            }
        }
        return b;
    }

    public int[][] zigzag(int rows, int cols, int start) {
        int[][] a = new int[rows][cols];
        for(int i = 0; i < rows; i++) {
            if(i % 2 == 0) {
                for(int j = 0; j < cols; j++) { a[i][j] = start++; }
            }
            else {
                for(int j = cols - 1; j >= 0 ; j--) { a[i][j] = start++; }
            }
        }
        return a;
    }
   
    private double distance(double[] p1, double[] p2) {
        double sum = 0.0;
        for(int i = 0; i < 3; i++) {
            sum += (p1[i] - p2[i]) * (p1[i] - p2[i]);
        }
        return Math.sqrt(sum);
    }
    
    public double maximumDistance(double[][] pts) {
        double d = 0;
        for(int i = 0; i < pts.length; i++) {
            for(int j = i + 1; j < pts.length; j++) {
                d = Math.max(d, distance(pts[i], pts[j]));
            }
        }
        return d;
    }
}
