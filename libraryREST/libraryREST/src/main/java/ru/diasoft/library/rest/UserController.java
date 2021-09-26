package ru.diasoft.library.rest;

import org.springframework.web.bind.annotation.RestController;
import ru.diasoft.library.repositories.UsersRepository;

@RestController
public class UserController {
    private final UsersRepository usersRepository;

    public UserController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
}
