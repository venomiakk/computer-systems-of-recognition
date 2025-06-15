package pl.ksr.summarizator.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class WeightsWindow {

    private List<Double> result;

    public List<Double> display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Wprowadź wagi");

        TextField field1 = new TextField();
        field1.setPromptText("Waga T1");
        field1.setText("0.5");
        TextField field2 = new TextField();
        field2.setPromptText("Waga T2");
        field2.setText("0.05");
        TextField field3 = new TextField();
        field3.setPromptText("Waga T3");
        field3.setText("0.05");
        TextField field4 = new TextField();
        field4.setPromptText("Waga T4");
        field4.setText("0.05");
        TextField field5 = new TextField();
        field5.setPromptText("Waga T5");
        field5.setText("0.05");
        TextField field6 = new TextField();
        field6.setPromptText("Waga T6");
        field6.setText("0.05");
        TextField field7 = new TextField();
        field7.setPromptText("Waga T7");
        field7.setText("0.05");
        TextField field8 = new TextField();
        field8.setPromptText("Waga T8");
        field8.setText("0.05");
        TextField field9 = new TextField();
        field9.setPromptText("Waga T9");
        field9.setText("0.05");
        TextField field10 = new TextField();
        field10.setPromptText("Waga T10");
        field10.setText("0.05");
        TextField field11 = new TextField();
        field11.setPromptText("Waga T11");
        field11.setText("0.05");

        Button okButton = new Button("OK");
        okButton.setOnAction(e -> {
            result = new ArrayList<>();
            result.add(Double.valueOf(field1.getText()));
            result.add(Double.valueOf(field2.getText()));
            result.add(Double.valueOf(field3.getText()));
            result.add(Double.valueOf(field4.getText()));
            result.add(Double.valueOf(field5.getText()));
            result.add(Double.valueOf(field6.getText()));
            result.add(Double.valueOf(field7.getText()));
            result.add(Double.valueOf(field8.getText()));
            result.add(Double.valueOf(field9.getText()));
            result.add(Double.valueOf(field10.getText()));
            result.add(Double.valueOf(field11.getText()));
            if (result.stream().mapToDouble(Double::doubleValue).sum() != 1.0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Złe wagi");
                alert.setHeaderText(null);
                alert.setContentText("Wagi muszą sumować się do 1.0.");
                alert.show();
                return;
            }
            if (result.stream().anyMatch(x -> x < 0 || x > 1)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Złe wagi");
                alert.setHeaderText(null);
                alert.setContentText("Wagi muszą być w zakresie od 0 do 1.");
                alert.show();
                return;
            }
            window.close();
        });

        VBox layout = new VBox(10,
                new Label("Wprowadź wagi:"),
                field1,
                field2,
                field3,
                field4,
                field5,
                field6,
                field7,
                field8,
                field9,
                field10,
                field11,
                okButton
        );
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout, 200, 450);
        window.setScene(scene);
        window.showAndWait();

        return result;
    }
}
