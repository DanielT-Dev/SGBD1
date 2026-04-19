package service;

import dao.ItemDAO;
import dao.UserDAO;
import model.Item;
import model.User;

import java.math.BigDecimal;
import java.util.List;

public class ItemService {

    private final ItemDAO itemDAO = new ItemDAO();
    private final UserDAO userDAO = new UserDAO();

    public List<Item> getAllItems() {
        return itemDAO.findAll();
    }

    public Item getById(int id) {
        return itemDAO.findById(id);
    }

    public void save(Item item) {
        itemDAO.save(item);
    }

    public void update(Item item) {
        itemDAO.update(item);
    }

    public void delete(int id) {
        Item item = itemDAO.findById(id);
        if (item != null) {
            itemDAO.delete(item);
        }
    }

    public void addItem(int sellerId, String title, String description, BigDecimal price) {
        User seller = userDAO.findById(sellerId);

        if (seller == null) {
            throw new RuntimeException("Seller not found: " + sellerId);
        }

        Item item = new Item();
        item.setSeller(seller);
        item.setTitle(title);
        item.setDescription(description);
        item.setPrice(price);

        itemDAO.save(item);
    }

    public void updateItem(int id, String title, String description, BigDecimal price) {
        Item item = itemDAO.findById(id);

        if (item == null) {
            throw new RuntimeException("Item not found: " + id);
        }

        item.setTitle(title);
        item.setDescription(description);
        item.setPrice(price);

        itemDAO.update(item);
    }
}