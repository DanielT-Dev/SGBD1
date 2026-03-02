package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.User;
import service.UserService;

import java.util.List;

public class UserController {

    private UserService userService = new UserService();

    public void loadUsers(TableView<User> table) {

        List<User> users = userService.getAllUsers();

        ObservableList<User> observableUsers =
                FXCollections.observableArrayList(users);

        table.setItems(observableUsers);
    }
}