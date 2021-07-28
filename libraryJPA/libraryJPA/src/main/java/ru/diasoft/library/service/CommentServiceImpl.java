package ru.diasoft.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.library.models.Book;
import ru.diasoft.library.models.Comment;
import ru.diasoft.library.repositories.CommentRepositoryJpa;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepositoryJpa commentRepository;

    @Override
    @Transactional
    public Comment addComment(String commentText) {

        Comment newComment = new Comment(commentText);
        return commentRepository.save(newComment);

    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAll() {
        return commentRepository.getAll();
    }
}
