module ksr.proj1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires org.apache.commons.text;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens ksr.proj1 to javafx.fxml;
    exports ksr.proj1;
}