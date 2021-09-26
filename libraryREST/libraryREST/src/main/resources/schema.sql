DROP TABLE IF EXISTS author;
CREATE TABLE author(id BIGINT IDENTITY PRIMARY KEY, name VARCHAR(255));
DROP TABLE IF EXISTS genre;
CREATE TABLE genre(id BIGINT IDENTITY PRIMARY KEY, name VARCHAR(255));
DROP TABLE IF EXISTS book;
CREATE TABLE book(id BIGINT IDENTITY PRIMARY KEY, title VARCHAR(255));

DROP TABLE IF EXISTS comment;
CREATE TABLE comment(id BIGINT IDENTITY PRIMARY KEY, book_id BIGINT references book(id) on delete cascade, comment_text VARCHAR(255));

DROP TABLE IF EXISTS book_author;
CREATE TABLE book_author(book_id BIGINT references book(id) on delete cascade, author_id BIGINT references author(id), primary key (book_id, author_id));
DROP TABLE IF EXISTS book_genre;
CREATE TABLE book_genre(book_id BIGINT references book(id) on delete cascade, genre_id BIGINT references genre(id),primary key (book_id, genre_id));

DROP TABLE IF EXISTS users;
CREATE TABLE users(id BIGINT IDENTITY PRIMARY KEY, login VARCHAR(255), password VARCHAR(255));
