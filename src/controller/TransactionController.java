package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.Transaction;
import service.TransactionService;

import java.util.List;

public class TransactionController {

    private TransactionService transactionService = new TransactionService();

    public void loadTransactions(TableView<Transaction> table) {
        List<Transaction> transactions = transactionService.getAllTransactions();
        ObservableList<Transaction> observableTransactions =
                FXCollections.observableArrayList(transactions);
        table.setItems(observableTransactions);
    }
}