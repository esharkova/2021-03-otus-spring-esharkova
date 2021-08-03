package ru.diasoft.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diasoft.library.models.Genre;

import java.util.Optional;

public interface GenreRepositoryJpa extends JpaRepository<Genre, Long> {

    Optional<Genre> findByName(String name);

}

