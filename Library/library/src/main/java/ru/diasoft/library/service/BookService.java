package ru.diasoft.library.service;

import ru.diasoft.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book create(String title, String authorName, String genreName);

    Book getById(long id);

    Optional<Book> getByTitle(String title);

    List<Book> getAll();

    void deleteById(long id);

    void update(Book book);

}
