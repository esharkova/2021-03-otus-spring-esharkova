insert into author (id, name) values (1, 'Ушинский К.Д.');
insert into author (id, name) values (2, 'Пушкин А.С.');
insert into author (id, name) values (3, 'Фет А.А.');
insert into genre (id, name) values (1, 'Сказка');
insert into genre (id, name) values (2, 'Рассказ');
insert into genre (id, name) values (3, 'Поэзия');
insert into book (id, title) values (1, 'Слепая лошадь');
insert into book_author(book_id, author_id) values (1, 1);
insert into book_genre(book_id, genre_id) values (1, 2);
insert into book (id, title) values(2, 'Верба');
insert into book_author(book_id, author_id) values (2, 3);
insert into book_genre(book_id, genre_id) values (2, 3);
insert into book (id, title) values(3, 'Сказка о царе салтане');
insert into book_author(book_id, author_id) values (3, 2);
insert into book_genre(book_id, genre_id) values (3, 1);
insert into book (id, title) values(4, 'Лукоморье');
insert into book_author(book_id, author_id) values (4, 2);
insert into book_genre(book_id, genre_id) values (4, 3);
insert into comment(id, book_id, comment_text) values (1, 1, 'Очень интересная книга!');
insert into comment(id, book_id, comment_text) values (2, 1, 'Книга понравилась!');
insert into comment(id, book_id, comment_text) values (3, 2, 'Красивые стихи!');
insert into users(id, login, password) values (1, 'elena_sharkova', '123456');
insert into users(id, login, password) values (2, 'admin', 'password');



