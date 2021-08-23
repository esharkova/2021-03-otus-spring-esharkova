package ru.diasoft.library.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.diasoft.library.models.Author;
import ru.diasoft.library.models.Book;
import ru.diasoft.library.models.Comment;
import ru.diasoft.library.models.Genre;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private long id;
    private String title;
    private Set<Author> authors;
    private Set<Genre> genres;
    private Set<Comment> comments;

    public static Book toDomainObject(BookDto dto){
        return new Book(dto.getId(), dto.getTitle(), dto.getAuthors(), dto.getGenres(), dto.getComments());
    }

    public static BookDto toDto(Book book){
        return new BookDto(book.getId(), book.getTitle(), book.getAuthors(), book.getGenres(), book.getComments());
    }

}
