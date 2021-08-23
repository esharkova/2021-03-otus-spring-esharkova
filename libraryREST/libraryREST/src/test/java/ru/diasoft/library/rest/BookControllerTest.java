package ru.diasoft.library.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.diasoft.library.models.Author;
import ru.diasoft.library.models.Book;
import ru.diasoft.library.models.Comment;
import ru.diasoft.library.models.Genre;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BookControllerTest {

    public static final long AUTHOR_ID1 = 1L;
    public static final String AUTHOR_NAME1 = "Ушинский К.Д.";
    public static final long GENRE_ID1 = 2L;
    public static final String GENRE_NAME1 = "Рассказ";
    public static final long BOOK_ID1 = 1L;
    public static final String TITLE1 = "Слепая лошадь";
    public static final String BOOK_COMMENT1 = "Интересная книга!";


    public static final long BOOK_ID2 = 2L;
    public static final String TITLE2 = "Верба";
    public static final long GENRE_ID2 = 3L;
    public static final String GENRE_NAME2 = "Поэзия";
    public static final long AUTHOR_ID2 = 3L;
    public static final String AUTHOR_NAME2 = "Фет А.А.";
    public static final String BOOK_COMMENT2 = "Очень понравилось!";

    public static final long BOOK_ID3 = 4L;
    public static final String TITLE3 = "Бишка";
    public static final String BOOK_COMMENT3 = "Смешная книжка!";

    public static final long BOOK_ID4 = 3L;
    public static final String TITLE4 = "Ласточка";

    public static final String NEW_TITLE3 = "Бишка и друзья";

    public static final String COMMENT_TEXT = "COMMENT_TEXT";
    public static final String ERROR_STRING = "Книга не найдена!";



    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    Author author1, author2;
    Set<Author> authors1 = new HashSet<>();
    Set<Author> authors2 = new HashSet<>();

    Genre genre1, genre2;
    Set<Genre> genres1 = new HashSet<>();
    Set<Genre> genres2 = new HashSet<>();

    Book book1, book2;

    List<Book> books = new ArrayList<>();

    Set<Comment> comments1 = new HashSet<>();
    Set<Comment> comments2 = new HashSet<>();
    Set<Comment> comments3 = new HashSet<>();
    Set<Comment> comments4 = new HashSet<>();


    @BeforeEach
    void setUp() {
        author1 = Author.builder().id(AUTHOR_ID1).name(AUTHOR_NAME1).build();
        author2 = Author.builder().id(AUTHOR_ID2).name(AUTHOR_NAME2).build();

        genre1 = Genre.builder().id(GENRE_ID1).name(GENRE_NAME1).build();
        genre2 = Genre.builder().id(GENRE_ID2).name(GENRE_NAME2).build();

        comments1.add(new Comment(1,BOOK_COMMENT1));
        comments2.add(new Comment(2,BOOK_COMMENT2));
        comments3.add(new Comment(3,BOOK_COMMENT3));

        comments4.add(new Comment(5,"Книга понравилась"));

        authors1.add(author1);
        authors2.add(author2);

        genres1.add(genre1);
        genres2.add(genre2);

        book1 = Book.builder()
                .id(BOOK_ID1)
                .title(TITLE1)
                .authors(authors1)
                .genres(genres1)
                .comments(comments1)
                .build();
        book2 = Book.builder()
                .id(BOOK_ID2)
                .title(TITLE2)
                .authors(authors2)
                .genres(genres2)
                .comments(comments2)
                .build();

        books.add(book1);
        books.add(book2);
    }


    @Test
    void getAllBooksTest() throws Exception {

        List<BookDto> expectedResult = books.stream()
                .map(BookDto::toDto).collect(Collectors.toList());

        mvc.perform(get("/books/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    void getBookByIdInInRequestTest() throws Exception {

        BookDto expectedBook = BookDto.toDto(book1);

        mvc.perform(get("/books").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedBook)));
    }

    @Test
    void getBookByIdInPath() throws Exception {

        BookDto expectedBook = BookDto.toDto(book1);

        mvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedBook)));

    }

    @Test
    void shouldCorrectSaveAndUpdateNewBook() throws Exception {
        Book book = new Book(BOOK_ID3, TITLE3, authors1, genres1, comments4);
        String expectedResult = mapper.writeValueAsString(BookDto.toDto(book));

        mvc.perform(post("/books").contentType(APPLICATION_JSON)
                .content(expectedResult))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));

        Book expectedBookAfterUpdate = book;
        expectedBookAfterUpdate.setTitle(NEW_TITLE3);
        String expectedResultAfterUpdate = mapper.writeValueAsString(BookDto.toDto(expectedBookAfterUpdate));

        mvc.perform(patch("/books/{id}/title", 4).param("title", NEW_TITLE3)
                .content(expectedResultAfterUpdate))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResultAfterUpdate));

    }

    @Test
    void getBookCommentsByIdInPath() throws Exception {

        BookDto expectedBook = BookDto.toDto(book2);

        Set<Comment> comments = book2.getComments();

        mvc.perform(get("/books/2/comments"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedBook.getComments())));

        assertThat(comments.size()).isGreaterThan(0);
    }

    @Test
    void addCommentByIdTest() throws Exception {

        Book book = new Book(BOOK_ID4, TITLE4, authors2, genres2, comments3);

        String expectedResult = mapper.writeValueAsString(BookDto.toDto(book));

        mvc.perform(post("/books").contentType(APPLICATION_JSON)
                .content(expectedResult))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));

        Comment newComment = new Comment(4,COMMENT_TEXT);
        book.getComments().add(newComment);

        String expectedResultAfterUpdate = mapper.writeValueAsString(BookDto.toDto(book));

        mvc.perform(patch("/books/{id}/comments", 3).param("comment", COMMENT_TEXT)
                .content(expectedResultAfterUpdate))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResultAfterUpdate));
    }

    @Test
    void shouldReturnExpectedErrorWhenBookNotFound() throws Exception {

        mvc.perform(get("/books").param("id", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ERROR_STRING));

        mvc.perform(get("/books/10"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ERROR_STRING));
    }

    @Test
    void shouldCorrectDeleteBook() throws Exception {
        mvc.perform(delete("/books/3"))
                .andExpect(status().isOk());
    }
}