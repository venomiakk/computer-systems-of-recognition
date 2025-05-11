module pl.ksr.linguisticsummarization {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.ksr.linguisticsummarization to javafx.fxml;
    exports pl.ksr.linguisticsummarization;
}