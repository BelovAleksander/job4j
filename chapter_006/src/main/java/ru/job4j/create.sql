
CREATE DATABASE system_of_items;

CREATE TABLE roles (
title text PRIMARY KEY
);

CREATE TABLE rules (
title text PRIMARY KEY
);

CREATE TABLE users (
user_id serial PRIMARY KEY,
name text,
role text REFERENCES roles (title)
);

CREATE TABLE rules_of_roles (
role text REFERENCES roles (title),
rule text REFERENCES rules (title),
is_active boolean,
CONSTRAINT id PRIMARY KEY (role, rule)
);

CREATE TABLE categories (
title text PRIMARY KEY
);

CREATE TABLE states (
title text PRIMARY KEY
);

CREATE TABLE items (
item_id serial PRIMARY KEY,
description text,
user_id serial REFERENCES users (user_id),
category text REFERENCES categories (title),
state text REFERENCES states (title)
);

CREATE TABLE attaches (
att_id serial PRIMARY KEY,
content text,
item serial REFERENCES items (item_id)
);

CREATE TABLE comments (
com_id serial PRIMARY KEY,
content text,
item serial REFERENCES items (item_id),
user_id serial REFERENCES users (user_id)
);


INSERT INTO roles (title)
VALUES ('admin'),
	('user');

INSERT INTO rules (title)
VALUES ('can create new item'),
	('can remove item'),
	('can block user');

INSERT INTO rules_of_roles (role, rule, is_active)
VALUES ('admin', 'can create new item', true),
	('admin', 'can remove item', true),
	('admin', 'can block user', true),
	('user', 'can create new item', true);

INSERT INTO users (user_id, name, role)
VALUES (1, 'Alex', 'user'),
	(2, 'Kate', 'user'),
	(777, 'Veniamin', 'admin');

INSERT INTO categories (title)
VALUES ('offers'),
	('complaints');

INSERT INTO states (title)
VALUES ('is active'),
	('completed'),
	('removed');

INSERT INTO items (item_id, description, user_id, category, state)
VALUES (1, 'Lets take a party!', 1, 'offers', 'is active'),
	(2, 'I have a small salary', 2, 'complaints', 'removed'),
	(3, 'Staff reduction is required', 777, 'offers', 'completed');

INSERT INTO attaches (att_id, content, item)
VALUES (12, 'some beer...', 1),
	(32, 'declaration', 3);

INSERT INTO comments (com_id, content, item, user_id)
VALUES (12, 'OK', 1, 2);

