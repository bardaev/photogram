CREATE TABLE comment (
    id_comment INTEGER NOT NULL PRIMARY KEY,
    created TIMESTAMP,
    text TEXT,
    id_user INTEGER NOT NULL,
    FOREIGN KEY (id_user) REFERENCES pg.users (id)
);