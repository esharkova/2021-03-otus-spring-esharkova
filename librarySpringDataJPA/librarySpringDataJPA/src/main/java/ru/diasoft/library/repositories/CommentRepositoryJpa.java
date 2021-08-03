package ru.diasoft.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diasoft.library.models.Comment;

public interface CommentRepositoryJpa extends JpaRepository<Comment, Long> {

}
