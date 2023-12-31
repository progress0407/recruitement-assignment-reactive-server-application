insert into users (id, email, name, password, created_at, last_modified_at) values (1, 'a@a.com', 'a', '1234', now(), now());
insert into users (id, email, name, password, created_at, last_modified_at) values (2, 'b@a.com', 'b', '1234', now(), now());
insert into users (id, email, name, password, created_at, last_modified_at) values (3, 'c@a.com', 'c', '1234', now(), now());

insert into item (id, name, price, stock_quantity, user_id, created_at, last_modified_at) values (1, 'item-a', 100, 100, 1, now(), now());
insert into item (id, name, price, stock_quantity, user_id, created_at, last_modified_at) values (2, 'item-b', 200, 200, 2, now(), now());
insert into item (id, name, price, stock_quantity, user_id, created_at, last_modified_at) values (3, 'item-c', 300, 300, 2, now(), now());
