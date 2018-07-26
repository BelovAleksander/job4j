CREATE TABLE engines (
	type varchar(20) NOT NUll PRIMARY KEY
);

CREATE TABLE transmissions (
	type varchar(20) NOT NULL PRIMARY KEY
);

CREATE TABLE carcases (
	type varchar(20) NOT NULL PRIMARY KEY
);

CREATE TABLE cars (
	model varchar(60) NOT NULL PRIMARY KEY,
	engine varchar(20) NOT NULL REFERENCES engines(type),
	transmission varchar(20) NOT NULL REFERENCES transmissions(type),
	carcase varchar(20) NOT NULL REFERENCES carcases(type)
);

INSERT INTO engines(type)
VALUES ('petrol'),
		('diesel'),
		('electric')
;
		
INSERT INTO transmissions(type)
VALUES ('mechanic'),
		('automatic'),
		('without transmission')
;
		
INSERT INTO carcases(type)
VALUES ('sedan'),
		('hatchback'),
		('universal'),
		('sport')
;		

INSERT INTO cars(model, engine, transmission, carcase)
VALUES ('Kia RIO', 'petrol', 'mechanic', 'hatchback'),
		('Ford MONDEO', 'diesel', 'automatic', 'sedan'),
		('Tesla MODEL3', 'electric', 'without transmission', 'sedan')
;
		
SELECT * FROM cars;

SELECT e.type
FROM (SELECT * FROM cars WHERE cars.model = 'Kia RIO') as c
JOIN engines as e
ON c.engine != e.type;

SELECT t.type
FROM (SELECT * FROM cars WHERE cars.model = 'Kia RIO') as c
JOIN transmissions as t
ON c.transmission != t.type;

SELECT cs.type
FROM (SELECT * FROM cars WHERE cars.model = 'Kia RIO') as c
JOIN carcases as cs
ON c.carcases != cs.type;


