create sequence author_id_seq start with 1 increment by 1;
CREATE TABLE author (
    id       INT PRIMARY KEY,
    username VARCHAR(128) UNIQUE NOT NULL,
    email    VARCHAR(128),
    bio      TEXT
);
