package ru.kpfu.itis.gnt.DAO.implementations;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.gnt.DAO.CommentsRepository;
import ru.kpfu.itis.gnt.entities.Comment;
import ru.kpfu.itis.gnt.exceptions.EmptyResultDbException;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;


public class CommentsRepositoryImpl implements CommentsRepository {

    private final JdbcTemplate jdbcTemplate;

    //language=SQL
    private static final String SQL_GET_ALL_COMMENTS = "SELECT * FROM comments where post_id =? LIMIT ? offset ?";

    //language=SQL
    private static final String SQL_ADD_COMMENT = "INSERT INTO comments(text, post_id, author_id)" +
            "values(?,?,?)";


    //language=SQL
    private static final String SQL_DELETE_COMMENT = "DELETE FROM comments where id =?";

    //language=SQL
    private static final String SQL_COMMENT_COUNT = "SELECT COUNT(*) from comments where post_id = ?";


    public CommentsRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<List<Comment>> findComments(int postId, int limit, int offset) throws EmptyResultDbException {
        try {
            return Optional.of(jdbcTemplate.query(SQL_GET_ALL_COMMENTS,
                    new Object[]{postId, limit, offset},
                    commentRowMapper)
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new EmptyResultDbException("Comment was not found");
        }
    }

    public Optional<Integer> getCommentCount(int post_id) throws EmptyResultDbException {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_COMMENT_COUNT,
                    new Object[]{post_id},
                    Integer.class
            ));
        }  catch (EmptyResultDataAccessException ex) {
            throw new EmptyResultDbException("Couldn't get comment count");
        }
    }


    @Override
    public boolean addComment(Comment comment) {
        return jdbcTemplate.update(
                SQL_ADD_COMMENT,
                comment.getText(),
                comment.getPost_id(),
                comment.getAuthor_id()
        ) > 0;
    }

    @Override
    public boolean deleteComment(Comment comment) {
        return jdbcTemplate.update(SQL_DELETE_COMMENT, comment.getId()) > 0;
    }

    private final RowMapper<Comment> commentRowMapper =
            (row, rowNumber) -> {
                Comment comment = new Comment(
                        row.getString("text"),
                        row.getInt("post_id"),
                        row.getInt("author_id")
                );
                comment.setCreated_at(row.getString("created_at"));
                comment.setId(row.getInt("id"));
                return comment;
            };
}
