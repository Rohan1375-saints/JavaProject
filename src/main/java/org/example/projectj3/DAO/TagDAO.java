package org.example.projectj3.DAO;

import org.example.projectj3.pojo.Tag;

import java.util.List;

public interface TagDAO {
    List<Tag> getAllTags();

    Tag getTagById(int tagId);

    boolean createTag(Tag tag);

    boolean updateTag(Tag tag);

    boolean deleteTag(int tagId);

    boolean linkTagToTask(int tagId, int taskId);

    List<Tag> getTagsByTaskId(int taskId);
}
