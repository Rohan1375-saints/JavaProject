package org.example.projectj3.tests;

import org.example.projectj3.Database.Database;
import org.example.projectj3.pojo.User;
import org.example.projectj3.tables.UserTable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserTableTest {

    public static void main(String[] args) throws SQLException {
        Connection connection = Database.getInstance().getConnection();
        if (connection == null) {
            System.out.println("Failed to establish a database connection.");
            return;
        }

        UserTable userTable = new UserTable(connection);

        testCreateUser(userTable);
        testGetAllUsers(userTable);
        testGetUserById(userTable);
        testUpdateUser(userTable);
        //testDeleteUser(userTable);

        Database.getInstance().closeConnection();
    }

    private static void testCreateUser(UserTable userTable) {
        System.out.println("\nRunning testCreateUser...");
        User user = new User("JohnDoe", "johndoe@example.com", "password123", false);
        boolean result = userTable.createUser(user);
        System.out.println("User created: " + result);
        System.out.println("Created User ID: " + user.getUserId());
    }

    private static void testGetAllUsers(UserTable userTable) {
        System.out.println("\nRunning testGetAllUsers...");
        List<User> users = userTable.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
    }

    private static void testGetUserById(UserTable userTable) {
        System.out.println("\nRunning testGetUserById...");
        User user = userTable.getUserById(1); // Assuming user ID 1 exists in database
        if (user != null) {
            System.out.println("User found: " + user);
        } else {
            System.out.println("No user found with the given ID.");
        }
    }

    private static void testUpdateUser(UserTable userTable) {
        System.out.println("\nRunning testUpdateUser...");
        User user = new User("JaneDoe", "janedoe@example.com", "newpassword456", true);
        user.setUserId(1); // Assuming user ID 1 exists in database
        boolean result = userTable.updateUser(user);
        System.out.println("User updated: " + result);
    }

    private static void testDeleteUser(UserTable userTable) {
        System.out.println("\nRunning testDeleteUser...");
        boolean result = userTable.deleteUser(1); // Assuming user ID 1 exists in database
        System.out.println("User deleted: " + result);
    }
}
