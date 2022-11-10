package ru.kpfu.itis.gnt.services;

import ru.kpfu.itis.gnt.entities.Tag;
import ru.kpfu.itis.gnt.exceptions.DBException;

import java.util.List;

public interface TagsService {
    List<Tag> findAllTags(int postId) throws DBException;
}
