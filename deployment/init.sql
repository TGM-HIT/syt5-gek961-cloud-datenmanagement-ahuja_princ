CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    roles TEXT[]
);

-- Beispiel-Eintrag für einen Benutzer mit mehreren Rollen
INSERT INTO users (name, email, password, roles)
VALUES
    ('John Doe', 'johndoe@example.com', 'encrypted_password_here', ARRAY['ADMIN', 'READER']);