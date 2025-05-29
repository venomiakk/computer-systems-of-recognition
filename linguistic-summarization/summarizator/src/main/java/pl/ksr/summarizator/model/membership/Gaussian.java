package pl.ksr.summarizator.model.membership;

public class Gaussian implements MembershipFunction {
    private final double mean;
    private final double sigma;
    private final double leftBound;
    private final double rightBound;

    public Gaussian(double mean, double sigma, double leftBound, double rightBound) {
        this.mean = mean;
        this.sigma = sigma;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }

    @Override
    public double calculateMembership(Object value) {
        if (!(value instanceof Number)) {
            throw new IllegalArgumentException("Value must be a number");
        }
        double x = Double.parseDouble(value.toString());
        if (x < leftBound || x > rightBound) {
            return 0;
        } else {
            return Math.exp(-Math.pow((x - mean), 2) / (sigma * sigma * 2));
        }
    }
}
