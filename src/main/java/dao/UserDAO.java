package dao;

import model.User;

public class UserDAO extends GenericDAO<User> {
    public UserDAO() {
        super(User.class);
    }
}