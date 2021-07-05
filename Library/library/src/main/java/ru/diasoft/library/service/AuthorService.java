package ru.diasoft.library.service;

import ru.diasoft.library.domain.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAll();

    Author create(String name);

    Author getByName(String name);
}
