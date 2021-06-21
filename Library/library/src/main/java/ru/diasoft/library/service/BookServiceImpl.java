package ru.diasoft.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.diasoft.library.dao.AuthorDao;
import ru.diasoft.library.dao.BookDao;
import ru.diasoft.library.dao.GenreDao;
import ru.diasoft.library.domain.Author;
import ru.diasoft.library.domain.Book;
import ru.diasoft.library.domain.Genre;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Override
    public Book create(String title, String authorName, String genreName) {
        Author author = authorDao.getByName(authorName).orElseGet(()->authorDao.create(authorName));

        Genre genre = genreDao.getByName(genreName).orElseGet(()->genreDao.create(genreName));

        return bookDao.create(title, author, genre);
    }

    @Override
    public Book getById(long id) {
        return bookDao.getById(id).orElse(null);
    }

    @Override
    public Optional<Book> getByTitle(String title) {

        return Optional.ofNullable(bookDao.getByTitle(title).orElse(null));
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public void deleteById(long id) {
        bookDao.deleteById(id);

    }

    @Override
    public void update(Book book) {
            bookDao.update(book);
    }
}
