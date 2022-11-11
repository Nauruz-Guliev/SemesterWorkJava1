package ru.kpfu.itis.gnt.DAO.implementations;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.gnt.DAO.TagsRepository;
import ru.kpfu.itis.gnt.entities.Post;
import ru.kpfu.itis.gnt.entities.Tag;
import ru.kpfu.itis.gnt.entities.TagName;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class TagsRepositoryImpl implements TagsRepository {

    //language=SQL
    private static final String SQL_REMOVE_TAG = "DELETE from post_tags where post_id=? and tag_name_id=?";

    //language=SQL
    private static final String SQL_FIND_ALL_TAGS = "SELECT * from post_tags where post_id=?";

    //language=SQL
    private static final String SQL_INSERT_TAG = "INSERT INTO post_tags(tag_name_id, post_id) values (?,?)";

    private final JdbcTemplate jdbcTemplate;

    public TagsRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean addTag(int postId, int tagNameId) throws DuplicateKeyException {
        try {
            return jdbcTemplate.update(
                    SQL_INSERT_TAG,
                    tagNameId,
                    postId
            ) > 0;
        } catch (DuplicateKeyException ex) {
            //нельзя один и тот же тэг добавить дважды, поэтому он просто не добавится
            return false;
        }
    }

    @Override
    public Optional<List<Tag>> findAllTags(int postId) {
        return Optional.of(jdbcTemplate.query(SQL_FIND_ALL_TAGS, new Object[]{postId}, tagRowMapper));
    }

    @Override
    public boolean removeTag(int postId, int tagNameId) {
        return jdbcTemplate.update(
                SQL_REMOVE_TAG,
                postId,
                tagNameId
        ) > 0;
    }

    private final RowMapper<Tag> tagRowMapper =
            (row, rowNumber) -> {
                Tag tag = new Tag(
                        row.getInt("post_id"),
                        row.getInt("tag_name_id")
                );
                tag.setId(row.getInt("id"));
                return tag;
            };
}
