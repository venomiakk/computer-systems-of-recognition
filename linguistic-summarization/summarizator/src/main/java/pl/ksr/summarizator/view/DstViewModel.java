package pl.ksr.summarizator.view;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class DstViewModel {
    private final SimpleStringProperty form;
    private final SimpleStringProperty term;
    private final SimpleDoubleProperty t1;
    public SimpleIntegerProperty index;

    public DstViewModel(String form, String term, double t1, int index) {
        this.form = new SimpleStringProperty(form);
        this.term = new SimpleStringProperty(term);
        this.t1 = new SimpleDoubleProperty(round(t1, 2));
        this.index = new SimpleIntegerProperty(index);
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    public String getForm() {
        return form.get();
    }

    public SimpleStringProperty formProperty() {
        return form;
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


    public int getIndex() {
        return index.get();
    }

    public SimpleIntegerProperty indexProperty() {
        return index;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (!(other instanceof DstViewModel)) return false;
        DstViewModel that = (DstViewModel) other;
        if (that.term.equals(this.term) &&
            that.form.equals(this.form) &&
            that.t1.get() == this.t1.get()) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(form, term, t1);
    }
}
