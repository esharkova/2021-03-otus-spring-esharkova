package ru.diasoft.library.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.diasoft.library.models.Author;
import ru.diasoft.library.models.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Репозиторий на основе Jpa для работы c жанрами")
@DataJpaTest
@Import(GenreRepositoryJpaImpl.class)
class GenreRepositoryJpaImplTest {

    public static final String GENRE_NAME = "Сказка";
    public static final long GENRE_ID = 1L;
    public static final int GENRES_NUMBER = 3;
    public static final String NEW_GENRE_NAME = "Басня";
    public static final long NEW_GENRE_ID = 0L;

    @Autowired
    private GenreRepositoryJpaImpl repositoryJpa;

    @DisplayName("должен возвращать все жанры")
    @Test
    void getAllTest() {
        val genres = repositoryJpa.getAll();
        assertThat(genres).isNotNull().hasSize(GENRES_NUMBER)
                .allMatch(s -> !s.getName().equals(""));
    }

    @DisplayName("должен добавлть жанр")
    @Test
    void createTest() {
        val genresBeforeInsert = repositoryJpa.getAll();

        Genre newGenre = new Genre(NEW_GENRE_ID, NEW_GENRE_NAME);
        Optional<Genre> genreActual = repositoryJpa.getByName(NEW_GENRE_NAME);
        assertThat(genreActual).isEmpty();

        Genre savedGenre = repositoryJpa.save(newGenre);
        val genresAfterInsert = repositoryJpa.getAll();

        assertThat(genresBeforeInsert).isNotNull().hasSize(GENRES_NUMBER);
        assertThat(savedGenre).isNotNull().isEqualTo(newGenre);
        assertThat(genresAfterInsert.size()).isGreaterThan(genresBeforeInsert.size());
    }

    @DisplayName("должен обновлять автора")
    @Test
    void updateTest() {

        val genresBeforeInsert = repositoryJpa.getAll();

        Genre existsGenre = new Genre(GENRE_ID,GENRE_NAME);
        Optional<Genre> GenreActual = repositoryJpa.getByName(GENRE_NAME);

        assertThat(GenreActual).isPresent().get().isEqualTo(existsGenre);

        Genre savedGenre = repositoryJpa.save(existsGenre);
        val genresAfterInsert = repositoryJpa.getAll();

        assertThat(genresBeforeInsert).isNotNull().hasSize(GENRES_NUMBER);
        assertThat(savedGenre).isNotNull().isEqualTo(existsGenre);
        assertThat(genresAfterInsert.size()).isEqualTo(genresBeforeInsert.size());

    }


    @DisplayName("должен находить жанр по называнию")
    @Test
    void getByNameTest() {

        Genre genreExpected = new Genre(GENRE_ID,GENRE_NAME);
        Optional<Genre> genreActual = repositoryJpa.getByName(GENRE_NAME);

        assertThat(genreActual).isPresent().get().isEqualTo(genreExpected);
    }
}