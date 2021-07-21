package ru.diasoft.library.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.diasoft.library.models.Author;
import ru.diasoft.library.models.Book;
import ru.diasoft.library.models.Comment;
import ru.diasoft.library.models.Genre;
import ru.diasoft.library.service.BookService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий на основе Jpa для работы c комментариями")
@DataJpaTest
@Import(CommentRepositoryJpaImpl.class)
class CommentRepositoryJpaImplTest {
    public static final int BOOK_ID = 4;
    public static final String COMMENT_TEXT = "Интересная книга!";

    @Autowired
    private CommentRepositoryJpaImpl repositoryJpa;

    @DisplayName("должен находить все комментарии ")
    @Test
    void getAll() {

        List<Comment> commentList = repositoryJpa.getAll();

        assertThat(commentList.size()).isGreaterThan(0);
    }

    @DisplayName("должен добавлять комментарий")
    @Test
    void save() {

        val commentsBeforeSave = repositoryJpa.getAll().size();

        Comment comment = new Comment(COMMENT_TEXT);
        repositoryJpa.save(comment);

        val commentsAfterSave = repositoryJpa.getAll().size();

        assertThat(commentsAfterSave).isGreaterThan(commentsBeforeSave);
    }


}