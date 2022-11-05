package ru.kpfu.itis.gnt.DAO;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.gnt.entities.Comment;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;


public class CommentsRepositoryImpl implements CommentsRepository {

    private final JdbcTemplate jdbcTemplate;

    //language=SQL
    private static final String SQL_GET_ALL_COMMENTS = "SELECT * FROM comments where post_id =?";

    //language=SQL
    private static final String SQL_ADD_COMMENT = "INSERT INTO comments(text, post_id, author_id)" +
            "values(?,?,?)";


    //language=SQL
    private static final String SQL_DELETE_COMMENT = "DELETE FROM comments where id =?";


    public CommentsRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<List<Comment>> findAllComments(int postId) {
        try {
            List<Comment> comments = jdbcTemplate.query(SQL_GET_ALL_COMMENTS, new Object[]{postId}, commentRowMapper);
            if (!comments.isEmpty()) {
                return Optional.of(comments);
            }
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
        return Optional.empty();
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
                comment.setCreated_at(row.getString("create_at"));
                comment.setId(row.getInt("id"));
                return comment;
            };
}
