package ru.diasoft.library.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.library.models.Author;
import ru.diasoft.library.repositories.AuthorRepositoryJpa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис AuthorServiceImpl должен")
class AuthorServiceImplTest {

    public static final long ID = 1L;
    public static final String NAME = "NAME";
    @Mock
    private AuthorRepositoryJpa repository;

    @InjectMocks
    private AuthorServiceImpl service;

    Author author;
    List<Author> authorList = new ArrayList<>();

    @BeforeEach
    void setUp() {

        author = Author.builder().id(ID).name(NAME).build();
        authorList.add(author);
    }

    @Test
    @DisplayName("возвращать всех авторов")
    void getAllTest() {
        when(repository.getAll()).thenReturn(authorList);

        assertThat(service.getAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("добавлять автора")
    void createTest() {

        when(repository.save(any())).thenReturn(author);
        Author createdAuthor =  service.create(NAME);
        assertEquals(createdAuthor, author);
        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("искать автора по имени")
    void getByNameTest() {

        when(repository.getByName(NAME))
                .thenReturn(java.util.Optional.ofNullable(author));
        Optional<Author> findAuthor = service.getByName(NAME);
        assertNotNull(findAuthor);
        assertEquals(findAuthor.get(),author);
        verify(repository, times(1)).getByName(NAME);
    }
}