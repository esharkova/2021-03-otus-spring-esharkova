package ru.diasoft.library.repositories;

import org.springframework.stereotype.Repository;
import ru.diasoft.library.models.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpaImpl implements BookRepositoryJpa {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Book> getById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }


    @Override
    public Optional<Book> getByTitle(String title) {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-authors-genres-graph");
        TypedQuery<Book> query = em.createQuery("select b " +
                        "from Book b " +
                        "where b.title = :title",
                Book.class);
        query.setParameter("title", title);
        query.setHint("javax.persistence.fetchgraph", entityGraph);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getAll() {

        EntityGraph<?> entityGraph = em.getEntityGraph("book-authors-genres-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {

        Book deletedBook = em.find(Book.class, id);
        em.remove(deletedBook);

    }

    @Override
    public Book save(Book book) {
        if (book.getId() <= 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }
}
