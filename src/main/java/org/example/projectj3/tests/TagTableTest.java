package org.example.projectj3.tests;

import org.example.projectj3.Database.Database;
import org.example.projectj3.pojo.Tag;
import org.example.projectj3.tables.TagTable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TagTableTest {

    public static void main(String[] args) throws SQLException {
        Connection connection = Database.getInstance().getConnection();
        if (connection == null) {
            System.out.println("Failed to establish a database connection.");
            return;
        }

        TagTable tagTable = new TagTable(connection);

        testCreateTag(tagTable);
        testGetAllTags(tagTable);
        testGetTagById(tagTable);
        testUpdateTag(tagTable);
        testLinkTagToTask(tagTable);
        testGetTagsByTaskId(tagTable);
        //testDeleteTag(tagTable);

        Database.getInstance().closeConnection();
    }

    private static void testCreateTag(TagTable tagTable) {
        System.out.println("\nRunning testCreateTag...");
        Tag tag = new Tag("Urgent");
        boolean result = tagTable.createTag(tag);
        System.out.println("Tag created: " + result);
        System.out.println("Created Tag ID: " + tag.getTagId());
    }

    private static void testGetAllTags(TagTable tagTable) {
        System.out.println("\nRunning testGetAllTags...");
        List<Tag> tags = tagTable.getAllTags();
        for (Tag tag : tags) {
            System.out.println(tag);
        }
    }

    private static void testGetTagById(TagTable tagTable) {
        System.out.println("\nRunning testGetTagById...");
        Tag tag = tagTable.getTagById(1); // this 1 is a valid Tag_ID in database
        if (tag != null) {
            System.out.println("Tag found: " + tag);
        } else {
            System.out.println("No tag found with the given ID.");
        }
    }

    private static void testUpdateTag(TagTable tagTable) {
        System.out.println("\nRunning testUpdateTag...");
        Tag tag = new Tag(1,"High Priority" ); // this 1 is a valid Tag_ID
        boolean result = tagTable.updateTag(tag);
        System.out.println("Tag updated: " + result);
    }

    private static void testLinkTagToTask(TagTable tagTable) {
        System.out.println("\nRunning testLinkTagToTask...");
        boolean result = tagTable.linkTagToTask(1, 2); // these are valid Tag_ID and Task_ID
        System.out.println("Tag linked to task: " + result);
    }

    private static void testGetTagsByTaskId(TagTable tagTable) {
        System.out.println("\nRunning testGetTagsByTaskId...");
        List<Tag> tags = tagTable.getTagsByTaskId(2); // this 2 is a valid Task_ID
        for (Tag tag : tags) {
            System.out.println(tag);
        }
    }

    private static void testDeleteTag(TagTable tagTable) {
        System.out.println("\nRunning testDeleteTag...");
        boolean result = tagTable.deleteTag(1); // this 1 is a valid Tag_ID
        System.out.println("Tag deleted: " + result);
    }
}
