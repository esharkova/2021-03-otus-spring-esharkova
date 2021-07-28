package ru.diasoft.library.repositories;

import ru.diasoft.library.models.Author;


import java.util.List;
import java.util.Optional;

public interface AuthorRepositoryJpa {
    List<Author> getAll();

    Optional<Author> getByName(String name);

    Author save(Author author);
}
