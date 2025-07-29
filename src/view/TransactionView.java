package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TransactionView extends VBox {

    public TransactionView() {
        // Section title
        Label title = new Label("Transactions");
        title.getStyleClass().addAll("label", "title");

        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.TOP_CENTER);
        this.getChildren().add(title);
    }
}
