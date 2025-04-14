package org.example.projectj3.tables;

import org.example.projectj3.DAO.TagDAO;
import org.example.projectj3.Database.DBConst;
import org.example.projectj3.pojo.Tag;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TagTable implements TagDAO {
    private final Connection connection;

    /**
     * Constructor for TagTable
     * @param connection
     */
    public TagTable(Connection connection) {
        this.connection = connection;
    }

    /**
     * Getter for all tags
     * @return tags
     */
    @Override
    public List<Tag> getAllTags() {
        List<Tag> tags = new ArrayList<>();
        String query = "SELECT * FROM tags_table";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tags.add(new Tag(
                        resultSet.getInt("Tag_ID"),
                        resultSet.getString("Tag_Title")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tags;
    }

    /**
     * Getter for tag by ID
     * @param tagId
     * @return tag
     */
    @Override
    public Tag getTagById(int tagId) {
        String query = "SELECT * FROM tags_table WHERE Tag_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tagId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Tag(
                        resultSet.getInt("Tag_ID"),
                        resultSet.getString("Tag_Title")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Create a tag
     * @param tag
     * @return boolean
     */
    @Override
    public boolean createTag(Tag tag) {
        String query = "INSERT INTO tags_table (Tag_Title) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, tag.getTitle());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet keys = statement.getGeneratedKeys();
                if (keys.next()) {
                    tag.setTagId(keys.getInt(1)); // Set the generated ID
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Update a tag
     * @param tag
     * @return boolean
     */
    @Override
    public boolean updateTag(Tag tag) {
        String query = "UPDATE tags_table SET Tag_Title = ? WHERE Tag_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, tag.getTitle());
            statement.setInt(2, tag.getTagId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Delete a tag
     * @param tagId
     * @return boolean
     */
    @Override
    public boolean deleteTag(int tagId) {
        String query = "DELETE FROM tags_table WHERE Tag_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tagId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Link a tag to a task
     * @param taskId
     * @param tagId
     * @return boolean
     */
    @Override
    public boolean linkTagToTask(int taskId, int tagId) {
        String insertTaskTagSql = "INSERT INTO " + DBConst.TABLE_TASK_TAG + " (" +
                DBConst.TASK_TAG_COLUMN_TASK_ID + ", " +
                DBConst.TASK_TAG_COLUMN_TAG_ID + ") VALUES (?, ?)";

        try (PreparedStatement insertTaskTagStmt = connection.prepareStatement(insertTaskTagSql)) {
            insertTaskTagStmt.setInt(1, taskId);
            insertTaskTagStmt.setInt(2, tagId);
            int rowsInserted = insertTaskTagStmt.executeUpdate();
            System.out.println("Rows inserted into task_tag_table: " + rowsInserted); // Debugging
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error linking task to tag. Task ID: " + taskId + ", Tag ID: " + tagId); // Debugging
        }
        return false;
    }



    /**
     * Get all tag titles
     * @return tagTitles
     */
    public List<String> getAllTagTitles() {
        List<String> tagTitles = new ArrayList<>();
        String query = "SELECT " + DBConst.TAG_COLUMN_TITLE + " FROM " + DBConst.TABLE_TAG;
        System.out.println("Fetching tags from the database...");
        System.out.println("Query: SELECT Tag_Title FROM tags_table");

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                tagTitles.add(resultSet.getString(DBConst.TAG_COLUMN_TITLE));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tagTitles;
    }


    /**
     * Get tags by task ID
     * @param taskId
     * @return tags
     */
    @Override
    public List<Tag> getTagsByTaskId(int taskId) {
        List<Tag> tags = new ArrayList<>();
        String query = "SELECT t.* FROM tags_table t " +
                "JOIN task_tag_table tt ON t.Tag_ID = tt.Tag_ID " +
                "WHERE tt.Task_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, taskId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tags.add(new Tag(
                        resultSet.getInt("Tag_ID"),
                        resultSet.getString("Tag_Title")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tags;
    }

    /**
     * Get tag ID by title
     * @param tagTitle
     * @return tagId
     */
    public int getTagIdByTitle(String tagTitle) {
        String query = "SELECT " + DBConst.TAG_COLUMN_ID + " FROM " + DBConst.TABLE_TAG +
                " WHERE " + DBConst.TAG_COLUMN_TITLE + " = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, tagTitle);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int tagId = resultSet.getInt(DBConst.TAG_COLUMN_ID);
                System.out.println("Tag ID found for title '" + tagTitle + "': " + tagId); // Debugging
                return tagId;
            } else {
                System.out.println("No tag found for title: " + tagTitle); // Debugging
            }
            } catch (SQLException e) {
                e.printStackTrace();
            }
                    return -1; 
    }


    /**
     * Get tag count by user ID
     * @param userId
     * @param tagTitle
     * @return tagCount
     */
    public int getTagCountByUserId(int userId, String tagTitle) {
        String query = "SELECT COUNT(*) FROM task_table t " +
                "JOIN task_tag_table tt ON t.Task_ID = tt.Task_ID " +
                "JOIN tags_table tg ON tt.Tag_ID = tg.Tag_ID " +
                "JOIN user_task_table ut ON t.Task_ID = ut.Task_ID " +
                "WHERE ut.User_ID = ? AND tg.Tag_Title = ? AND t.deleted = 0";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setString(2, tagTitle);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
