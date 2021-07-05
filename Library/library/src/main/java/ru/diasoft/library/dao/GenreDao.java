package ru.diasoft.library.dao;

import ru.diasoft.library.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    Genre create(String name);

    Optional<Genre> getByName(String name);

    List<Genre> getAll();

    Genre getById(long id);
}
