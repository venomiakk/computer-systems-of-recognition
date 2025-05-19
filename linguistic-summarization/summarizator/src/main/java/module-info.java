module pl.ksr.summarizator {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.ksr.summarizator to javafx.fxml;
    exports pl.ksr.summarizator;
}