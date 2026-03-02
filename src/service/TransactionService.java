package service;

import model.Transaction;
import repository.TransactionRepository;

import java.util.List;

public class TransactionService {

    private TransactionRepository transactionRepository = new TransactionRepository();

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}