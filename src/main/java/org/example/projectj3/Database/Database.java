package org.example.projectj3.Database;

import java.sql.*;

import static org.example.projectj3.Database.DBConst.*;

public class Database {
    private static Database instance;
    private Connection connection;
    private String dbName;
    private String dbUser;
    private String dbPassword;

    private static final String[] DEFAULT_TAGS = {
            "High Priority", "Medium Priority", "Low Priority", "Urgent", "Optional",
            "School", "Gym", "Work", "Personal", "Hobby"
    };

    private Database() {
    }

    /**
     * Get the instance of the database
     *
     * @return Database
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * Initialize the connection to the database
     *
     * @param dbName     Name of the database
     * @param dbUser     Username for the database
     * @param dbPassword Password for the database
     * @return boolean
     */
    public boolean initializeConnection(String dbName, String dbUser, String dbPassword) {
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        return reconnect();
    }

    /**
     * Reconnect to the database
     *
     * @return boolean
     */
    private boolean reconnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3307/" + dbName + "?serverTimezone=UTC",
                    dbUser,
                    dbPassword
            );
            System.out.println("Connected to database!");

            createTable("user_table", CREATE_TABLE_USER);
            createTable("task_table", CREATE_TABLE_TASK);
            createTable("tags_table", CREATE_TABLE_TAG);
            createTable("user_tasks", CREATE_TABLE_USER_TASK);
            createTable("task_tags", CREATE_TABLE_TASK_TAG);

            insertDefaultTags();
            return true;
        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get the connection to the database
     *
     * @return Connection
     */
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3307/" + dbName + "?serverTimezone=UTC",
                        dbUser,
                        dbPassword
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Close the connection to the database
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Create a table if it does not exist
     *
     * @param tableName  Name of the table
     * @param tableQuery SQL query to create the table
     * @throws SQLException
     */
    private void createTable(String tableName, String tableQuery) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();

        try (ResultSet tables = metaData.getTables(null, null, tableName, null)) {
            if (tables.next()) {
                System.out.println(tableName + " table already exists");
            } else {
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(tableQuery);
                    System.out.println("Created table: " + tableName);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error during table creation for " + tableName + ": " + e.getMessage());
        }
    }


    /**
     * Insert default tags into the database
     */
    private void insertDefaultTags() {
        String checkQuery = "SELECT COUNT(*) FROM " + TABLE_TAG;
        String insertQuery = "INSERT INTO " + TABLE_TAG + " (" + TAG_COLUMN_TITLE + ") VALUES (?)";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
             PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {

            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Default tags already exist.");
                return;
            }

            for (String tag : DEFAULT_TAGS) {
                insertStmt.setString(1, tag);
                insertStmt.executeUpdate();
            }
            System.out.println("Default tags inserted successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting default tags: " + e.getMessage());
        }
    }
}
