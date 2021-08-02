package ru.diasoft.library.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.diasoft.library.models.Author;
import ru.diasoft.library.models.Book;
import ru.diasoft.library.models.Comment;
import ru.diasoft.library.models.Genre;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий для работы с книгами должен")
@DataJpaTest
class BookRepositoryJpaTest {

    public static final long AUTHOR_ID = 1L;
    public static final String AUTHOR_NAME = "Ушинский К.Д.";
    public static final long GENRE_ID = 2L;
    public static final String GENRE_NAME = "Рассказ";
    public static final long BOOK_ID = 1L;
    public static final String TITLE = "Слепая лошадь";

    public static final long NEW_BOOK_ID = 5L;
    public static final String NEW_BOOK_TITLE = "Бишка";

    @Autowired
    private BookRepositoryJpa repository;

    Book createdBook, book;
    Author author;
    Set<Author> authorList = new HashSet<>();
    Genre genre;
    Set<Genre> genreList = new HashSet<>();
    List<Book> books = new ArrayList<>();
    List<String> authors = new ArrayList<>();
    List<String> genres = new ArrayList<>();
    Set<Comment> comments = new HashSet<>();

    @BeforeEach
    void setUp() {
        author = Author.builder().id(AUTHOR_ID).name(AUTHOR_NAME).build();

        genre = Genre.builder().id(GENRE_ID).name(GENRE_NAME).build();

        authorList.add(author);

        genreList.add(genre);

        /*authors.add(AUTHOR_NAME);
        genres.add(GENRE_NAME);*/

        book = Book.builder()
                .id(NEW_BOOK_ID)
                .title(NEW_BOOK_TITLE)
                .authors(authorList)
                .genres(genreList)
                .comments(comments)
                .build();

        books.add(book);
    }


    @DisplayName("сохранять книгу")
    @Test
    void createBookTest(){
        int bookCountBeforeSave = repository.findAll().size();
        createdBook = repository.save(book);
        int bookCountAfterSave = repository.findAll().size();

        assertThat(createdBook).isEqualTo(book);
        assertThat(bookCountAfterSave).isGreaterThan(bookCountBeforeSave);
    }

    @DisplayName("удалять книгу")
    @Test
    void deleteBookTest(){

        createdBook = repository.save(book);
        int bookCountBeforeDelete = repository.findAll().size();
        repository.delete(createdBook);
        int bookCountAfterDelete = repository.findAll().size();

        assertThat(bookCountAfterDelete).isLessThan(bookCountBeforeDelete);
    }

    @DisplayName("искать книгу по названию")
    @Test
    void findByTitleTest() {

        createdBook = repository.save(book);
        Optional<Book> findBook = repository.findByTitle(NEW_BOOK_TITLE);

        assertNotNull(findBook);
        assertThat(findBook.get().getTitle()).isEqualTo(book.getTitle());
        assertThat(findBook.get().getAuthors()).isEqualTo(book.getAuthors());
        assertThat(findBook.get().getGenres()).isEqualTo(book.getGenres());

    }

    @DisplayName("искать все книги")
    @Test
    void findAllTest() {
        repository.save(book);
        assertNotNull(repository.findAll());
        assertThat(repository.findAll().size()).isGreaterThan(0);
    }

    @DisplayName("искать книгу по автору")
    @Test
    void findByAuthorsTest() {
        repository.save(book);
        List<Book> findBooks = repository.findByAuthors(author);

        assertNotNull(findBooks);
        assertThat(findBooks.size()).isEqualTo(2);

    }

    @DisplayName("искать книгу по жанру")
    @Test
    void findByGenresTest() {
        repository.save(book);
        List<Book> findBooks = repository.findByGenres(genre);

        assertNotNull(findBooks);
        assertThat(findBooks.size()).isEqualTo(2);
    }
}