package ru.diasoft.library.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Book {
    private final long id;
    private final String title;
    private final Author author;
    private final Genre genre;


    @Override
    public String toString() {
        return title + ' ' + author.getName() + ' ' + genre.getName();
    }
}
