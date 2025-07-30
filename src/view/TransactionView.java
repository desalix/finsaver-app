package view;

import controller.AppState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import model.Category;
import model.Transaction;

import java.util.stream.Collectors;

public class TransactionView extends VBox {

    private final AppState appState = new AppState(); // In real app: inject or singleton
    private final ObservableList<Transaction> transactionData = FXCollections.observableArrayList();
    private final TableView<Transaction> table = new TableView<>();

    public TransactionView() {
        Label title = new Label("Transactions");
        title.getStyleClass().add("title-view");
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(title);

        // ==== Input fields ====
        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Description");

        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        ComboBox<Category> categoryBox = new ComboBox<>();
        categoryBox.setPromptText("Category");
        categoryBox.getItems().addAll(
            FXCollections.observableArrayList(Category.values())
                .filtered(c -> c != Category.MONTHLY_INCOME)
        );

        Button addBtn = new Button("Add Transaction");

        HBox form = new HBox(10, descriptionField, amountField, categoryBox, addBtn);
        form.setAlignment(Pos.CENTER_LEFT);
        form.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(descriptionField, Priority.ALWAYS);
        HBox.setHgrow(amountField, Priority.ALWAYS);
        HBox.setHgrow(categoryBox, Priority.ALWAYS);
        HBox.setHgrow(addBtn, Priority.NEVER);

        form.setPadding(new Insets(10));

        // ==== Filter ComboBox ====
        ComboBox<String> filterBox = new ComboBox<>();
        filterBox.setPromptText("Filter by category");
        filterBox.getItems().add("All Categories");
        filterBox.getItems().addAll(appState.getCategoryNames());

        filterBox.setOnAction(_ -> applyFilter(filterBox.getValue()));

        HBox filterRow = new HBox(filterBox);
        filterRow.setAlignment(Pos.CENTER_LEFT);
        filterRow.setMaxWidth(Double.MAX_VALUE);
        filterRow.setPadding(new Insets(5, 10, 5, 10));

        // ==== Table Setup ====
        table.setItems(transactionData);

        TableColumn<Transaction, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Transaction, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(cellData ->
                javafx.beans.binding.Bindings.createStringBinding(() ->
                        cellData.getValue().getCategory().getDisplayName()
                )
        );

        TableColumn<Transaction, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Transaction, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(cellData ->
                javafx.beans.binding.Bindings.createStringBinding(() ->
                        cellData.getValue().getDate().toString()
                )
        );

        table.getColumns().add(descCol);
        table.getColumns().add(categoryCol);
        table.getColumns().add(amountCol);
        table.getColumns().add(dateCol);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);



        // ==== Add Button Logic ====
        addBtn.setOnAction(_ -> {
            try {
                String description = descriptionField.getText();
                String amountText = amountField.getText();
                Category selectedCategory = categoryBox.getValue();

                Transaction t = appState.processTransaction(description, amountText, selectedCategory);
                transactionData.add(t);

                descriptionField.clear();
                amountField.clear();
                categoryBox.getSelectionModel().clearSelection();
                categoryBox.setPromptText("Select Category");

                // Refresh filter
                applyFilter(filterBox.getValue());
            } catch (IllegalArgumentException ex) {
                showAlert("Input Error", ex.getMessage());
            }
        });

        // ==== Initial Load ====
        transactionData.setAll(appState.getAccount().getTransactions());

        // ==== Layout ====
        this.setSpacing(10);
        this.setPadding(new Insets(20));
        this.getChildren().addAll(form, filterRow, table);
    }

    private void applyFilter(String selectedCategoryName) {
        if (selectedCategoryName.equals("All Categories")) {
            transactionData.setAll(appState.getAccount().getTransactions());
        } else {
            Category category = Category.fromDisplayName(selectedCategoryName);
            transactionData.setAll(
                    appState.getCategoryTransactions(category)
                            .stream()
                            .collect(Collectors.toList())
            );
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
