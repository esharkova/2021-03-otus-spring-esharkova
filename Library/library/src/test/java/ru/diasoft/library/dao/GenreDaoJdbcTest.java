package ru.diasoft.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.diasoft.library.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с жанрами должно")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    @Autowired
    private GenreDao genreDao;

    @Test
    @DisplayName("добавлять жанр")
    void createTest() {
        int countBefore = genreDao.getAll().size();
        Genre expectedGenre = new Genre(4, "TestGenre");
        genreDao.create(expectedGenre.getName());
        int countAfter = genreDao.getAll().size();
        Genre actualGenre = genreDao.getById(expectedGenre.getId());
        assertThat(actualGenre).isEqualTo(expectedGenre);
        assertThat(countAfter).isGreaterThan(countBefore);
    }

    @Test
    @DisplayName("возвращать жанр по идентификатору")
    void getById() {
        Genre expectedGenre = new Genre(3, "Поэзия");
        Genre actualGenre = genreDao.getById(expectedGenre.getId());
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("возвращать жанр по названию")
    void getByName() {
        Genre expectedGenre = new Genre(3, "Поэзия");
        Genre actualGenre = genreDao.getByName(expectedGenre.getName()).orElse(null);
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("возвращать список жанров")
    void getAll() {

        List<Genre> actualGenreList = genreDao.getAll();
        assertThat(actualGenreList.size()).isGreaterThan(0);

    }
}