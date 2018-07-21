
CREATE TABLE types (
id serial PRIMARY KEY,
name text
);


CREATE TABLE products (
id serial PRIMARY KEY,
name text,
type_id serial REFERENCES types (id),
expired_date date,
price money
);

--1. Написать запрос получение всех продуктов с типом "СЫР"

SELECT products.*
FROM products, types
WHERE products.type_id = (SELECT types.id
		WHERE types.name = 'СЫР');

--2. Написать запрос получения всех продуктов, у кого в имени 
--   есть слово "мороженное"

SELECT * 
FROM products p
WHERE p.name like '%мороженное%';

--3. Написать запрос, который выводит все продукты,
--   срок годности которых заканчивается в следующем месяце.

SELECT *
FROM products p
WHERE p.expired_date BETWEEN '2018,07,22'AND '2018,08,22';

--4. Написать запрос, который вывод самый дорогой продукт.

SELECT products.*
FROM products
WHERE products.price = (SELECT max(price) FROM products);

--5. Написать запрос, который выводит количество всех продуктов
--   определенного типа.

SELECT count(*)
FROM products, types
WHERE products.type_id = (SELECT types.id
			WHERE types.name = 'МОЛОКО');
			
--6. Написать запрос получение всех продуктов
--   с типом "СЫР" и "МОЛОКО"

SELECT *
FROM products, types
WHERE products.type_id IN (SELECT types.id
			WHERE types.name = 'МОЛОКО' 
			OR types.name = 'СЫР');
					
--7. Написать запрос, который выводит тип продуктов,
--   которых осталось меньше 10 штук.  

-- Но у нас нет поля amount

--8. Вывести все продукты и их тип.

SELECT products.*, types.name
FROM products, types
WHERE products.type_id = types.id;






