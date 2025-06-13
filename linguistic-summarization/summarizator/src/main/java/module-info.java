module pl.ksr.summarizator {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens pl.ksr.summarizator to javafx.fxml;
    exports pl.ksr.summarizator;
    exports pl.ksr.summarizator.view;
    opens pl.ksr.summarizator.view to javafx.fxml;
}