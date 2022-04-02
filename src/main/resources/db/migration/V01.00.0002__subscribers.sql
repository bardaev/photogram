CREATE TABLE subscribers (
    id_subscriber INTEGER NOT NULL,
    id_author INTEGER NOT NULL,
    PRIMARY KEY (id_subscriber, id_author)
);