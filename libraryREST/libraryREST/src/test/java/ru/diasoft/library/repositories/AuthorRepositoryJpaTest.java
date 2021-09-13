package ru.diasoft.library.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.diasoft.library.models.Author;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий для работы с авторами должен")
@DataJpaTest
class AuthorRepositoryJpaTest {

    public static final String AUTHOR = "AUTHOR";
    @Autowired
    private AuthorRepositoryJpa repository;

    Author createdAuthor, author;

    @BeforeEach
    void setUp() {
        author = Author.builder().name(AUTHOR).build();
    }

    @DisplayName("сохранять автора")
    @Test
    void createAuthorTest(){
        int authorCountBeforeSave = repository.findAll().size();
        createdAuthor = repository.save(author);
        int authorCountAfterSave = repository.findAll().size();

        assertThat(createdAuthor).isEqualTo(author);
        assertThat(authorCountAfterSave).isGreaterThan(authorCountBeforeSave);
    }


    @Test
    @DisplayName("возвращать автора по имени")
    void findByName() {

        createdAuthor = repository.save(author);
        Optional<Author> findAuthor = repository.findByName(AUTHOR);

        assertNotNull(findAuthor);
        assertThat(findAuthor.get().getName()).isEqualTo(author.getName());
        assertThat(findAuthor.get().getId()).isEqualTo(author.getId());
    }

    @DisplayName("удалять автора")
    @Test
    void deleteAuthorTest(){

        createdAuthor = repository.save(author);
        int authorCountBeforeDelete = repository.findAll().size();
        repository.delete(createdAuthor);
        int authorCountAfterDelete = repository.findAll().size();

        assertThat(authorCountAfterDelete).isLessThan(authorCountBeforeDelete);
    }
}