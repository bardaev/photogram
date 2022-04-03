CREATE TABLE photo (
    id_photo INTEGER NOT NULL PRIMARY KEY,
    photo bytea,
    description TEXT,
    created TIMESTAMP,
    id_user INTEGER NOT NULL,
    FOREIGN KEY (id_user) REFERENCES pg.users (id_user)
);