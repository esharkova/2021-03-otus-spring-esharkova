package ru.diasoft.library.service;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import lombok.RequiredArgsConstructor;
import ru.diasoft.library.domain.Author;
import ru.diasoft.library.domain.Book;
import ru.diasoft.library.domain.Genre;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;

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
        for (Book book: bookService.getAll()){
            System.out.println("ID: " + book.getId()
                    + " Название: " + book.getTitle()
                    + " Автор: " + book.getAuthor().getName()
                    + " Жанр: " +book.getGenre().getName() );
        }
    }

    @ShellMethod(value = "Add Book",  key = {"AddB", "Add Book"})
    public void AddBook(String title, String authorName, String genreName) {
        bookService.create(title, authorName, genreName);

        System.out.println("После добавления:");
        for (Book book: bookService.getAll()){
            System.out.println("ID: " + book.getId()
                    + " Название: " + book.getTitle()
                    + " Автор: " + book.getAuthor().getName()
                    + " Жанр: " +book.getGenre().getName() );
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


        System.out.println("После удаления:");
        for (Book book: bookService.getAll()){
            System.out.println("ID: " + book.getId()
                    + " Название: " + book.getTitle()
                    + " Автор: " + book.getAuthor().getName()
                    + " Жанр: " +book.getGenre().getName() );
        }

    }


    @ShellMethod(value = "Update Book",  key = {"UB", "Update Book"})
    public void UpdateBook(Long id, String title, String authorName, String genreName) {

        Author author = authorService.getByName(authorName);

        Genre genre = genreService.getByName(genreName);

        if (bookService.getById(id) != null){
            bookService.update(new Book(id, title, author, genre));
        }
        else
            System.out.println("Книга не найдена!");


        System.out.println("После обновления :");
        for (Book book: bookService.getAll()){
            System.out.println("ID: " + book.getId()
                    + " Название: " + book.getTitle()
                    + " Автор: " + book.getAuthor().getName()
                    + " Жанр: " +book.getGenre().getName() );
        }

    }

}

