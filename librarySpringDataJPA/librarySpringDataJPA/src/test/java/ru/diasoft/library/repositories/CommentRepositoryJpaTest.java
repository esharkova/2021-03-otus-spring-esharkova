package ru.diasoft.library.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.diasoft.library.models.Comment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий для работы с комменарием должен")
@DataJpaTest
class CommentRepositoryJpaTest {

    public static final String TEXT = "commentText";

    @Autowired
    private CommentRepositoryJpa repository;

    Comment comment, createdComment;


    @BeforeEach
    void setUp() {
        comment = Comment.builder().commentText(TEXT).build();
    }

    @DisplayName("добавлять комментарий")
    @Test
    void createAuthorTest(){
        int commentCountBeforeSave = repository.findAll().size();
        createdComment = repository.save(comment);
        int authorCountAfterSave = repository.findAll().size();

        assertThat(createdComment).isEqualTo(comment);
        assertThat(authorCountAfterSave).isGreaterThan(commentCountBeforeSave);
    }

    @DisplayName("удалять комментарий")
    @Test
    void deleteGenreTest(){

        createdComment = repository.save(comment);
        int genreCountBeforeDelete = repository.findAll().size();
        repository.delete(createdComment);
        int genreCountAfterDelete = repository.findAll().size();

        assertThat(genreCountAfterDelete).isLessThan(genreCountBeforeDelete);
    }


}