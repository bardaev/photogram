CREATE SEQUENCE hibernate_sequence;

CREATE TABLE users (
    id_user INTEGER NOT NULL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(500) NOT NULL,
    description TEXT,
    created TIMESTAMP,
    enabled BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER SEQUENCE hibernate_sequence OWNED BY users.id_user;

CREATE TABLE IF NOT EXISTS persistent_logins (
    username VARCHAR(64) NOT NULL,
    series VARCHAR(64) PRIMARY KEY,
    token VARCHAR(64) NOT NULL,
    last_used TIMESTAMP NOT NULL
);