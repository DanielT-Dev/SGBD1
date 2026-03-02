package model;

import java.time.LocalDateTime;

public class Transaction {

    private int id;
    private int buyerId;
    private int itemId;
    private int quantity;
    private LocalDateTime transactionDate;

    public Transaction(int id, int buyerId, int itemId, int quantity, LocalDateTime transactionDate) {
        this.id = id;
        this.buyerId = buyerId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.transactionDate = transactionDate;
    }

    public int getId() { return id; }
    public int getBuyerId() { return buyerId; }
    public int getItemId() { return itemId; }
    public int getQuantity() { return quantity; }
    public LocalDateTime getTransactionDate() { return transactionDate; }
}