package ru.diasoft.library.service;

import ru.diasoft.library.models.Book;
import ru.diasoft.library.models.Comment;

import java.util.List;

public interface CommentService {


    Comment addComment(String commentText);

    List<Comment> getAll();
}
