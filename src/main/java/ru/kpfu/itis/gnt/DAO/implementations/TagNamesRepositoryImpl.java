package ru.kpfu.itis.gnt.DAO.implementations;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.gnt.DAO.TagNamesRepository;
import ru.kpfu.itis.gnt.entities.TagName;

import javax.sql.DataSource;
import java.util.Optional;

public class TagNamesRepositoryImpl implements TagNamesRepository {

    //language=SQL
    private final static String SQL_INSERT_TAG_NAME = "INSERT INTO tag_names(name) values(?)";

    //language=SQL
    private final static String SQL_FIND_TAG_NAME_BY_ID = "SELECT * from tag_names where id=?";

    //language=SQL
    private final static String SQL_FIND_TAG_NAME_BY_NAME = "SELECT id from tag_names where name=?";


    private final JdbcTemplate jdbcTemplate;

    public TagNamesRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean addNewTagName(String tagName) {
        return jdbcTemplate.update(SQL_INSERT_TAG_NAME, tagName) > 0;
    }

    @Override
    public Optional<TagName> findTagNameById(int tag_id) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                        SQL_FIND_TAG_NAME_BY_ID,
                        new Object[]{tag_id},
                        tagNameRowMapper
                )
        );
    }

    @Override
    public Optional<Integer> findTagNameByName(String tagName) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    SQL_FIND_TAG_NAME_BY_NAME,
                    new Object[]{tagName},
                    Integer.class
            ));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.of(0);
        }
    }

    private final RowMapper<TagName> tagNameRowMapper =
            (row, rowNumber) -> {
                TagName tagName = new TagName(
                        row.getString("name")
                );
                tagName.setId(row.getInt("id"));
                return tagName;
            };


}
