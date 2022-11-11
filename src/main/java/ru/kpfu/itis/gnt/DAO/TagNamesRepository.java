package ru.kpfu.itis.gnt.DAO;


import ru.kpfu.itis.gnt.entities.TagName;
import ru.kpfu.itis.gnt.exceptions.EmptyResultDbException;

import java.util.Optional;

public interface TagNamesRepository {
    boolean addNewTagName(String tagName);
    Optional<TagName> findTagNameById(int tag_id) throws EmptyResultDbException;
    Optional<Integer> findTagNameByName(String tag_id);

}
