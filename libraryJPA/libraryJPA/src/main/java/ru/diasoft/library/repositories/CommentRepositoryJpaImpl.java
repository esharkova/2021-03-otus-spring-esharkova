package ru.diasoft.library.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.library.models.Author;
import ru.diasoft.library.models.Book;
import ru.diasoft.library.models.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CommentRepositoryJpaImpl implements CommentRepositoryJpa {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Comment> getAll() {

        TypedQuery<Comment> query = em.createQuery("select c " +
                        "from Comment c" ,
                Comment.class);

        return query.getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() <= 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public void deleteByBook(Book book) {
        Query query = em.createQuery("delete from Comment c where c.book = :book");
        query.setParameter("book", book);
        query.executeUpdate();
    }
}
