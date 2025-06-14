package pl.ksr.summarizator.model;

import pl.ksr.summarizator.model.fuzzylogic.Quantifier;
import pl.ksr.summarizator.model.membership.Gaussian;
import pl.ksr.summarizator.model.membership.Trapezoidal;
import pl.ksr.summarizator.model.membership.Triangular;

import java.util.List;

public class DefinedQuantifiers {
    public static Quantifier niewiele = new Quantifier(
            "NIEWIELE",
            new Trapezoidal(0.0, 0.0, 0.1, 0.2),
            0.0,
            1.0,
            false
    );
    public static Quantifier mniejszosc = new Quantifier(
            "MNIEJSZOŚĆ",
            new Gaussian(0.25, 0.04, 0.1, 0.4),
            0.0,
            1.0,
            false
    );
    public static Quantifier okoloPolowy = new Quantifier(
            "OKOŁO POŁOWY",
            new Triangular(0.3, 0.5, 0.7),
            0.0,
            1.0,
            false
    );
    public static Quantifier wiekszosc = new Quantifier(
            "WIĘKSZOŚĆ",
            new Gaussian(0.75, 0.04, 0.6, 0.9),
            0.0,
            1.0,
            false
    );
    public static Quantifier prawieWszystkie = new Quantifier(
            "PRAWIE WSZYSTKIE",
            new Trapezoidal(0.8, 0.9, 1.0, 1.0),
            0.0,
            1.0,
            false
    );

    public static List<Quantifier> getAllQuantifiers() {
        return List.of(
                niewiele,
                mniejszosc,
                okoloPolowy,
                wiekszosc,
                prawieWszystkie
        );
    }
}
