package ru.diasoft.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.library.models.Author;
import ru.diasoft.library.repositories.AuthorRepositoryJpa;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepositoryJpa authorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAll() {
        return authorRepository.getAll();
    }

    @Override
    @Transactional
    public Author create(String name) {
        return authorRepository.save(new Author(0, name));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Author> getByName(String name) {

        return authorRepository.getByName(name);
    }
}
