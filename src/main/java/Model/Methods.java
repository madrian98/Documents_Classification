package Model;

/**
 N-gram method
 */

public enum Methods {
    N_GRAM {
        @Override
        double compare(String x, String y) {
            double matching = 0;
            double max = 0;
            if(x.length() > 2 && y.length() > 2) {
                max = 2 * (x.length() - 2) * (y.length() - 2);
                matching += getMatching(x, y);
                matching += getMatching(y, x);
            }
            else{
                matching = 0;
                max = 1;
            }
            return 1 - (matching / max);
        }
    };

    private static double getMatching(String x, String y) {
        double matching = 0;
        for (int i = 0; i < x.length() - 2; i++) {
            for (int j = 0; j < y.length() - 2; j++) {
                if(x.charAt(i) == y.charAt(j) && x.charAt(i+1) == y.charAt(j+1) && x.charAt(i+2) == y.charAt(j+2)){
                    matching++;
                    j = y.length();
                }
            }
        }
        return matching;
    }

    abstract double compare(String x, String y);
}
