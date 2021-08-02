package ru.diasoft.library.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.diasoft.library.models.Author;
import ru.diasoft.library.models.Book;
import ru.diasoft.library.models.Genre;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryJpa extends JpaRepository<Book, Long> {

    @EntityGraph(attributePaths = {"authors", "genres"})
    Optional<Book> findByTitle(String title);

    @EntityGraph(attributePaths = {"authors", "genres"})
    List<Book> findAll();

    List<Book> findByAuthors(Author author);

    List<Book> findByGenres(Genre genre);

}
