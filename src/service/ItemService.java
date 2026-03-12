package service;

import model.Item;
import repository.ItemRepository;

import java.math.BigDecimal;
import java.util.List;

public class ItemService {

    private ItemRepository itemRepository = new ItemRepository();

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // ---------------- Add Item ----------------
    public void addItem(int sellerId, String title, String description, BigDecimal price) {
        itemRepository.insertItem(sellerId, title, description, price);
    }
    public void deleteItem(int itemId) {
        itemRepository.deleteItem(itemId);
    }
    public void updateItem(int id, String title, String description, BigDecimal price) {
        itemRepository.updateItem(id, title, description, price);
    }
}