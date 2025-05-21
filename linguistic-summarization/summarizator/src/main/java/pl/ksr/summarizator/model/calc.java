package pl.ksr.summarizator.model;

public class calc {
    static double cal(double x, double leftBottom, double leftUp, double rightUp, double rightBottom) {
        if (x <= leftBottom || x >= rightBottom) {
            return 0;
        } else if (x > leftBottom && x < leftUp) {
            return (x - leftBottom) / (leftUp - leftBottom);
        } else if (x >= leftUp && x <= rightUp) {
            return 1;
        } else if (x > rightUp && x < rightBottom) {
            return (rightBottom - x) / (rightBottom - rightUp);
        }
        return 0;
    }

    static double cal(double x, double left,double vertex, double right) {
        if (x <= left || x >= right) {
            return 0;
        } else if (x > left && x < vertex) {
            return (x - left) / (vertex - left);
        } else if (x >= vertex && x <= right) {
            return (right - x) / (right - vertex);
        }
        return 0;

    }

    static double cal(double x, double mean, double sigma) {
        return Math.exp(-Math.pow((x - mean) / sigma, 2));
    }
}
