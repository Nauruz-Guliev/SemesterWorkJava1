package ru.kpfu.itis.gnt.DAO.implementations;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.gnt.DAO.LikesRepository;
import ru.kpfu.itis.gnt.entities.Like;
import ru.kpfu.itis.gnt.entities.Post;

import javax.sql.DataSource;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class LikesRepositoryImpl implements LikesRepository {

    //language=SQL
    private final static String SQL_LIKE_POST = "INSERT INTO likes(user_id, post_id) values (?,?)";

    //language=SQL
    private final static String SQL_LIKE_COMMENT = "INSERT INTO likes(user_id, comment_id) values (?,?)";

    //language=SQL
    private final static String SQL_FIND_COMMENT_COUNT = "SELECT count(comment_id) from likes where comment_id = ?";

    //language=SQL
    private final static String SQL_FIND_POST_COUNT = "SELECT count(post_id) from likes where post_id = ?";

    //language=SQL
    private final static String SQL_FIND_COMMENT_LIKE = "SELECT * from likes where comment_id=? and user_id=?";

    //language=SQL
    private final static String SQL_FIND_POST_LIKE = "SELECT * from likes where post_id=? and user_id=?";

    //language=SQL
    private final static String SQL_DELETE_COMMENT_LIKE = "DELETE from likes where comment_id=? and user_id =?";

    //language=SQL
    private final static String SQL_DELETE_POST_LIKE = "DELETE from likes where post_id=? and user_id=?";

    private JdbcTemplate jdbcTemplate;

    public LikesRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public boolean likePost(int user_id, int post_id) {
        return jdbcTemplate.update(SQL_LIKE_POST, user_id, post_id) > 0;
    }

    @Override
    public boolean likeComment(int user_id, int comment_id) {
        return jdbcTemplate.update(SQL_LIKE_COMMENT, user_id, comment_id) > 0;
    }

    @Override
    public boolean findCommentLike(int user_id, int comment_id) {
        return jdbcTemplate.update(SQL_FIND_COMMENT_LIKE, comment_id, user_id) > 0;

    }

    @Override
    public boolean isPostLikePresent(int user_id, int post_id) {
        try {
            jdbcTemplate.queryForObject(SQL_FIND_POST_LIKE, new Object[]{post_id, user_id}, likeRowMapper);
            return true;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }

    @Override
    public boolean deleteCommentLike(int user_id, int comment_id) {
        return jdbcTemplate.update(SQL_DELETE_COMMENT_LIKE, comment_id, user_id) > 0;
    }

    @Override
    public boolean deletePostLike(int user_id, int post_id) {
        return jdbcTemplate.update(SQL_DELETE_POST_LIKE, post_id, user_id) > 0;
    }

    @Override
    public int countCommentLike(int comment_id) throws NullPointerException {
        return jdbcTemplate.queryForObject(SQL_FIND_COMMENT_COUNT,
                new Object[]{comment_id},
                Integer.class
        );

    }

    @Override
    public int countPostLike(int post_id) throws NullPointerException {
        return jdbcTemplate.queryForObject(SQL_FIND_POST_COUNT,
                new Object[]{post_id},
                Integer.class
        );
    }

    private final RowMapper<Like> likeRowMapper =
            (row, rowNumber) -> {
                Like like = new Like(
                        row.getInt("user_id"),
                        row.getInt("comment_id"),
                        row.getInt("post_id")
                );

                like.setId(row.getInt("id"));
                return like;
            };

}
