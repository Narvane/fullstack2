CREATE TABLE users
(
    id       UUID PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE INDEX idx_users_email ON users (email);

ALTER TABLE tasklists
    ADD COLUMN user_id UUID;

ALTER TABLE tasklists
    ADD CONSTRAINT fk_tasklists_users
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE;

CREATE INDEX idx_tasklists_user_id ON tasklists (user_id);

