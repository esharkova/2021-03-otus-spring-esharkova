package ru.diasoft.library.repositories;

import ru.diasoft.library.models.Author;
import ru.diasoft.library.models.Book;
import ru.diasoft.library.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryJpa {

    List<Comment> getAllByBook(Book book);

    List<Comment> getAll();

    Comment save(Comment comment);

    void deleteByBook(Book book);
}
