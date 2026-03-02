package model;

import java.math.BigDecimal;

public class Item {

    private int id;
    private int sellerId;
    private String title;
    private String description;
    private BigDecimal price;

    public Item(int id, int sellerId, String title, String description, BigDecimal price) {
        this.id = id;
        this.sellerId = sellerId;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public int getId() { return id; }
    public int getSellerId() { return sellerId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
}