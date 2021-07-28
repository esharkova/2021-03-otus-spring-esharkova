package ru.diasoft.library.repositories;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.diasoft.library.models.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий на основе Jpa для работы авторами")
@DataJpaTest
@Import(AuthorRepositoryJpaImpl.class)
class AuthorRepositoryJpaImplTest {

    public static final String AUTHOR_NAME = "Ушинский К.Д.";
    public static final long AUTHOR_ID = 1L;
    public static final int AUTHORS_NUMBER = 3;
    public static final String NEW_AUTHOR_NAME = "Крылов";
    public static final long NEW_AUTHOR_ID = 0L;


    @Autowired
    private AuthorRepositoryJpaImpl repositoryJpa;

    @Autowired
    private TestEntityManager em;


    @DisplayName("должен возвращать всех авторов")
    @Test
    void getAllTest() {

        val authors = repositoryJpa.getAll();
        assertThat(authors).isNotNull().hasSize(AUTHORS_NUMBER)
                .allMatch(s -> !s.getName().equals(""));

    }

    @DisplayName("должен добавлять автора")
    @Test
    void createTest() {

        val authorsBeforeInsert = repositoryJpa.getAll();

        Author newAuthor = new Author(NEW_AUTHOR_ID,NEW_AUTHOR_NAME);
        Optional<Author> authorActual = repositoryJpa.getByName(NEW_AUTHOR_NAME);
        assertThat(authorActual).isEmpty();

        Author savedAuthor = repositoryJpa.save(newAuthor);
        val authorsAfterInsert = repositoryJpa.getAll();

        assertThat(authorsBeforeInsert).isNotNull().hasSize(AUTHORS_NUMBER);
        assertThat(savedAuthor).isNotNull().isEqualTo(newAuthor);
        assertThat(authorsAfterInsert.size()).isGreaterThan(authorsBeforeInsert.size());

    }

    @DisplayName("должен обновлять автора")
    @Test
    void updateTest() {

        val authorsBeforeInsert = repositoryJpa.getAll();

        Author existsAuthor = new Author(AUTHOR_ID,AUTHOR_NAME);
        Optional<Author> authorActual = repositoryJpa.getByName(AUTHOR_NAME);

        assertThat(authorActual).isPresent().get().isEqualTo(existsAuthor);

        Author savedAuthor = repositoryJpa.save(existsAuthor);
        val authorsAfterInsert = repositoryJpa.getAll();

        assertThat(authorsBeforeInsert).isNotNull().hasSize(AUTHORS_NUMBER);
        assertThat(savedAuthor).isNotNull().isEqualTo(existsAuthor);
        assertThat(authorsAfterInsert.size()).isEqualTo(authorsBeforeInsert.size());

    }


    @DisplayName("должен находить автора по имени")
    @Test
    void getByNameTest() {

        Author authorExpected = new Author(AUTHOR_ID,AUTHOR_NAME);
        Optional<Author> authorActual = repositoryJpa.getByName(AUTHOR_NAME);

        assertThat(authorActual).isPresent().get().isEqualTo(authorExpected);
    }
}