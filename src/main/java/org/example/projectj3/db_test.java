package org.example.projectj3;

import org.example.projectj3.Database.Database;
public class db_test {
    public static void main(String[] args) {
        Database db = Database.getInstance();
        // Verify the connection
        if (db.getConnection() != null) {
            System.out.println("Connection to the database is successful!");
            // Close the connection after testing

        } else {
            System.out.println("Failed to connect to the database.");
        }
    }
}