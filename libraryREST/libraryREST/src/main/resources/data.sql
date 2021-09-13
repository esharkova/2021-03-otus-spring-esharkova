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


