package ru.diasoft.library.service;

import ru.diasoft.library.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    List<Genre> getAll();

    Genre create(String name);

    Optional<Genre> getByName(String name);

}
