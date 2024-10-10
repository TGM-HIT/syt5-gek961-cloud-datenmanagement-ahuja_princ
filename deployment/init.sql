CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password INTEGER NOT NULL,
    roles TEXT[]
);

-- Beispiel-Eintrag für einen Benutzer mit mehreren Rollen
INSERT INTO users (name, username, password, roles)
VALUES
    ('John Doe', 'johndoe@example.com', 69490486, ARRAY['ADMIN', 'READER']); -- 69490486 = Hallo
