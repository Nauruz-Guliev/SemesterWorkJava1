package ru.kpfu.itis.gnt.services.implementations;

import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.dto.UserSignUp;
import ru.kpfu.itis.gnt.entities.User;
import ru.kpfu.itis.gnt.services.UsersService;

public class UsersServiceImpl implements UsersService {

    private final UsersRepositoryJDBCTemplateImpl usersRepository;

    public UsersServiceImpl(UsersRepositoryJDBCTemplateImpl usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void signUp(UserSignUp form) {
        User user = new User(
                form.getFirstName(),
                form.getLastName(),
                form.getEmail(),
                form.getPassword(),
                form.getGender(),
                form.getDateOfBirth(),
                form.getCountry()
        );
        usersRepository.saveUser(user);
    }
}
