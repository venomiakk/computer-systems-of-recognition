package pl.ksr.summarizator.model;

public class MathFunctions {
    static double calc(double x, double leftBottom, double leftUp, double rightUp, double rightBottom) {
        if (x <= leftBottom || x >= rightBottom) {
            return 0;
        } else if (x < leftUp) {
            return (x - leftBottom) / (leftUp - leftBottom);
        } else if (x >= leftUp && x <= rightUp) {
            return 1;
        } else if (x > rightUp) {
            return (rightBottom - x) / (rightBottom - rightUp);
        }
        return 0;
    }

    static double calc(double x, double left, double vertex, double right) {
        if (x <= left || x >= right) {
            return 0;
        } else if (x < vertex) {
            return (x - left) / (vertex - left);
        } else if (x >= vertex) {
            return (right - x) / (right - vertex);
        }
        return 0;

    }

    static double calc(double x, double mean, double sigma) {
        return Math.exp(-Math.pow((x - mean) / sigma, 2));
    }
}
