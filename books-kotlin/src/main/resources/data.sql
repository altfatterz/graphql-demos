insert into authors(id, first_name, last_name)
    values ('cab22442-e1d7-4f1b-98b7-03fc34b2032b', 'Dan', 'Brown');
insert into authors(id, first_name, last_name)
    values ('4112a591-8def-4242-a34f-38abcbe0f7d1', 'Arthur', 'Golden');
insert into authors(id, first_name, last_name)
    values ('59189bc8-a8c2-423c-a31b-be620d7cd020', 'Paolo', 'Coelho');

insert into books(id, title, page_count, published_date, author_id)
    values ('5a2642d2-3440-48f5-9c71-ebc4a8b9ef14', 'The Da Vinci Code', 489, '2006-03-28', 'cab22442-e1d7-4f1b-98b7-03fc34b2032b');
insert into books(id, title, page_count, published_date, author_id)
    values ('c5ec9fb6-b586-449f-b869-bfe4057a4f0e', 'Memoirs of a Geisha', 434, '2005-11-15', '4112a591-8def-4242-a34f-38abcbe0f7d1');
insert into books(id, title, page_count, published_date, author_id)
    values ('b41c35c8-3b39-428d-a84b-97aebc7a1602', 'The Alchemist', 197, '1993-05-01', '59189bc8-a8c2-423c-a31b-be620d7cd020');

insert into reviews(id, stars, comment, book_id)
    values ('893054f3-3651-422f-80dc-35512e51b952', 4, 'people either passionately love this book or they passionately hate it', '5a2642d2-3440-48f5-9c71-ebc4a8b9ef14');
insert into reviews(id, stars, comment, book_id)
    values ('4d1e4d0f-f487-4247-b81a-b03e99ef9f70', 5, 'Very good storytelling', 'c5ec9fb6-b586-449f-b869-bfe4057a4f0e');