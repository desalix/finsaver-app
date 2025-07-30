package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SavingsView extends VBox {

    public SavingsView() {
        Label title = new Label("Savings");
        title.getStyleClass().addAll("label", "title");

        this.setAlignment(Pos.CENTER);
        this.getChildren().add(title);
    }
}
