package ru.kpfu.itis.gnt.DAO;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.gnt.entities.Post;
import ru.kpfu.itis.gnt.entities.User;

import java.util.List;
import java.util.Optional;

public class PostsRepositoryImpl implements PostsRepository {

    //language=SQL
    private final static String SQL_GET_ALL_POSTS = "SELECT * from posts";
    //language=SQL
    private final static String SQL_GET_POST_BY_ID = "SELECT * from posts where id = ?";
    //language=SQL
    private final static String SQL_UPDATE_POST = "UPDATE posts SET title=?, post_body =?, edited_at = current_timestamp where id = ?";

    private final JdbcTemplate jdbcTemplate;

    public PostsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<List<Post>> findAllPosts() {
        List<Post> postList = jdbcTemplate.query(SQL_GET_ALL_POSTS, postMapper);
        if (!postList.isEmpty()) {
            return Optional.of(postList);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Post> findPostById(int postId) {
        try {
            Post post = jdbcTemplate.queryForObject(SQL_GET_POST_BY_ID, new Object[]{postId}, postMapper);
            if (post != null) {
                return Optional.of(post);
            }
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
        return Optional.empty();
    }


    @Override
    public boolean updatePost(Post post) {
        return jdbcTemplate.update(SQL_UPDATE_POST, post.getTitle(), post.getBody(), post.getId()) > 0;
    }


    private final RowMapper<Post> postMapper =
            (row, rowNumber) -> {
                Post post = new Post(
                        row.getString("title"),
                        row.getString("post_body"),
                        row.getInt("likes"),
                        row.getInt("author_id"),
                        row.getInt("category_id")
                );
                post.setId(row.getInt("id"));
                return post;
            };

}
