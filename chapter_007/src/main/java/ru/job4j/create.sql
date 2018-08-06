

CREATE TABLE roles (
id serial PRIMARY KEY,
title text
);

CREATE TABLE rules (
id serial PRIMARY KEY,
title text
);

CREATE TABLE users (
id serial PRIMARY KEY,
name text,
role_id serial REFERENCES roles (id)
);

CREATE TABLE rules_of_roles (
role_id serial REFERENCES roles (id),
rule_id serial REFERENCES rules (id),
is_active boolean,
CONSTRAINT id PRIMARY KEY (role_id, rule_id)
);

CREATE TABLE categories (
id serial PRIMARY KEY,
title text
);

CREATE TABLE states (
id serial PRIMARY KEY,
title text
);

CREATE TABLE items (
id serial PRIMARY KEY,
description text,
user_id serial REFERENCES users (id),
cat_id serial REFERENCES categories (id),
st_id serial REFERENCES states (id)
);

CREATE TABLE attaches (
id serial PRIMARY KEY,
content text,
item_id serial REFERENCES items (id)
);

CREATE TABLE comments (
id serial PRIMARY KEY,
content text,
item serial REFERENCES items (id),
user_id serial REFERENCES users (id)
);


INSERT INTO roles (title)
VALUES ('admin'),
	('user');

INSERT INTO rules (title)
VALUES ('can create new item'),
	('can remove item'),
	('can block user');

INSERT INTO rules_of_roles (role_id, rule_id, is_active)
VALUES (1, 1, true),
	(1, 2, true),
	(1, 3, true),
	(2, 1, true);

INSERT INTO users (id, name, role_id)
VALUES (1, 'Alex', 2),
	(2, 'Kate', 2),
	(777, 'Veniamin', 1);

INSERT INTO categories (title)
VALUES ('offers'),
	('complaints');

INSERT INTO states (title)
VALUES ('is active'),
	('completed'),
	('removed');

INSERT INTO items (id, description, user_id, cat_id, st_id)
VALUES (1, 'Lets take a party!', 1, 1, 1),
	(2, 'I have a small salary', 2, 2, 3),
	(3, 'Staff reduction is required', 777, 1, 2);

INSERT INTO attaches (id, content, item_id)
VALUES (12, 'some beer...', 1),
	(32, 'declaration', 3);

INSERT INTO comments (id, content, item, user_id)
VALUES (12, 'OK', 1, 2);
