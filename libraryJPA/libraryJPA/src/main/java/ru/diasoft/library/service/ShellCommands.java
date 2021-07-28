package ru.diasoft.library.service;

import org.springframework.boot.jdbc.metadata.CommonsDbcp2DataSourcePoolMetadata;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.library.models.Author;
import ru.diasoft.library.models.Book;
import ru.diasoft.library.models.Comment;
import ru.diasoft.library.models.Genre;

import javax.persistence.NoResultException;
import java.util.*;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final CommentService commentService;

    @ShellMethod(value = "All Authors",  key = {"AA", "All Authors"})
    public void ShowAllAuthors() {

        System.out.println(authorService.getAll());
    }

    @ShellMethod(value = "Add Author",  key = {"AddA", "Add Author"})
    public void AddAuthor(String name) {

        System.out.println(authorService.create(name));
        System.out.println(authorService.getAll());
    }

    @ShellMethod(value = "All Genres",  key = {"AG", "All Genres"})
    public void ShowAllGenres() {

        System.out.println(genreService.getAll());
        for (Genre genre: genreService.getAll()){
            System.out.println("ID :" + genre.getId() + " Жанр :" + genre.getName());
        }
    }

    @ShellMethod(value = "Add Genre",  key = {"AddG", "Add Genre"})
    public void AddGenre(String name) {

        System.out.println(genreService.create(name));
        System.out.println(genreService.getAll());
    }

    @ShellMethod(value = "All Books",  key = {"AB", "All Books"})
    public void ShowAllBooks() {

        System.out.println(bookService.getAll());
    }

    @ShellMethod(value = "Add Book",  key = {"AddB", "Add Book"})
    public void AddBook(String title, String authorName, String genreName) {

        List<String> authors = Arrays.asList(authorName.split(","));

        for(String author: authors){

            if (authorService.getByName(author).equals(Optional.empty())){
                authorService.create(author);
            }
        }

        List<String> genres = Arrays.asList(genreName.split(","));

        for(String genre: genres){

            if (genreService.getByName(genre).equals(Optional.empty())){
                genreService.create(genre);
            }
        }

        bookService.create(title, authors, genres);

        System.out.println("После добавления:");
        for (Book book: bookService.getAll()){
            System.out.println("ID: " + book.getId()
                    + " Название: " + book.getTitle()
                    + " Автор: " + book.getAuthors()
                    + " Жанр: " +book.getGenres());
        }
    }

    @ShellMethod(value = "Find Book",  key = {"FB", "Find Book"})
    public void FindBook(String title) {
        if (bookService.getByTitle(title).isPresent())
            System.out.println(bookService.getByTitle(title));
        else
            System.out.println("Книга не найдена!");
    }

    @ShellMethod(value = "Delete Book",  key = {"DB", "Delete Book"})
    public void DeleteBook(String title) {

        if (bookService.getByTitle(title).isPresent())
            bookService.deleteById(bookService.getByTitle(title).orElse(null).getId());
        else
            System.out.println("Книга не найдена!");
    }

    @Transactional
    @ShellMethod(value = "Add Comment",  key = {"AddC", "Add Comment"})
    public void AddComment(String title, String commentText) {

        Book commentedBook = bookService.getByTitle(title).get();

        if (commentedBook.equals(Optional.empty())){
            System.out.println("Книга не найдена!");

        }
        else{
            commentedBook.getComments().add(new Comment(0,commentText));
            bookService.save(commentedBook);
        }
    }

    @Transactional(readOnly = true)
    @ShellMethod(value = "Show Book Comments",  key = {"SC", "Show Book Comments"})
    public void ShowBookComments(String title) {

        Book commentedBook = bookService.getByTitle(title).get();

        if (commentedBook.equals(Optional.empty())){
            System.out.println("Книга не найдена!");
        }
        else{
            System.out.println(commentedBook.getComments().toString());
        }
    }

    @ShellMethod(value = "Show All Comments",  key = {"AC", "All Comments"})
    public void ShowAllComments() {

        System.out.println(commentService.getAll());
    }

    @Transactional
    @ShellMethod(value = "Delete Book Comments",  key = {"DC", "Delete Book Comments"})
    public void DeleteBookComments(String title) {

        Book commentedBook = bookService.getByTitle(title).get();

        if (commentedBook.equals(Optional.empty())){
            System.out.println("Книга не найдена!");
        }
        else{
            commentedBook.getComments().clear();
            bookService.save(commentedBook);
        }
    }

    @ShellMethod(value = "Update Book",  key = {"UB", "Update Book"})
    public void UpdateBook(Long id, String title, String authorName, String genreName) {

        Set<Author> authorList = new HashSet<>();
        Set<Genre> genreList = new HashSet<>();

        List<String> authors = Arrays.asList(authorName.split(","));

        for(String author: authors){
            if (authorService.getByName(author).equals(Optional.empty())){
                authorService.create(author);
            }
        }

        List<String> genres = Arrays.asList(genreName.split(","));

        for(String genre: genres){
            if (genreService.getByName(genre).equals(Optional.empty())){
                genreService.create(genre);
            }
        }

        for(String author: authors) {
            authorList.add(authorService.getByName(author).get());
        }

        for (String genre : genres) {
            genreList.add(genreService.getByName(genre).get());
        }

        Book updatedBook = bookService.getById(id);
        updatedBook.setTitle(title);
        updatedBook.setGenres(genreList);
        updatedBook.setAuthors(authorList);

        bookService.save(updatedBook);

        System.out.println("После обновления:");
        for (Book book: bookService.getAll()){
            System.out.println("ID: " + book.getId()
                    + " Название: " + book.getTitle()
                    + " Автор: " + book.getAuthors()
                    + " Жанр: " +book.getGenres());
        }
    }
}