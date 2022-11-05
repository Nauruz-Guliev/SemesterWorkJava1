package ru.kpfu.itis.gnt.DAO;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.kpfu.itis.gnt.entities.User;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UsersRepositoryJDBCTemplateImpl implements UsersRepository {


    private final JdbcTemplate jdbcTemplate;
   // private final SimpleJdbcInsert insert;
    //language=SQL
    private static final String SQL_INSERT_USER = "insert into users(firstname, lastname, email, password, country, gender, dateofbirth)" +
            "values (?,?,?,?,?,?,TO_DATE(?,'YYYYMMDD'))";

    //language=SQL
    private static final String SQL_FIND_USER = "Select * from users where email=? and password=?";

    //language=SQL
    private static final String SQL_DELETE_USER = "DELETE from users where id = ?";

    //language=SQL
    private static final String SQL_GET_ALL_USERS = "SELECT * from users";

    //language=SQL
    private static final String SQL_CHANGE_USER_COUNTRY = "UPDATE users SET country = ? where id=?";

    public UsersRepositoryJDBCTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
       // this.insert = new SimpleJdbcInsert(jdbcTemplate);
    }

    @Override
    public Optional<List<User>> findAll() {
        List<User> listOfUsers = jdbcTemplate.query(SQL_GET_ALL_USERS, userMapper);
        if (!listOfUsers.isEmpty()) {
            return Optional.of(listOfUsers);
        }
        return Optional.empty();
    }

    @Override
    public boolean saveUser(User user) {
        return jdbcTemplate.update(
                SQL_INSERT_USER,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getCountry(),
                user.getGender(),
                user.getDateOfBirth()
        ) > 0;
    }

    @Override
    public boolean deleteUser(int userId) {
        return jdbcTemplate.update(SQL_DELETE_USER, userId) > 0;
    }

    @Override
    public boolean updateCountry(String country, int userId) {
        return jdbcTemplate.update(SQL_CHANGE_USER_COUNTRY, country, userId) > 0;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findUser(String email, String password) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_USER, new Object[]{email, password}, userMapper);
            if (user != null) {
                return Optional.of(user);
            }
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
        return Optional.empty();
    }


    private final RowMapper<User> userMapper =
            (row, rowNumber) -> {
                User user = new User(
                        row.getString("firstName"),
                        row.getString("lastName"),
                        row.getString("email"),
                        row.getString("password"),
                        row.getString("gender"),
                        row.getString("dateOfBirth"),
                        row.getString("country")
                );
                user.setRole(row.getString("website_role"));
                user.setId(row.getInt("id"));
                return user;
            };


    /*
    @Override
    public void save(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("firstName", user.getFirstName());
        params.put("lastname", user.getLastName());
        params.put("email", user.getEmail());
        params.put("password", user.getPassword());
        params.put("country", user.getCountry());
        params.put("gender", user.getGender());
        params.put("dateofbirth", user.getDateOfBirth());

        int id = insert.withSchemaName("users")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new MapSqlParameterSource(params)).intValue();

        user.setId(id);
    }

     */


}
