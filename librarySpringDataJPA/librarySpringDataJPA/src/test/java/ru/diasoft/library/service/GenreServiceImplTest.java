package ru.diasoft.library.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.library.models.Author;
import ru.diasoft.library.models.Genre;
import ru.diasoft.library.repositories.AuthorRepositoryJpa;
import ru.diasoft.library.repositories.GenreRepositoryJpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис GenreServiceImpl должен")
class GenreServiceImplTest {

    public static final long ID = 1L;
    public static final String NAME = "NAME";
    @Mock
    private GenreRepositoryJpa repository;

    @InjectMocks
    private GenreServiceImpl service;

    Genre genre;
    List<Genre> genreList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        genre = Genre.builder().id(ID).name(NAME).build();
        genreList.add(genre);
    }

    @Test
    @DisplayName("возвращать все жанры")
    void getAllTest() {
        when(repository.findAll()).thenReturn(genreList);

        assertThat(service.getAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("создавать жанр")
    void createTest() {
        when(repository.save(any())).thenReturn(genre);
        Genre createdGenre =  service.create(NAME);
        assertEquals(createdGenre, genre);
        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("возвращать жанр по названию")
    void getByNameTest() {
        when(repository.findByName(NAME))
                .thenReturn(java.util.Optional.ofNullable(genre));
        Optional<Genre> findGenre = service.getByName(NAME);
        assertNotNull(findGenre);
        assertEquals(findGenre.get(),genre);
        verify(repository, times(1)).findByName(NAME);
    }
}