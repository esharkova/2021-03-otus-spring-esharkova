package ru.diasoft.library.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.diasoft.library.models.Author;
import ru.diasoft.library.models.Book;
import ru.diasoft.library.models.Comment;
import ru.diasoft.library.models.Genre;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий на основе Jpa для работы c книгами")
@DataJpaTest
@Import(BookRepositoryJpaImpl.class)
class BookRepositoryJpaImplTest {

    public static final String AUTHOR_NAME = "Ушинский К.Д.";
    public static final long AUTHOR_ID = 1L;
    public static final String GENRE_NAME = "Рассказ";
    public static final long GENRE_ID = 2L;
    public static final String BOOK_TITLE = "Слепая лошадь";
    public static final long BOOK_ID = 1;
    public static final String NEW_BOOK_TITLE = "Ласточка";
    public static final long NEW_BOOK_ID = 5;
    public static final int BOOKS_NUMBER = 4;
    public static final String COMMENT_TEXT = "Интересная книга!";

    @Autowired
    private BookRepositoryJpaImpl repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен находить книгу по id")
    @Test
    void getById() {
        Set<Author> authors = new HashSet<>();
        Set<Genre> genres =  new HashSet<>();
        Set<Comment> comments = new HashSet<>();

        authors.add(new Author(AUTHOR_ID,AUTHOR_NAME));
        genres.add(new Genre(GENRE_ID,GENRE_NAME));

        Book expectedBook = new Book(BOOK_ID, BOOK_TITLE, authors, genres, comments);
        Optional<Book> actualBook = repositoryJpa.getById(BOOK_ID);

        assertThat(actualBook.isPresent()).isEqualTo(true);
        assertThat(actualBook.get()).matches(b -> b.getId() == expectedBook.getId())
                .matches(b -> b.getTitle().equals(expectedBook.getTitle()))
                .matches(b -> b.getAuthors().size() == expectedBook.getAuthors().size())
                .matches(b -> b.getAuthors().containsAll(expectedBook.getAuthors()))
                .matches(b -> b.getGenres().size() == expectedBook.getGenres().size())
                .matches(b -> b.getGenres().containsAll(expectedBook.getGenres()))
                .matches(book -> book.getComments().size()==0);
    }

    @DisplayName("должен находить книгу по названию")
    @Test
    void getByTitle() {

        Set<Author> authors = new HashSet<>();
        Set<Genre> genres =  new HashSet<>();
        Set<Comment> comments = new HashSet<>();

        authors.add(new Author(AUTHOR_ID,AUTHOR_NAME));
        genres.add(new Genre(GENRE_ID,GENRE_NAME));

        Book expectedBook = new Book(BOOK_ID, BOOK_TITLE, authors, genres, comments);
        Optional<Book> actualBook = repositoryJpa.getByTitle(BOOK_TITLE);

        assertThat(actualBook.isPresent()).isEqualTo(true);
        assertThat(actualBook.get()).matches(b -> b.getId() == expectedBook.getId())
                .matches(b -> b.getTitle().equals(expectedBook.getTitle()))
                .matches(b -> b.getAuthors().size() == expectedBook.getAuthors().size())
                .matches(b -> b.getAuthors().containsAll(expectedBook.getAuthors()))
                .matches(b -> b.getGenres().size() == expectedBook.getGenres().size())
                .matches(b -> b.getGenres().containsAll(expectedBook.getGenres()))
                .matches(book -> book.getComments().size()==0);

    }

    @DisplayName("должен находить все книги")
    @Test
    void getAll() {
        val books = repositoryJpa.getAll();
        assertThat(books).isNotNull().hasSize(BOOKS_NUMBER)
                .allMatch(b -> !b.getTitle().equals(""))
                .allMatch(b -> b.getAuthors()!= null && b.getAuthors().size() > 0)
                .allMatch(b -> b.getGenres() != null && b.getGenres().size() > 0);
    }

    @DisplayName("должен удалять книгу по id")
    @Test
    void deleteById() {

        val book = em.find(Book.class, BOOK_ID);
        assertThat(book).isNotNull();
        em.detach(book);

        repositoryJpa.deleteById(BOOK_ID);
        val deletedBook = em.find(Book.class, BOOK_ID);

        assertThat(deletedBook).isNull();

    }

    @DisplayName("должен добавлять книгу")
    @Test
    void createTest() {

        val booksBeforeInsert = repositoryJpa.getAll();

        Set<Author> authors = new HashSet<>();
        Set<Genre> genres =  new HashSet<>();
        Set<Comment> comments = new HashSet<>();

        authors.add(new Author(AUTHOR_ID,AUTHOR_NAME));
        genres.add(new Genre(GENRE_ID,GENRE_NAME));

        Book expectedBook = new Book(NEW_BOOK_ID, NEW_BOOK_TITLE, authors, genres, comments);
        Optional<Book> findBook = repositoryJpa.getById(NEW_BOOK_ID);
        Book actualBook = repositoryJpa.save(expectedBook);

        val booksAfterInsert = repositoryJpa.getAll();

        assertThat(findBook.isPresent()).isEqualTo(false);
        assertThat(booksBeforeInsert).isNotNull().hasSize(BOOKS_NUMBER);
        assertThat(booksAfterInsert.size()).isGreaterThan(BOOKS_NUMBER);
    }

    @DisplayName("должен обновлять книгу")
    @Test
    void updateTest() {

        val booksBeforeUpdate = repositoryJpa.getAll();

        Set<Author> authors = new HashSet<>();
        Set<Genre> genres =  new HashSet<>();

        authors.add(new Author(AUTHOR_ID,AUTHOR_NAME));
        genres.add(new Genre(GENRE_ID,GENRE_NAME));

        Book expectedBook = new Book(BOOK_ID, BOOK_TITLE, authors, genres, null);
        Optional<Book> findBook = repositoryJpa.getById(BOOK_ID);
        Book actualBook = repositoryJpa.save(expectedBook);

        val booksAfterUpdate = repositoryJpa.getAll();

        assertThat(findBook.isPresent()).isEqualTo(true);
        assertThat(booksBeforeUpdate).isNotNull().hasSize(BOOKS_NUMBER);
        assertThat(booksAfterUpdate.size()).isEqualTo(BOOKS_NUMBER);
    }

    @DisplayName("должен находить все комментарии по книге")
    @Test
    void getAllByBook() {
        /*Set<Author> authors = new HashSet<>();
        Set<Genre> genres =  new HashSet<>();
        Set<Comment> comments = new HashSet<>();

        authors.add(new Author(AUTHOR_ID,AUTHOR_NAME));
        genres.add(new Genre(GENRE_ID,GENRE_NAME));
        comments.add(new Comment(0, BOOK_ID, COMMENT_TEXT));

        Book expectedBook = new Book(BOOK_ID, BOOK_TITLE, authors, genres, comments);
        Book actualBook = repositoryJpa.save(expectedBook);

        assertThat(actualBook.getComments().size()).isGreaterThan(0);*/
    }
}