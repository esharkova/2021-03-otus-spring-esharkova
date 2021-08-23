package ru.diasoft.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.library.models.Author;
import ru.diasoft.library.models.Book;
import ru.diasoft.library.models.Genre;
import ru.diasoft.library.repositories.AuthorRepositoryJpa;
import ru.diasoft.library.repositories.BookRepositoryJpa;
import ru.diasoft.library.repositories.GenreRepositoryJpa;

import java.util.*;


@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepositoryJpa bookRepository;
    private final AuthorRepositoryJpa authorRepository;
    private final GenreRepositoryJpa genreRepository;

    @Override
    @Transactional
    public Book create(String title, List<String> authors, List<String> genres) {

        Set<Author> authorList = new HashSet<>();
        Set<Genre> genreList = new HashSet<>();

        for(String author: authors) {
            authorList.add(authorRepository.findByName(author).get());
        }

        for (String genre : genres) {
           genreList.add(genreRepository.findByName(genre).get());
        }

        Book newBook = new Book(0, title, authorList, genreList, null);
        bookRepository.save(newBook);

        return newBook;
    }

    @Override
    @Transactional
    public Book save(Book book) {

        return bookRepository.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Book getById(long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> getByTitle(String title) {

        return bookRepository.findByTitle(title);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getByAuthor(Author author) {

        return bookRepository.findByAuthors(author);
    }

    @Override
    public List<Book> getByGenre(Genre genre) {
        return bookRepository.findByGenres(genre);
    }

}