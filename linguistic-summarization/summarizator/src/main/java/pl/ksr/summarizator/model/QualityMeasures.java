package pl.ksr.summarizator.model;

public class QualityMeasures {
    public double t1(boolean isAbsolute, double quantifierValue, double fuzzySetValue) {
        if (isAbsolute) {
            return quantifierValue - fuzzySetValue;
        } else {
            return quantifierValue * fuzzySetValue;
        }

    }

    public double t2(boolean isAbsolute, double quantifierValue, double fuzzySetValue) {
        if (isAbsolute) {
            return quantifierValue + fuzzySetValue;
        } else {
            return quantifierValue / fuzzySetValue;
        }
    }
}
