package pl.ksr.summarizator.view;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import pl.ksr.summarizator.model.fuzzylogic.SingleSubjectTerm;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SstViewModel {
    private final SimpleStringProperty term;
    private final SimpleDoubleProperty t1;
    private final SimpleDoubleProperty t2;
    private final SimpleDoubleProperty t3;
    private final SimpleDoubleProperty t4;
    private final SimpleDoubleProperty t5;
    private final SimpleDoubleProperty t6;
    private final SimpleDoubleProperty t7;
    private final SimpleDoubleProperty t8;
    private final SimpleDoubleProperty t9;
    private final SimpleDoubleProperty t10;
    private final SimpleDoubleProperty t11;
    private final SimpleDoubleProperty t0;

    public SstViewModel(SingleSubjectTerm termObject) {
        this.term = new SimpleStringProperty(termObject.getTerm());
        this.t0 = new SimpleDoubleProperty(round(termObject.getOptimalSummary(),2));
        this.t1 = new SimpleDoubleProperty(round(termObject.getT1(),2));
        this.t2 = new SimpleDoubleProperty(round(termObject.getT2(),2));
        this.t3 = new SimpleDoubleProperty(round(termObject.getT3(),2));
        this.t4 = new SimpleDoubleProperty(round(termObject.getT4(),2));
        this.t5 = new SimpleDoubleProperty(round(termObject.getT5(),2));
        this.t6 = new SimpleDoubleProperty(round(termObject.getT6(),2));
        this.t7 = new SimpleDoubleProperty(round(termObject.getT7(),2));
        this.t8 = new SimpleDoubleProperty(round(termObject.getT8(),2));
        this.t9 = new SimpleDoubleProperty(round(termObject.getT9(),2));
        this.t10 = new SimpleDoubleProperty(round(termObject.getT10(),2));
        this.t11 = new SimpleDoubleProperty(round(termObject.getT11(),2));
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public String getTerm() {
        return term.get();
    }

    public SimpleStringProperty termProperty() {
        return term;
    }

    public double getT1() {
        return t1.get();
    }

    public SimpleDoubleProperty t1Property() {
        return t1;
    }

    public double getT2() {
        return t2.get();
    }

    public SimpleDoubleProperty t2Property() {
        return t2;
    }

    public double getT3() {
        return t3.get();
    }

    public SimpleDoubleProperty t3Property() {
        return t3;
    }

    public double getT4() {
        return t4.get();
    }

    public SimpleDoubleProperty t4Property() {
        return t4;
    }

    public double getT5() {
        return t5.get();
    }

    public SimpleDoubleProperty t5Property() {
        return t5;
    }

    public double getT6() {
        return t6.get();
    }

    public SimpleDoubleProperty t6Property() {
        return t6;
    }

    public double getT7() {
        return t7.get();
    }

    public SimpleDoubleProperty t7Property() {
        return t7;
    }

    public double getT8() {
        return t8.get();
    }

    public SimpleDoubleProperty t8Property() {
        return t8;
    }

    public double getT9() {
        return t9.get();
    }

    public SimpleDoubleProperty t9Property() {
        return t9;
    }

    public double getT10() {
        return t10.get();
    }

    public SimpleDoubleProperty t10Property() {
        return t10;
    }

    public double getT11() {
        return t11.get();
    }

    public SimpleDoubleProperty t11Property() {
        return t11;
    }

    public double getT0() {
        return t0.get();
    }

    public SimpleDoubleProperty t0Property() {
        return t0;
    }

    public void setTerm(String term) {
        this.term.set(term);
    }

    public void setT1(double t1) {
        this.t1.set(t1);
    }

    public void setT2(double t2) {
        this.t2.set(t2);
    }

    public void setT3(double t3) {
        this.t3.set(t3);
    }

    public void setT4(double t4) {
        this.t4.set(t4);
    }

    public void setT5(double t5) {
        this.t5.set(t5);
    }

    public void setT6(double t6) {
        this.t6.set(t6);
    }

    public void setT7(double t7) {
        this.t7.set(t7);
    }

    public void setT8(double t8) {
        this.t8.set(t8);
    }

    public void setT9(double t9) {
        this.t9.set(t9);
    }

    public void setT10(double t10) {
        this.t10.set(t10);
    }

    public void setT11(double t11) {
        this.t11.set(t11);
    }

    public void setT0(double t0) {
        this.t0.set(t0);
    }

}
