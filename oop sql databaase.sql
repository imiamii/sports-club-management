CREATE TABLE Sport (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE Player (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INTEGER NOT NULL,
    sport_id INTEGER REFERENCES Sport(id) ON DELETE CASCADE
);

INSERT INTO Sport (name) VALUES ('Football'), ('Basketball');
INSERT INTO Player (name, age, sport_id) VALUES 
('John Doe', 22, 1),
('Mike Smith', 25, 2);

SELECT * FROM Player;

UPDATE Player SET name = 'John Updated' WHERE id = 1;

DELETE FROM Player WHERE id = 1;

SELECT table_name FROM information_schema.tables WHERE table_schema = 'public';
