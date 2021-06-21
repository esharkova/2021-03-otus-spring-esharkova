package ru.diasoft.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.diasoft.library.dao.GenreDao;
import ru.diasoft.library.domain.Genre;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }

    @Override
    public Genre create(String name) {
            return genreDao.create(name);
    }

    @Override
    public Genre getByName(String name) {
        return genreDao.getByName(name).orElseGet(()->genreDao.create(name));

    }

}
