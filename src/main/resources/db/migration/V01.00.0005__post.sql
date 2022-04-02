CREATE TABLE post (
    id_post INTEGER NOT NULL PRIMARY KEY,
    description TEXT,
    created TIMESTAMP,
    id_photo TEXT NOT NULL,
    id_comment INTEGER NOT NULL,
    FOREIGN KEY (id_photo) REFERENCES pg.photo (id_photo),
    FOREIGN KEY (id_comment) REFERENCES pg.comment (id_comment)
);