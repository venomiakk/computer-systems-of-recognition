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
        if (!(value instanceof Number)) {
            throw new IllegalArgumentException("Value must be a number");
        }

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
}
