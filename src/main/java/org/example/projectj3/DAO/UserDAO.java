package org.example.projectj3.DAO;

import org.example.projectj3.pojo.User;
import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();

    User getUserById(int userId);

    boolean createUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(int userId);

    boolean authenticateUser(String userName, String password);
}
