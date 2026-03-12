package controller;

import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import model.Item;
import service.ItemService;

import java.math.BigDecimal;
import java.util.List;

public class ItemController {

    private ItemService itemService = new ItemService();

    // Load all items into a TableView
    public void loadItems(TableView<Item> table) {
        List<Item> items = itemService.getAllItems();
        table.setItems(FXCollections.observableArrayList(items));
    }

    // Load items for a specific user
    public List<Item> loadItemsForUser(int sellerId) {
        return itemService.getAllItems().stream()
                .filter(item -> item.getSellerId() == sellerId)
                .toList();
    }

    // Add new item for a user
    public void addItemForUser(int sellerId, String title, String description, BigDecimal price) {
        itemService.addItem(sellerId, title, description, price);
    }

    public void deleteItemForUser(int itemId) {
        itemService.deleteItem(itemId);
    }

    public void updateItem(int id, String title, String description, BigDecimal price) {
        itemService.updateItem(id, title, description, price);
    }
}