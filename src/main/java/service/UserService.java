package service;

import dao.UserDAO;
import model.User;

import java.util.List;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public User getById(int id) {
        return userDAO.findById(id);
    }

    public void save(User user) {
        userDAO.save(user);
    }

    public void update(User user) {
        userDAO.update(user);
    }

    public void delete(int id) {
        User user = userDAO.findById(id);
        if (user != null) {
            userDAO.delete(user);
        }
    }
}