module ksr.knn {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires org.apache.commons.text;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens ksr.knn to javafx.fxml;
    exports ksr.knn;
}