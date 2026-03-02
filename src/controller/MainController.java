package controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Item;
import model.User;

import java.math.BigDecimal;
import java.util.List;

public class MainController {

    private Stage stage;

    // Users tab
    private TableView<User> userTable = new TableView<>();
    private TableView<Item> userItemsTable = new TableView<>();
    private Button showItemsBtn = new Button("Show Items for Selected User");
    private Button addItemBtn = new Button("Add Item for Selected User");
    private Button deleteItemBtn = new Button("Delete Selected Item");
    private Label userItemsLabel = new Label("Select a user to display posted items");

    // Items & Transactions tabs
    private TableView<Item> allItemsTable = new TableView<>();
    private TableView<model.Transaction> transactionTable = new TableView<>();

    public MainController(Stage stage) {
        this.stage = stage;
        initUI();
    }

    private void initUI() {

        Label title = new Label("Marketplace App");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // ---- Users Tab ----
        setupUserTable();
        setupUserItemsTable();

        new UserController().loadUsers(userTable);

        showItemsBtn.setOnAction(e -> showItemsForSelectedUser());

        addItemBtn.setOnAction(e -> addItemForSelectedUser());
        addItemBtn.setVisible(false);

        deleteItemBtn.setOnAction(e -> deleteSelectedItem());
        deleteItemBtn.setVisible(false);

        userItemsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            deleteItemBtn.setVisible(newSel != null);
        });

        userItemsLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        userItemsTable.setVisible(false);
        showItemsBtn.setVisible(false);

        // Show Add/Delete buttons when a user is selected
        userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            boolean userSelected = newSel != null;
            showItemsBtn.setVisible(userSelected);
            addItemBtn.setVisible(userSelected);
            deleteItemBtn.setVisible(userSelected); // now visible with Add button
            userItemsLabel.setText("Select a user to display posted items");
            userItemsTable.setVisible(false);
        });

        // Disable Delete button if no item is selected
        userItemsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            deleteItemBtn.setDisable(newSel == null);
        });

        // Horizontal box for Add and Delete buttons
        HBox buttonBox = new HBox(10); // 10px spacing between buttons
        buttonBox.getChildren().addAll(addItemBtn, deleteItemBtn);

        // Users tab VBox
        VBox usersTabContent = new VBox(10,
                userTable,         // users table
                showItemsBtn,      // show items button
                userItemsLabel,    // label
                userItemsTable,    // items table
                buttonBox          // Add/Delete buttons side by side
        );

        // ---- Items Tab ----
        setupAllItemsTable();
        new ItemController().loadItems(allItemsTable);
        VBox itemsTabContent = new VBox(10, allItemsTable);

        // ---- Transactions Tab ----
        setupTransactionTable();
        VBox transactionsTabContent = new VBox(10, transactionTable);
        new controller.TransactionController().loadTransactions(transactionTable);

        // ---- TabPane ----
        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(
                new Tab("Users", usersTabContent),
                new Tab("Items", itemsTabContent),
                new Tab("Transactions", transactionsTabContent)
        );
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        VBox root = new VBox(15, title, tabPane);
        root.setStyle("-fx-padding: 20;");

        stage.setScene(new Scene(root, 1100, 700));
        stage.setTitle("Marketplace");
        showItemsBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8 20 8 20;");
        addItemBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8 20 8 20;");
        deleteItemBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8 20 8 20;");

        stage.show();
    }

    // ----------------- Users Table -----------------
    private void setupUserTable() {
        TableColumn<User, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getId()).asObject());

        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getUsername()));

        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEmail()));

        userTable.getColumns().addAll(idCol, usernameCol, emailCol);
    }

    private void setupUserItemsTable() {
        TableColumn<Item, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getId()).asObject());

        TableColumn<Item, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTitle()));

        TableColumn<Item, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDescription()));

        TableColumn<Item, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPrice().toString()));

        userItemsTable.getColumns().addAll(idCol, titleCol, descCol, priceCol);    }

    private void showItemsForSelectedUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            List<Item> items = new ItemController().loadItemsForUser(selectedUser.getId());
            userItemsTable.setItems(FXCollections.observableArrayList(items));
            userItemsTable.setVisible(true);
            userItemsLabel.setText("Showing items for " + selectedUser.getUsername());
        }
    }

    // ----------------- Add Item -----------------
    private void addItemForSelectedUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) return;

        TextInputDialog titleDialog = new TextInputDialog();
        titleDialog.setHeaderText("Enter item title:");
        titleDialog.setTitle("Add Item");
        String title = titleDialog.showAndWait().orElse("");
        if (title.isEmpty()) return;

        TextInputDialog descDialog = new TextInputDialog();
        descDialog.setHeaderText("Enter item description:");
        descDialog.setTitle("Add Item");
        String desc = descDialog.showAndWait().orElse("");

        TextInputDialog priceDialog = new TextInputDialog();
        priceDialog.setHeaderText("Enter item price:");
        priceDialog.setTitle("Add Item");
        String priceStr = priceDialog.showAndWait().orElse("");
        if (priceStr.isEmpty()) return;

        BigDecimal price;
        try {
            price = new BigDecimal(priceStr);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid price.");
            alert.showAndWait();
            return;
        }

        new controller.ItemController().addItemForUser(selectedUser.getId(), title, desc, price);

        showItemsForSelectedUser();
    }

    private void deleteSelectedItem() {
        Item selectedItem = userItemsTable.getSelectionModel().getSelectedItem();
        User selectedUser = userTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null && selectedUser != null) {
            // Confirmation dialog
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Delete");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to delete item \""
                    + selectedItem.getTitle() + "\" from user \"" + selectedUser.getUsername() + "\"?");

            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Delete item
                    new ItemController().deleteItemForUser(selectedItem.getId());
                    // Refresh table
                    showItemsForSelectedUser();
                }
            });
        }
    }

    // ----------------- Other tables setup (items & transactions) -----------------
    private void setupAllItemsTable() {
        TableColumn<Item, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getId()).asObject());

        TableColumn<Item, Integer> sellerCol = new TableColumn<>("Seller ID");
        sellerCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getSellerId()).asObject());

        TableColumn<Item, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTitle()));

        TableColumn<Item, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDescription()));

        TableColumn<Item, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPrice().toString()));

        allItemsTable.getColumns().addAll(idCol, sellerCol, titleCol, descCol, priceCol);    }

    private void setupTransactionTable() {
        TableColumn<model.Transaction, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getId()).asObject());

        TableColumn<model.Transaction, Integer> buyerCol = new TableColumn<>("Buyer ID");
        buyerCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getBuyerId()).asObject());

        TableColumn<model.Transaction, Integer> itemCol = new TableColumn<>("Item ID");
        itemCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getItemId()).asObject());

        TableColumn<model.Transaction, Integer> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getQuantity()).asObject());

        TableColumn<model.Transaction, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTransactionDate().toString()));

        transactionTable.getColumns().addAll(idCol, buyerCol, itemCol, qtyCol, dateCol);
    }
}