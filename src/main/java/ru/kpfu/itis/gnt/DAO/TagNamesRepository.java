package ru.kpfu.itis.gnt.DAO;


import ru.kpfu.itis.gnt.entities.TagName;

import java.util.Optional;

public interface TagNamesRepository {
    boolean addNewTagName(String tagName);
    Optional<TagName> findTagNameById(int tag_id);
    Optional<Integer> findTagNameByName(String tag_id);

}
