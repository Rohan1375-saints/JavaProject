package org.example.projectj3.Database;

public class DBConst {

    // User Table
    public static final String TABLE_USER = "user_table";
    public static final String USER_COLUMN_ID = "User_ID";
    public static final String USER_COLUMN_NAME = "User_Name";
    public static final String USER_COLUMN_EMAIL = "Email";
    public static final String USER_COLUMN_PASSWORD = "Password";
    public static final String USER_COLUMN_IS_PREMIUM = "is_Premium";

    public static final String CREATE_TABLE_USER =
            "CREATE TABLE " + TABLE_USER + " (" +
                    USER_COLUMN_ID + " int NOT NULL AUTO_INCREMENT, " +
                    USER_COLUMN_NAME + " VARCHAR(50), " +
                    USER_COLUMN_EMAIL + " VARCHAR(100) UNIQUE, " +
                    USER_COLUMN_PASSWORD + " VARCHAR(100), " +
                    USER_COLUMN_IS_PREMIUM + " BOOLEAN DEFAULT FALSE, " +
                    "PRIMARY KEY(" + USER_COLUMN_ID + "));";

    // Task Table
    public static final String TABLE_TASK = "task_table";
    public static final String TASK_COLUMN_ID = "Task_ID";
    public static final String TASK_COLUMN_TITLE = "Task_Title";
    public static final String TASK_COLUMN_DESCRIPTION = "Task_Description";
    public static final String TASK_COLUMN_DUE_DATE = "Task_Due_Date";
    public static final String TASK_COLUMN_IS_COMPLETED = "is_Completed";
    public static final String TASK_COLUMN_IS_PINNED = "is_Pinned";

    public static final String CREATE_TABLE_TASK =
            "CREATE TABLE " + TABLE_TASK + " (" +
                    TASK_COLUMN_ID + " int NOT NULL AUTO_INCREMENT, " +
                    TASK_COLUMN_TITLE + " VARCHAR(100), " +
                    TASK_COLUMN_DESCRIPTION + " TEXT, " +
                    TASK_COLUMN_DUE_DATE + " DATE, " +
                    TASK_COLUMN_IS_COMPLETED + " BOOLEAN DEFAULT FALSE, " +
                    TASK_COLUMN_IS_PINNED + " BOOLEAN DEFAULT FALSE, " +
                    "deleted BOOLEAN DEFAULT 0, " +
                    "PRIMARY KEY(" + TASK_COLUMN_ID + "));";

    // Tags Table
    public static final String TABLE_TAG = "tags_table";
    public static final String TAG_COLUMN_ID = "Tag_ID";
    public static final String TAG_COLUMN_TITLE = "Tag_Title";

    public static final String CREATE_TABLE_TAG =
            "CREATE TABLE " + TABLE_TAG + " (" +
                    TAG_COLUMN_ID + " int NOT NULL AUTO_INCREMENT, " +
                    TAG_COLUMN_TITLE + " VARCHAR(50), " +
                    "PRIMARY KEY(" + TAG_COLUMN_ID + "));";

    // User-Task Relationship Table (Many-to-Many)
    public static final String TABLE_USER_TASK = "user_task_table";
    public static final String USER_TASK_COLUMN_USER_ID = "User_ID";
    public static final String USER_TASK_COLUMN_TASK_ID = "Task_ID";

    public static final String CREATE_TABLE_USER_TASK =
            "CREATE TABLE " + TABLE_USER_TASK + " (" +
                    USER_TASK_COLUMN_USER_ID + " int NOT NULL, " +
                    USER_TASK_COLUMN_TASK_ID + " int NOT NULL, " +
                    "PRIMARY KEY(" + USER_TASK_COLUMN_USER_ID + ", " + USER_TASK_COLUMN_TASK_ID + ")" +
                    ");";


    // Task-Tag Relationship Table (Many-to-Many)
    public static final String TABLE_TASK_TAG = "task_tag_table";
    public static final String TASK_TAG_COLUMN_TASK_ID = "Task_ID";
    public static final String TASK_TAG_COLUMN_TAG_ID = "Tag_ID";

    public static final String CREATE_TABLE_TASK_TAG =
            "CREATE TABLE " + TABLE_TASK_TAG + " (" +
                    TASK_TAG_COLUMN_TASK_ID + " int NOT NULL, " +
                    TASK_TAG_COLUMN_TAG_ID + " int NOT NULL, " +
                    "PRIMARY KEY(" + TASK_TAG_COLUMN_TASK_ID + ", " + TASK_TAG_COLUMN_TAG_ID + ")" +
                    ");";

}
