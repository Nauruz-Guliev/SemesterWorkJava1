package ru.kpfu.itis.gnt.DAO.implementations;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.gnt.DAO.PostsRepository;
import ru.kpfu.itis.gnt.entities.Post;
import ru.kpfu.itis.gnt.entities.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class PostsRepositoryImpl implements PostsRepository {

    //language=SQL
    private final static String SQL_GET_ALL_POSTS = "SELECT * from posts order by posts.created_at desc limit ? offset ?";
    //language=SQL
    private final static String SQL_GET_POST_BY_ID = "SELECT * from posts where id = ?";
    //language=SQL
    private final static String SQL_UPDATE_POST = "UPDATE posts SET title=?, post_body =?, edited_at = current_timestamp where id = ?";

    //language=SQL
    private final static String SQL_INSERT_POST = "INSERT INTO posts(title, post_body, author_id)" +
            "values(?, ? , ?)";

    private final JdbcTemplate jdbcTemplate;

    public PostsRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean updatePost(Post post) {
        return jdbcTemplate.update(SQL_UPDATE_POST, post.getTitle(), post.getBody(), post.getId()) > 0;
    }

    @Override
    public Optional<List<Post>> findPosts(int limit, int offset) {
        return Optional.of(jdbcTemplate.query(SQL_GET_ALL_POSTS, postMapper, limit, offset));
    }

    @Override
    public Optional<Post> findPostById(int postId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_GET_POST_BY_ID, new Object[]{postId}, postMapper));
    }


    @Override
    public boolean addPost(Post post) {
        return jdbcTemplate.update(SQL_INSERT_POST, post.getTitle(), post.getAuthor_id()) > 0;
    }


    private final RowMapper<Post> postMapper =
            (row, rowNumber) -> {
                Post post = new Post(
                        row.getString("title"),
                        row.getString("post_body"),
                        row.getInt("author_id")
                );
                post.setId(row.getInt("id"));
                return post;
            };

}
