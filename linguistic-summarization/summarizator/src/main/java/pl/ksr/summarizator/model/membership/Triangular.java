package pl.ksr.summarizator.model.membership;

public class Triangular implements MembershipFunction{

    private final double left;
    private final double vertex;
    private final double right;

    public Triangular(double left, double vertex, double right) {
        this.left = left;
        this.vertex = vertex;
        this.right = right;
    }

    @Override
    public double calculateMembership(Object value) {
        if (!(value instanceof Number)) {
            throw new IllegalArgumentException("Value must be a number");
        }

        double x = Double.parseDouble(value.toString());

        if (x < left || x > right) {
            return 0;
        } else if (x < vertex) {
            return (x - left) / (vertex - left);
        } else {
            return (right - x) / (right - vertex);
        }
    }
}
