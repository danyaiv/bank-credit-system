ALTER TABLE books
    ADD COLUMN publication_year INTEGER NULL;

ALTER TABLE books
    ADD CONSTRAINT chk_books_publication_year
        CHECK (publication_year BETWEEN 1450 AND 3000);

