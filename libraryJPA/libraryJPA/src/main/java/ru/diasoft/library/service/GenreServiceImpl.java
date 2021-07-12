package ru.diasoft.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.library.models.Genre;
import ru.diasoft.library.repositories.GenreRepositoryJpa;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class GenreServiceImpl implements GenreService {

    private final GenreRepositoryJpa genreRepository;

    @Override
    public List<Genre> getAll() {
        return genreRepository.getAll();
    }

    @Override
    public Genre create(String name) {
            return genreRepository.save(new Genre(0,name));
    }

    @Override
    public Optional<Genre> getByName(String name) {
        return genreRepository.getByName(name);

    }

}
