package Model;

import static java.lang.Math.sqrt;

public enum Metrics {

    MANHATTAN {
        @Override
        double process(double[] distance) {
            double sum = 0;
            for (double d : distance) {
                sum += d;
            }
            return sum;
        }
    },
    EUCLIDES {
        @Override
        double process(double[] distance) {
            double sum = 0;
            for (double d : distance) {
                sum += d * d;
            }
            return sqrt(sum);
        }
    },
    CHEBYSHEV {
        @Override
        double process(double[] distance) {
            double max = 0;
            for (double d : distance) {
                if (d > max) {
                    max = d;
                }
            }
            return max;
        }
    };

    abstract double process(double[] distance);
}
