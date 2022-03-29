CREATE SEQUENCE hibernate_sequence;

CREATE TABLE users (
    id INTEGER NOT NULL,
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(500) NOT NULL,
    created TIMESTAMP,
    enabled BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER SEQUENCE hibernate_sequence OWNED BY users.id;

CREATE TABLE IF NOT EXISTS persistent_logins (
    username VARCHAR(64) NOT NULL,
    series VARCHAR(64) PRIMARY KEY,
    token VARCHAR(64) NOT NULL,
    last_used TIMESTAMP NOT NULL
);