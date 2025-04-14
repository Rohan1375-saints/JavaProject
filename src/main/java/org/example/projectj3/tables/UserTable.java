package org.example.projectj3.tables;

import org.example.projectj3.DAO.UserDAO;
import org.example.projectj3.pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserTable implements UserDAO {
    private final Connection connection;

    public UserTable(Connection connection) {
        this.connection = connection;
    }

    /**
     * Getter for all users
     * @return users
     */
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM user_table";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("User_Name"),
                        resultSet.getString("Email"),
                        resultSet.getString("Password"),
                        resultSet.getBoolean("is_Premium")
                );
                user.setUserId(resultSet.getInt("User_ID"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Getter for user by ID
     * @param userId
     * @return user
     */
    @Override
    public User getUserById(int userId) {
        String query = "SELECT * FROM user_table WHERE User_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new User(
                        resultSet.getString("User_Name"),
                        resultSet.getString("Email"),
                        resultSet.getString("Password"),
                        resultSet.getBoolean("is_Premium")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Create a user
     * @param user
     * @return boolean
     */
    @Override
    public boolean createUser(User user) {
        String query = "INSERT INTO user_table (User_Name, Email, Password, is_Premium) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setBoolean(4, user.isPremium());
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet keys = statement.getGeneratedKeys();
                if (keys.next()) {
                    user.setUserId(keys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Update a user
     * @param user
     * @return boolean
     */
    @Override
    public boolean updateUser(User user) {
        String query = "UPDATE user_table SET User_Name = ?, Email = ?, Password = ?, is_Premium = ? WHERE User_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setBoolean(4, user.isPremium());
            statement.setInt(5, user.getUserId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Delete a user
     * @param userId
     * @return boolean
     */
    @Override
    public boolean deleteUser(int userId) {
        String query = "DELETE FROM user_table WHERE User_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Authenticate a user
     * @param userName
     * @param password
     * @return boolean
     */
    @Override
    public boolean authenticateUser(String userName, String password) {
        String query = "SELECT * FROM user_table WHERE User_Name = ? AND Password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userName);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            // Debugging (GONNA DELETE IT LATER? MAYBE.... WHO KNOWS)
            if (resultSet.next()) {
                System.out.println("User authenticated: " + userName);
                return true;
            } else {
                System.out.println("Authentication failed for: " + userName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get user ID by username
     * @param username
     * @return userId
     */
    public int getUserIdByUsername(String username) {
        String query = "SELECT User_ID FROM user_table WHERE User_Name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("User_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
