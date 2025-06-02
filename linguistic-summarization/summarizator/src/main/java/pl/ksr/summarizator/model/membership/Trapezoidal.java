package pl.ksr.summarizator.model.membership;

public class Trapezoidal implements MembershipFunction{
    private final double leftBottom;
    private final double leftUp;
    private final double rightUp;
    private final double rightBottom;

    public Trapezoidal(double leftBottom, double leftUp, double rightUp, double rightBottom) {
        this.leftBottom = leftBottom;
        this.leftUp = leftUp;
        this.rightUp = rightUp;
        this.rightBottom = rightBottom;
    }

    @Override
    public double calculateMembership(Object value) {
        double x = Double.parseDouble(value.toString());

        if (x < leftBottom || x > rightBottom) {
            return 0;
        } else if (x < leftUp) {
            return (x - leftBottom) / (leftUp - leftBottom);
        } else if (x <= rightUp) {
            return 1;
        } else {
            return (rightBottom - x) / (rightBottom - rightUp);
        }
    }

    @Override
    public double getSupportSize() {
        return rightBottom - leftBottom;
    }

    @Override
    public double getCardinality() {
        double step = 0.01;
        double cardinality = 0.0;
        for (double x = leftBottom; x <= rightBottom; x += step) {
            cardinality += calculateMembership(x);
        }
        return cardinality / (1 / step);
    }
}
