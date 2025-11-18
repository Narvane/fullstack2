CREATE TABLE tasklists
(
    id    UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL
);

CREATE TABLE tasks
(
    id          UUID PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    completed   BOOLEAN      NOT NULL DEFAULT FALSE,
    tasklist_id UUID         NOT NULL,
    CONSTRAINT fk_tasks_tasklists
        FOREIGN KEY (tasklist_id)
            REFERENCES tasklists (id)
            ON DELETE CASCADE
);

CREATE INDEX idx_tasks_tasklist_id ON tasks (tasklist_id);