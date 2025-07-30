package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HomeView extends VBox {

    public HomeView() {
        Label title = new Label("Home");
        title.getStyleClass().add("title-view");
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(title);

    }
}
