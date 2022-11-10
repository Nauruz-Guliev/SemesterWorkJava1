package ru.kpfu.itis.gnt.DAO;

import org.springframework.dao.DuplicateKeyException;
import ru.kpfu.itis.gnt.entities.Tag;

import java.util.Optional;

public interface TagsRepository {
    boolean addTag(int postId, int tagNameId) throws DuplicateKeyException;
    Optional<Tag> findAllTags(int postId);
    boolean removeTag(int postId, int tagNameId);
}
