package ru.diasoft.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.diasoft.library.domain.Author;
import ru.diasoft.library.domain.Book;
import ru.diasoft.library.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao для работы с книгами должно")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    @Autowired
    private BookDao bookDao;

    private Author testAuthor = new Author(4, "TestAuthor");
    private Genre testGenre = new Genre(4, "TestGenre");

    @Test
    @DisplayName("добавлять книгу")
    void createTest() {
        Book expectedBook = new Book(5, "TestBook", testAuthor, testGenre);

        Book actualBook = bookDao.create("TestBook", testAuthor, testGenre);

        assertThat(actualBook).isEqualTo(expectedBook);
        assertNotNull(actualBook);

    }

    @Test
    @DisplayName("изменять книгу по идентификатору")
    void updateTest() {
        Book oldBook = bookDao.getById(3).orElse(null);
        Book newBook = new Book(3, "TestBook", oldBook.getAuthor(), oldBook.getGenre());

        bookDao.update(newBook);

        Book afterUpdate = bookDao.getById(3).orElse(null);

        assertNotEquals(afterUpdate.getTitle(), oldBook.getTitle());
        assertEquals(afterUpdate.getAuthor(), oldBook.getAuthor());
        assertEquals(afterUpdate.getGenre(), oldBook.getGenre());
    }

    @Test
    @DisplayName("возвращать книгу по идентификатору")
    void getByIdTest() {
        Author author = new Author(3, "Фет А.А.");
        Genre genre = new Genre(3, "Поэзия");
        Book expectedBook = new Book(2, "Верба", author, genre);
        Book actualBook  = bookDao.getById(expectedBook.getId()).orElse(null);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("возвращать книгу по названию")
    void getByTitleTest() {
        Author author = new Author(3, "Фет А.А.");
        Genre genre = new Genre(3, "Поэзия");
        Book expectedBook = new Book(2, "Верба", author, genre);
        Book actualBook  = bookDao.getByTitle(expectedBook.getTitle()).orElse(null);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("возвращать список всех книг")
    void getAllTest() {
        List<Book> actualBookList = bookDao.getAll();
        assertThat(actualBookList.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("удалять книгу по идентификатору")
    void deleteByIdTest() {

        assertThatCode(() -> bookDao.getById(3))
                .doesNotThrowAnyException();

        int countBefore = bookDao.getAll().size();

        bookDao.deleteById(3);

        int countAfter = bookDao.getAll().size();

        assertThat(countAfter).isLessThan(countBefore);
    }
}