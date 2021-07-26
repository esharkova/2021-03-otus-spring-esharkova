package ru.diasoft.library.repositories;

import ru.diasoft.library.dto.BookCommetDto;
import ru.diasoft.library.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryJpa {

    Optional<Book> getById(long id);

    Optional<Book> getByTitle(String title);

    List<Book> getAll();

    List<BookCommetDto> getCommetByBook(String title);

    void deleteById(long id);

    Book save(Book book);
}
