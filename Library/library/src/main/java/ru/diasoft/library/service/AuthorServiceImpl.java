package ru.diasoft.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.diasoft.library.dao.AuthorDao;
import ru.diasoft.library.domain.Author;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
    }

    @Override
    public Author create(String name) {
        return authorDao.create(name);
    }

    @Override
    public Author getByName(String name) {

        return authorDao.getByName(name).orElseGet(()->authorDao.create(name));
    }
}
