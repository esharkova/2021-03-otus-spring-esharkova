package ru.diasoft.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.diasoft.library.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao для работы с авторами должно")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDao authorDao;

    @Test
    @DisplayName("добавлять автора")
    void createTest() {
        int countBefore = authorDao.getAll().size();
        Author expectedAuthor = new Author(4, "TestAuthor");
        authorDao.create(expectedAuthor.getName());
        int countAfter = authorDao.getAll().size();
        Author actualAuthor = authorDao.getById(expectedAuthor.getId()).orElse(null);
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
        assertThat(countAfter).isGreaterThan(countBefore);
    }

    @Test
    @DisplayName("возвращать автора по идентификатору")
    void getById() {
        Author expectedAuthor = new Author(3, "Фет А.А.");
        Author actualAuthor = authorDao.getById(expectedAuthor.getId()).orElse(null);
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("возвращать автора по имени")
    void getByName() {
        Author expectedAuthor = new Author(3, "Фет А.А.");
        Author actualAuthor = authorDao.getByName(expectedAuthor.getName()).orElse(null);
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("возвращать список авторов")
    void getAll() {

        List<Author> actualAuthorList = authorDao.getAll();
        assertThat(actualAuthorList.size()).isGreaterThan(0);

    }

    @Test
    @DisplayName("удалять автора по идентификатору")
    void deleteById() {
        assertThatCode(() -> authorDao.getById(3))
                .doesNotThrowAnyException();

        int countBefore = authorDao.getAll().size();

        authorDao.deleteById(3);

        int countAfter = authorDao.getAll().size();

        assertThat(countAfter).isLessThan(countBefore);

    }
}