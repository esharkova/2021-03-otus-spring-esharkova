insert into author (id, name) values (1, 'Ушинский К.Д.');
insert into author (id, name) values (2, 'Пушкин А.С.');
insert into author (id, name) values (3, 'Фет А.А.');
insert into genre (id, name) values (1, 'Сказка');
insert into genre (id, name) values (2, 'Рассказ');
insert into genre (id, name) values (3, 'Поэзия');
insert into book (id, title, author_id, genre_id) values (1, 'Слепая лошадь', 1, 2);
insert into book (id, title, author_id, genre_id) values(2, 'Верба', 3, 3);
insert into book (id, title, author_id, genre_id) values(3, 'Сказка о царе салтане', 2, 1);
insert into book (id, title, author_id, genre_id) values(4, 'Лукоморье', 2, 3);

