package ru.diasoft.library.service;

import ru.diasoft.library.models.Author;
import ru.diasoft.library.models.Book;
import ru.diasoft.library.models.Genre;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book create(String title, List<String> authors, List<String> genres);

    Book save(Book book);

    Book getById(long id);

    Optional<Book> getByTitle(String title);

    List<Book> getAll();

    void deleteById(long id);

    List<Book> getByAuthor(Author author);

    List<Book> getByGenre(Genre genre);

}
