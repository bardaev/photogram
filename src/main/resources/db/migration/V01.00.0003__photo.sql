CREATE TABLE photo (
    id_photo TEXT NOT NULL PRIMARY KEY,
    description TEXT,
    created TIMESTAMP,
    id_user INTEGER NOT NULL,
    FOREIGN KEY (id_user) REFERENCES pg.users (id)
);