package view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainView extends BorderPane {

    private final ToggleGroup navigationGroup;
    private final VBox sideBar;
    private final StackPane mainContent;

    public MainView(Stage stage) {
        // ===== Side Bar =====
        Label selectorLabel = new Label("Selector");
        selectorLabel.getStyleClass().addAll("label", "title");

        RadioButton homeBtn = new RadioButton("Home");
        RadioButton transactionsBtn = new RadioButton("Transactions");
        RadioButton savingsBtn = new RadioButton("Savings");

        homeBtn.getStyleClass().add("radio-button");
        transactionsBtn.getStyleClass().add("radio-button");
        savingsBtn.getStyleClass().add("radio-button");

        navigationGroup = new ToggleGroup();
        homeBtn.setToggleGroup(navigationGroup);
        transactionsBtn.setToggleGroup(navigationGroup);
        savingsBtn.setToggleGroup(navigationGroup);

        homeBtn.setSelected(true); // Default view

        VBox selectorBox = new VBox(10, selectorLabel, homeBtn, transactionsBtn, savingsBtn);
        selectorBox.getStyleClass().add("vbox");

        sideBar = new VBox(selectorBox);
        sideBar.getStyleClass().add("vbox");
        sideBar.setPrefWidth(200);

        // ===== Main Content =====
        mainContent = new StackPane();
        mainContent.setPadding(new Insets(20));
        setContent("Home"); // Load default

        VBox mainContentBox = new VBox(mainContent);
        mainContentBox.setPadding(new Insets(10));

        // ===== Layout =====
        this.setLeft(sideBar);
        this.setCenter(mainContentBox);

        // ===== Event Handling =====
        navigationGroup.selectedToggleProperty().addListener((_, _, newVal) -> {
            if (newVal != null) {
                RadioButton selected = (RadioButton) newVal;
                setContent(selected.getText());
            }
        });
    }

    private void setContent(String page) {
        mainContent.getChildren().clear();

        switch (page) {
            case "Home":
                mainContent.getChildren().add(new HomeView());
                break;
            case "Transactions":
                mainContent.getChildren().add(new TransactionView());
                break;
            case "Savings":
                mainContent.getChildren().add(new SavingsView());
                break;
        }
    }

    public Scene createScene() {
        Scene scene = new Scene(this, 900, 600);
        scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
        return scene;
    }
}
