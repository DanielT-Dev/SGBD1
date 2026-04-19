package dao;

import model.Transaction;

public class TransactionDAO extends GenericDAO<Transaction> {
    public TransactionDAO() {
        super(Transaction.class);
    }
}