package ru.diasoft.library.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.diasoft.library.models.Book;
import ru.diasoft.library.models.Comment;
import ru.diasoft.library.repositories.BookRepositoryJpa;
import ru.diasoft.library.service.BookService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class BookController {
    private final BookRepositoryJpa repository;
    private final BookService service;

    public BookController(BookRepositoryJpa repository, BookService service) {
        this.repository = repository;
        this.service = service;
    }

    @RequestMapping(value = "/books/all", method = RequestMethod.GET, params = {})
    public List<BookDto> getAllBooks() {
        /*return repository.findAll().stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());*/
        return service.getAll().stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/books")
    public BookDto getBookByIdInInRequest(@RequestParam("id") long id) {
        Book book = repository.findById(id).orElseThrow(NotFoundException::new);
        return BookDto.toDto(book);
    }

    @GetMapping("/books/{id}")
    public BookDto getBookByIdInPath(@PathVariable("id") long id) {
        Book book = repository.findById(id).orElseThrow(NotFoundException::new);
        return BookDto.toDto(book);
    }

    @PostMapping("/books")
    public BookDto createNewBook(@RequestBody BookDto dto) {
        Book book = BookDto.toDomainObject(dto);
        Book savedBook = repository.save(book);
        return BookDto.toDto(savedBook);
    }


    @PatchMapping("/books/{id}/title")
    public BookDto updateTitleById(@PathVariable("id") long id, @RequestParam("title") String title) {
        Book book = repository.findById(id).orElseThrow(NotFoundException::new);
        book.setTitle(title);
        return BookDto.toDto(repository.save(book));
    }

    @PatchMapping("/books/{id}/comments")
    public BookDto addCommentById(@PathVariable("id") long id, @RequestParam("comment") String comment) {
        Book book = repository.findById(id).orElseThrow(NotFoundException::new);

        book.getComments().add(new Comment(0,comment));
        return BookDto.toDto(service.save(book));
    }

    @GetMapping("/books/{id}/comments")
    public Set<Comment> getBookCommentsByIdInPath(@PathVariable("id") long id) {
        Book book = repository.findById(id).orElseThrow(NotFoundException::new);
        return BookDto.toDto(book).getComments();
    }



    @DeleteMapping("/books/{id}")
    public void deleteById(@PathVariable("id") long id) {
        repository.deleteById(id);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        return ResponseEntity.badRequest().body("Книга не найдена!");
    }

}
