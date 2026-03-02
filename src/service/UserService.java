package service;

import model.User;
import repository.UserRepository;

import java.util.List;

public class UserService {

    private UserRepository userRepository = new UserRepository();

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}