package ru.kpfu.itis.gnt.DAO;

import ru.kpfu.itis.gnt.entities.User;

import java.util.List;
import java.util.Optional;

public interface UsersRepository {
    Optional<List<User>> findAll();
    boolean saveUser(User user);

    boolean deleteUser(int userId);

    boolean updateUser(User user);

    boolean updateCountry(String country, int userId);
    Optional<User> findById(int id);

    Optional<User> findUser(String email, String password);
}
