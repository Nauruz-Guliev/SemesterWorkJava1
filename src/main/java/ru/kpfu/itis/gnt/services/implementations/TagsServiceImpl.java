package ru.kpfu.itis.gnt.services.implementations;

import org.springframework.dao.EmptyResultDataAccessException;
import ru.kpfu.itis.gnt.DAO.TagNamesRepository;
import ru.kpfu.itis.gnt.DAO.implementations.PostsRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.TagsRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.entities.Tag;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.services.TagsService;

import java.util.ArrayList;
import java.util.List;

public class TagsServiceImpl implements TagsService {


    private final TagsRepositoryImpl tagsDao;

    private final TagNamesRepository tagNamesDao;

    public TagsServiceImpl(TagsRepositoryImpl tagsDao, TagNamesRepository tagNamesDao) {
        this.tagsDao = tagsDao;
        this.tagNamesDao = tagNamesDao;
    }

    @Override
    public List<Tag> findAllTags(int postId) throws DBException {
        try {
            List<Tag> tags = tagsDao.findAllTags(postId).get();
            for (Tag tag : tags) {
                tag.setName(
                        tagNamesDao.findTagNameById(tag.getTag_name_id()).get().getName()
                );
            }
            return tags;
        } catch (EmptyResultDataAccessException ex) {
            throw new DBException("Tag was not found!");
        }
    }
}
