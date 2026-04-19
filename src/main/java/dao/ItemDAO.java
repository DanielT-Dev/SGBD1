package dao;

import model.Item;

public class ItemDAO extends GenericDAO<Item> {
    public ItemDAO() {
        super(Item.class);
    }
}