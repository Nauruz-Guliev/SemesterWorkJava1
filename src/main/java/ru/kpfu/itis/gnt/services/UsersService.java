package ru.kpfu.itis.gnt.services;

import ru.kpfu.itis.gnt.dto.UserSignUp;
import ru.kpfu.itis.gnt.entities.User;

public interface UsersService {
    void signUp(UserSignUp form);
}
