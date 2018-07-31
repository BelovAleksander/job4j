
--SQL Select by company [#2760]
--@since 31.07.18

--Retrieve in a single query:
--names of all persons that are NOT in the company with id = 5
SELECT person.name
FROM person, company
WHERE person.company_id != 5 AND person.company_id = company.id;


-- company name for each person
SELECT person.name, company.name
FROM person LEFT OUTER JOIN company
ON person.company_id = company.id;

--Select the name of the company with the maximum number of persons
-- + number of persons in this company
WITH companies as
(SELECT company.name, count(person)
FROM person, company
WHERE person.company_id = company.id
GROUP BY company.name)
SELECT * FROM companies
WHERE companies.count = (SELECT MAX(companies.count) FROM companies);
