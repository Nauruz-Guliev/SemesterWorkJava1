package ru.kpfu.itis.gnt.DAO.implementations;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.gnt.DAO.TagNamesRepository;
import ru.kpfu.itis.gnt.entities.TagName;
import ru.kpfu.itis.gnt.exceptions.EmptyResultDbException;

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
        try {
            jdbcTemplate.update(SQL_INSERT_TAG_NAME, tagName);
            return true;
        } catch (DuplicateKeyException ex) {
            //ограничение на уникальность. Если такое имя есть, значит его больше добавить не можем
            return false;
        }
    }

    @Override
    public Optional<TagName> findTagNameById(int tag_id) throws EmptyResultDbException {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            SQL_FIND_TAG_NAME_BY_ID,
                            new Object[]{tag_id},
                            tagNameRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new EmptyResultDbException("Couldn't find tag name");
        }
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
            //обрабатывается в сервисе
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
