package ru.diasoft.library.service;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.library.models.Author;
import ru.diasoft.library.models.Book;
import ru.diasoft.library.models.Genre;
import ru.diasoft.library.repositories.AuthorRepositoryJpa;
import ru.diasoft.library.repositories.BookRepositoryJpa;
import ru.diasoft.library.repositories.GenreRepositoryJpa;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис BookServiceImpl должен")
class BookServiceImplTest {
    public static final long AUTHOR_ID = 1L;
    public static final String AUTHOR_NAME = "NAME";
    public static final long GENRE_ID = 1L;
    public static final String GENRE_NAME = "NAME";
    public static final long BOOK_ID = 1L;
    public static final String TITLE = "TITLE";

    @Mock
    private AuthorRepositoryJpa authorRepositoryJpa;

    @Mock
    private GenreRepositoryJpa genreRepositoryJpa;

    @Mock
    private BookRepositoryJpa bookRepositoryJpa;

    @InjectMocks
    private BookServiceImpl service;

    Author author;
    Set<Author> authorList = new HashSet<>();
    Genre genre;
    Set<Genre> genreList = new HashSet<>();
    Book book;
    List<Book> books = new ArrayList<>();
    List<String> authors = new ArrayList<>();
    List<String> genres = new ArrayList<>();

    @BeforeEach
    void setUp() {
        author = Author.builder().id(AUTHOR_ID).name(AUTHOR_NAME).build();

        genre = Genre.builder().id(GENRE_ID).name(GENRE_NAME).build();

        authors.add(AUTHOR_NAME);
        genres.add(GENRE_NAME);

        book = Book.builder()
                .id(BOOK_ID)
                .title(TITLE)
                .authors(authorList)
                .genres(genreList)
                .comments(null)
                .build();
        books.add(book);
    }

    @Test
    @DisplayName("добавлять книгу")
    void createTest() {
        when(authorRepositoryJpa.findByName(AUTHOR_NAME)).thenReturn(java.util.Optional.ofNullable(author));
        when(genreRepositoryJpa.findByName(GENRE_NAME)).thenReturn(java.util.Optional.ofNullable(genre));
        when(bookRepositoryJpa.save(any())).thenReturn(book);

        authorList.add(author);
        genreList.add(genre);

        Book createdBook = service.create(TITLE,authors,genres);

        assertEquals(createdBook.getTitle(), book.getTitle());
        assertEquals(createdBook.getAuthors(), authorList);
        assertThat(createdBook.getAuthors().size()).isEqualTo(1);
        assertEquals(createdBook.getGenres(), genreList);
        assertThat(createdBook.getGenres().size()).isEqualTo(1);
        assertNull(createdBook.getComments());

        verify(bookRepositoryJpa, times(1)).save(any());
        verify(authorRepositoryJpa, times(1)).findByName(AUTHOR_NAME);
        verify(genreRepositoryJpa, times(1)).findByName(GENRE_NAME);


    }

    @Test
    @DisplayName("обновлять книгу")
    void save() {
        when(bookRepositoryJpa.save(any())).thenReturn(book);

        Book savedBook = service.save(book);

        assertEquals(savedBook,book);

        verify(bookRepositoryJpa, times(1)).save(any());
    }

    @Test
    @DisplayName("искать книгу по идентификатору")
    void getById() {
        when(bookRepositoryJpa.findById(BOOK_ID)).thenReturn(java.util.Optional.ofNullable(book));

        Book findBook = service.getById(BOOK_ID);

        assertEquals(findBook,book);

        verify(bookRepositoryJpa, times(1)).findById(BOOK_ID);
    }

    @Test
    @DisplayName("искать книгу по названию")
    void getByTitle() {
        when(bookRepositoryJpa.findByTitle(TITLE)).thenReturn(java.util.Optional.ofNullable(book));

        Optional<Book> findBook = service.getByTitle(TITLE);

        assertEquals(findBook.get(),book);

        verify(bookRepositoryJpa, times(1)).findByTitle(TITLE);

    }

    @Test
    @DisplayName("возвращать все книги")
    void getAll() {
        when(bookRepositoryJpa.findAll()).thenReturn(books);

        List<Book> bookList = service.getAll();

        assertEquals(bookList,books);

        verify(bookRepositoryJpa, times(1)).findAll();
    }

    @Test
    @DisplayName("удалять книгу по идентификатору")
    void deleteById() {

        when(bookRepositoryJpa.save(any())).thenReturn(book);

        Book savedBook = service.save(book);

        Optional<Book> foundBook1 = service.getByTitle(savedBook.getTitle());

        service.deleteById(savedBook.getId());
        Optional<Book> foundBook = service.getByTitle(savedBook.getTitle());

        assertNotNull(savedBook);
        assertNotNull(foundBook1);
        assertThat(foundBook).isEmpty();

        verify(bookRepositoryJpa, times(1)).deleteById(savedBook.getId());

    }
}