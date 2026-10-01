CREATE TABLE authors (
    id UUID PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE books (
    id UUID PRIMARY KEY,
    title VARCHAR(300) NOT NULL,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    author_id UUID NOT NULL REFERENCES authors(id),
    status VARCHAR(32) NOT NULL,
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_books_author_id ON books(author_id);

