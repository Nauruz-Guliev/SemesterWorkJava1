package ru.kpfu.itis.gnt.DAO.implementations;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.gnt.DAO.TagsRepository;
import ru.kpfu.itis.gnt.entities.Tag;
import ru.kpfu.itis.gnt.entities.TagName;

import javax.sql.DataSource;
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
        return jdbcTemplate.update(
                SQL_INSERT_TAG,
                tagNameId,
                postId
        ) > 0;
    }

    @Override
    public Optional<Tag> findAllTags(int postId) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                        SQL_FIND_ALL_TAGS,
                        new Object[]{postId},
                        tagRowMapper
                )
        );
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
                        row.getInt("tag_name_id"),
                        row.getInt("post_id")
                );
                tag.setId(row.getInt("id"));
                return tag;
            };
}
