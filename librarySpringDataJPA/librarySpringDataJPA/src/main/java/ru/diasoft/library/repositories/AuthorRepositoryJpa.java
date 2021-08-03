package ru.diasoft.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diasoft.library.models.Author;

import java.util.Optional;

public interface AuthorRepositoryJpa extends JpaRepository<Author, Long> {

    Optional<Author> findByName(String name);
}
