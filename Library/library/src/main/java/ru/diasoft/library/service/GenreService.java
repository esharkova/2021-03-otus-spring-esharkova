package ru.diasoft.library.service;

import ru.diasoft.library.domain.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> getAll();

    Genre create(String name);

    Genre getByName(String name);

}
