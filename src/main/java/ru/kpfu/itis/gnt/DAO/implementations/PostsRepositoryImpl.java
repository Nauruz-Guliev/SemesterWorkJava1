package ru.kpfu.itis.gnt.DAO.implementations;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcCallOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.kpfu.itis.gnt.DAO.PostsRepository;
import ru.kpfu.itis.gnt.entities.Post;
import ru.kpfu.itis.gnt.entities.User;
import ru.kpfu.itis.gnt.exceptions.EmptyResultDbException;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PostsRepositoryImpl implements PostsRepository {

    //language=SQL
    private final static String SQL_GET_ALL_POSTS = "SELECT * from posts order by posts.created_at desc limit ? offset ?";
    //language=SQL
    private final static String SQL_GET_POST_BY_ID = "SELECT * from posts where id = ?";

    //language=SQL
    private final static String SQL_GET_ALL_POSTS_BY_TAG_ID = "SELECT p.* from posts p join post_tags pt on pt.post_id = p.id where tag_name_id = ?";
    //language=SQL
    private final static String SQL_UPDATE_POST = "UPDATE posts SET title=?, post_body =?, edited_at = current_timestamp where id = ?";

    //language=SQL
    private final static String SQL_INSERT_POST = "INSERT INTO posts(title, post_body, author_id)" +
            "values(?, ? , ?)";

    //language=SQL
    private final static String SQL_GET_MOST_POPULAR_POSTS = "with likes_count as (" +
            "select post_id, count(*) as lk from likes group by post_id )" +
            "select p.* from posts p join likes_count l on p.id = l.post_id order by l.lk desc limit 5";

    private final JdbcTemplate jdbcTemplate;


    public PostsRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean updatePost(Post post) {
        return jdbcTemplate.update(SQL_UPDATE_POST, post.getTitle(), post.getBody(), post.getId()) > 0;
    }

    public Optional<List<Post>> findMostPopular() throws EmptyResultDbException {
        try {
            return Optional.of(jdbcTemplate.query(SQL_GET_MOST_POPULAR_POSTS, postMapper));
        } catch (EmptyResultDataAccessException ex) {
            throw new EmptyResultDbException("Most popular posts were not found");
        }
    }

    @Override
    public Optional<List<Post>> findPosts(int limit, int offset) throws EmptyResultDbException {
        try {
            return Optional.of(jdbcTemplate.query(SQL_GET_ALL_POSTS, postMapper, limit, offset));
        } catch (EmptyResultDataAccessException ex) {
            throw new EmptyResultDbException("Posts were not found");
        }
    }

    public Optional<List<Post>> getPostsByTagId(int tagId) throws EmptyResultDbException {
        try {
            return Optional.of(jdbcTemplate.query(SQL_GET_ALL_POSTS_BY_TAG_ID, postMapper, tagId));
        } catch (EmptyResultDataAccessException ex) {
            throw new EmptyResultDbException("Tag was not found");
        }
    }

    @Override
    public Optional<Post> findPostById(int postId) throws EmptyResultDbException {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_GET_POST_BY_ID, new Object[]{postId}, postMapper));
        } catch (EmptyResultDataAccessException ex) {
            throw new EmptyResultDbException("Post was not found");
        }
    }


    @Override
    public void addPost(Post post) {
        Map<String, Object> params = new HashMap<>();
        params.put("title", post.getTitle());
        params.put("post_body", post.getBody());
        params.put("author_id", post.getAuthor_id());

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        int id = insert.withTableName("posts")
                .usingGeneratedKeyColumns("id")
                .usingColumns("title", "post_body", "author_id")
                .executeAndReturnKey(new MapSqlParameterSource(params)).intValue();
        post.setId(id);
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
