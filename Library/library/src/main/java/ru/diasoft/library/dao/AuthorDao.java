package ru.diasoft.library.dao;

import ru.diasoft.library.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    Author create(String name);

    Optional<Author> getById(long id);

    Optional<Author> getByName(String name);

    List<Author> getAll();

    void deleteById(long id);

}
