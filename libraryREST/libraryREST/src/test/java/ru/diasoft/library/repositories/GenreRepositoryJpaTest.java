package ru.diasoft.library.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.diasoft.library.models.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий для работы с авторами должен")
@DataJpaTest
class GenreRepositoryJpaTest {

    public static final String GENRE = "GENRE";
    @Autowired
    private GenreRepositoryJpa repository;

    Genre createdGenre, genre;

    @BeforeEach
    void setUp() {
        genre = Genre.builder().name(GENRE).build();
    }

    @DisplayName("сохранять жанр")
    @Test
    void createGenreTest(){
        int genreCountBeforeSave = repository.findAll().size();
        createdGenre = repository.save(genre);
        int genreCountAfterSave = repository.findAll().size();

        assertThat(createdGenre).isEqualTo(genre);
        assertThat(genreCountAfterSave).isGreaterThan(genreCountBeforeSave);
    }


    @Test
    @DisplayName("возвращать жанр по названию")
    void findByName() {

        createdGenre = repository.save(genre);
        Optional<Genre> findGenre = repository.findByName(GENRE);

        assertNotNull(findGenre);
        assertThat(findGenre.get().getName()).isEqualTo(genre.getName());
        assertThat(findGenre.get().getId()).isEqualTo(genre.getId());
    }

    @DisplayName("удалять жанр")
    @Test
    void deleteGenreTest(){

        createdGenre = repository.save(genre);
        int genreCountBeforeDelete = repository.findAll().size();
        repository.delete(createdGenre);
        int genreCountAfterDelete = repository.findAll().size();

        assertThat(genreCountAfterDelete).isLessThan(genreCountBeforeDelete);
    }
}