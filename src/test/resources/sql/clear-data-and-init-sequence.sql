delete from users;
delete from item;

ALTER SEQUENCE users_id_seq RESTART WITH 1;
ALTER SEQUENCE item_id_seq RESTART WITH 1;