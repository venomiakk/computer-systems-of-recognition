package pl.ksr.summarizator;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;

public class HelloController {
    //@FXML
    //private Label welcomeText;
    //
    //@FXML
    //protected void onHelloButtonClick() {
    //    welcomeText.setText("Welcome to JavaFX Application!");
    //}
    @FXML
    private ChoiceBox<String> dropdown1;

    @FXML
    private TreeView<String> dropdown2;
}