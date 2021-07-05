package ru.diasoft.library.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.diasoft.library.domain.Author;
import ru.diasoft.library.domain.Book;
import ru.diasoft.library.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Book create(String title, Author author, Genre genre) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", title);
        params.addValue("author_id", author.getId());
        params.addValue("genre_id", genre.getId());

        KeyHolder kh = new GeneratedKeyHolder();
        namedParameterJdbcOperations.update("insert into book (title, author_id, genre_id) values (:title, :author_id, :genre_id)", params, kh);
        return new Book(kh.getKey().longValue(), title, author, genre);
    }

    @Override
    public void update(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());

        namedParameterJdbcOperations.update("update book " +
                        "set title = :title, " +
                        "author_id = :author_id, " +
                        "genre_id = :genre_id " +
                        "where id = :id", params);
    }


    @Override
    public Optional<Book> getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.query(
                "select b.id, b.title, b.author_id, a.name as author_name, b.genre_id, g.name as genre_name from book b"+
                        " inner join author a on a.id = b.author_id" +
                        " inner join genre g on g.id = b.genre_id where b.id = :id", params, new BookDaoJdbc.BookMapper()).stream().findFirst();
    }

    @Override
    public Optional<Book> getByTitle(String title) {
        Map<String, Object> params = Collections.singletonMap("title", title);
        return namedParameterJdbcOperations.query(
                "select b.id, b.title, b.author_id, a.name as author_name, b.genre_id, g.name as genre_name from book b"+
                        " inner join author a on a.id = b.author_id" +
                        " inner join genre g on g.id = b.genre_id" +
                        " where title = :title", params, new BookDaoJdbc.BookMapper()
        ).stream().findFirst();
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.query("select b.id, b.title, b.author_id, a.name as author_name, b.genre_id, g.name as genre_name from book b"+
                " inner join author a on a.id = b.author_id" +
                " inner join genre g on g.id = b.genre_id", new BookDaoJdbc.BookMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from book where id = :id", params
        );

    }


    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            Author author = new Author(resultSet.getLong("author_id"), resultSet.getString("author_name"));
            Genre genre = new Genre(resultSet.getLong("genre_id"), resultSet.getString("genre_name"));
            return new Book(id, title, author, genre);
        }
    }

}
