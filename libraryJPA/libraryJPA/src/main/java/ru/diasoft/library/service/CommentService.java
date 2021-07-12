package ru.diasoft.library.service;

import ru.diasoft.library.models.Book;
import ru.diasoft.library.models.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAllByBook(Book book);

    Comment addComment(String commentText);

    void deleteByBook(Book book);

    List<Comment> getAll();
}
