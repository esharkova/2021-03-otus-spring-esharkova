package ru.diasoft.library.dao;

import ru.diasoft.library.domain.Author;
import ru.diasoft.library.domain.Book;
import ru.diasoft.library.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Book create(String title, Author author, Genre genre);

    Optional<Book> getById(long id);

    Optional<Book> getByTitle(String title);

    List<Book> getAll();

    void deleteById(long id);

    void update(Book book);
}
