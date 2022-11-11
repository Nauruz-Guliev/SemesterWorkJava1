package ru.kpfu.itis.gnt.DAO;

import ru.kpfu.itis.gnt.entities.User;
import ru.kpfu.itis.gnt.exceptions.EmptyResultDbException;

import java.util.List;
import java.util.Optional;

public interface UsersRepository {
    Optional<List<User>> findAll() throws EmptyResultDbException;
    boolean saveUser(User user);

    boolean deleteUser(int userId);

    boolean updateUser(User user);

    boolean updateCountry(String country, int userId);
    Optional<User> findById(int id) throws EmptyResultDbException;

    Optional<User> findUser(String email, String password) throws EmptyResultDbException;
}
