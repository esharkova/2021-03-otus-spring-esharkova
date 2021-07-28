package ru.diasoft.library.repositories;

import ru.diasoft.library.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepositoryJpa {

    List<Genre> getAll();

    Optional<Genre> getByName(String name);

    Genre save(Genre genre);
}

