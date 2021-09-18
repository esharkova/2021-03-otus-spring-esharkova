package ru.diasoft.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diasoft.library.models.Users;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByLogin(String login);
}
