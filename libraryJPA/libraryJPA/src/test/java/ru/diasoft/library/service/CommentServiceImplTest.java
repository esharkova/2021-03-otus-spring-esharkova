package ru.diasoft.library.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.library.models.Author;
import ru.diasoft.library.models.Comment;
import ru.diasoft.library.repositories.AuthorRepositoryJpa;
import ru.diasoft.library.repositories.CommentRepositoryJpa;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис CommentServiceImpl должен")
class CommentServiceImplTest {

    public static final long ID = 1L;
    public static final String COMMENT_TEXT = "commentText";

    @Mock
    private CommentRepositoryJpa repository;

    @InjectMocks
    private CommentServiceImpl service;

    Comment comment;
    List<Comment> commentList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        comment = Comment.builder().id(ID).commentText(COMMENT_TEXT).build();
        commentList.add(comment);
    }

    @Test
    @DisplayName("добавлять комментарий")
    void addComment() {

        when(repository.save(any())).thenReturn(comment);
        Comment createdComment =  service.addComment(COMMENT_TEXT);
        assertEquals(createdComment, comment);
        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("возращать все комментарии")
    void getAll() {
        when(repository.getAll()).thenReturn(commentList);

        assertThat(service.getAll().size()).isEqualTo(1);
    }
}