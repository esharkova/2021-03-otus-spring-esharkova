package ru.diasoft.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.library.models.Author;
import ru.diasoft.library.models.Book;
import ru.diasoft.library.models.Comment;
import ru.diasoft.library.models.Genre;
import ru.diasoft.library.repositories.AuthorRepositoryJpa;
import ru.diasoft.library.repositories.BookRepositoryJpa;
import ru.diasoft.library.repositories.CommentRepositoryJpa;
import ru.diasoft.library.repositories.GenreRepositoryJpa;

import javax.persistence.NoResultException;
import java.util.*;

import static org.apache.logging.log4j.ThreadContext.isEmpty;


@RequiredArgsConstructor
@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepositoryJpa bookRepository;
    private final AuthorRepositoryJpa authorRepository;
    private final GenreRepositoryJpa genreRepository;
    private final CommentRepositoryJpa commentRepository;

    @Override
    public Book create(String title, List<String> authors, List<String> genres) {

        Set<Author> authorList = new HashSet<>();
        Set<Genre> genreList = new HashSet<>();

        for(String author: authors) {
            authorList.add(authorRepository.getByName(author).get());
        }

        for (String genre : genres) {
           genreList.add(genreRepository.getByName(genre).get());
        }

        Book newBook = new Book(0, title, authorList, genreList, null);
        System.out.println(newBook);
        bookRepository.save(newBook);

        return newBook;
    }

    @Override
    public Book save(Book book) {

        return bookRepository.save(book);
    }

    @Override
    public Book getById(long id) {
        return bookRepository.getById(id).orElse(null);
    }

    @Override
    public Optional<Book> getByTitle(String title) {

        return bookRepository.getByTitle(title);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);

    }

}
