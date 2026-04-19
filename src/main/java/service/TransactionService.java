package service;

import dao.ItemDAO;
import dao.TransactionDAO;
import dao.UserDAO;
import model.Item;
import model.Transaction;
import model.User;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionService {

    private final TransactionDAO transactionDAO = new TransactionDAO();
    private final UserDAO userDAO = new UserDAO();
    private final ItemDAO itemDAO = new ItemDAO();

    public List<Transaction> getAllTransactions() {
        return transactionDAO.findAll();
    }

    public Transaction getById(int id) {
        return transactionDAO.findById(id);
    }

    public void save(Transaction transaction) {
        transactionDAO.save(transaction);
    }

    public void update(Transaction transaction) {
        transactionDAO.update(transaction);
    }

    public void delete(int id) {
        Transaction t = transactionDAO.findById(id);
        if (t != null) {
            transactionDAO.delete(t);
        }
    }

    public void addTransaction(int buyerId, int itemId, int quantity, LocalDateTime date) {

        User buyer = userDAO.findById(buyerId);
        Item item = itemDAO.findById(itemId);

        if (buyer == null) {
            throw new RuntimeException("Buyer not found: " + buyerId);
        }

        if (item == null) {
            throw new RuntimeException("Item not found: " + itemId);
        }

        Transaction t = new Transaction();
        t.setBuyer(buyer);
        t.setItem(item);
        t.setQuantity(quantity);
        t.setTransactionDate(date);

        transactionDAO.save(t);
    }
}