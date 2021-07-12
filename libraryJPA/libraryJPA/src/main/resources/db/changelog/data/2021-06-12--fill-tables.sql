--liquibase formatted sql

--changeset esharkova:2021-06-12-fill-author
insert into author (id, name)
values (1, 'Ушинский К.Д.'),
       (2, 'Пушкин А.С.'),
       (3, 'Фет А.А.')

--changeset esharkova:2021-06-12-fill-author
insert into genre (id, name)
values (1, 'Сказка'),
       (2, 'Рассказ'),
       (3, 'Поэзия')

--changeset esharkova:2021-06-12-fill-author
insert into book (id, title, author_id, genre_id)

values (1, 'Слепая лошадь', 1, 2),
       (2, 'Верба', 3, 3),
       (3, 'Сказка о царе салтане', 2, 1),
       (4, 'Лукоморье', 2, 3)

