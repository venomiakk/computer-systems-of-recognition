module ksr.proj1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires org.apache.commons.text;


    opens ksr.proj1 to javafx.fxml;
    exports ksr.proj1;
}