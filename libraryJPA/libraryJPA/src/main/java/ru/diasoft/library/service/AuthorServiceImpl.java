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
@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepositoryJpa authorRepository;

    @Override
    public List<Author> getAll() {
        return authorRepository.getAll();
    }

    @Override
    public Author create(String name) {
        return authorRepository.save(new Author(0, name));
    }

    @Override
    public Optional<Author> getByName(String name) {

        return authorRepository.getByName(name);
    }
}
