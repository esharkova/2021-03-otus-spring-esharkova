package ru.diasoft.library.service;

import ru.diasoft.library.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> getAll();

    Author create(String name);

    Optional<Author> getByName(String name);
}
