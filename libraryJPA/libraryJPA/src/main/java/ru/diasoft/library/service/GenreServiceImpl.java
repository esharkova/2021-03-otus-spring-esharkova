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
public class GenreServiceImpl implements GenreService {

    private final GenreRepositoryJpa genreRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Genre> getAll() {
        return genreRepository.getAll();
    }

    @Override
    @Transactional
    public Genre create(String name) {
            return genreRepository.save(new Genre(0,name));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Genre> getByName(String name) {
        return genreRepository.getByName(name);

    }

}
