CREATE TABLE IF NOT EXISTS clients (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    passport_number VARCHAR(50) NOT NULL UNIQUE,
    monthly_income NUMERIC(15, 2) NOT NULL,
    current_debt NUMERIC(15, 2) NOT NULL DEFAULT 0.00,
    version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
                             );

CREATE TABLE IF NOT EXISTS loan_applications (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    amount NUMERIC(15, 2) NOT NULL,
    term_months INT NOT NULL,
    purpose VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    interest_rate DOUBLE PRECISION,
    debt_load_ratio DOUBLE PRECISION,
    rejection_reason VARCHAR(500),
    version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_loan_client FOREIGN KEY (client_id) REFERENCES clients (id) ON DELETE CASCADE
    );

CREATE INDEX IF NOT EXISTS idx_loans_client_id ON loan_applications (client_id);
CREATE INDEX IF NOT EXISTS idx_clients_passport ON clients (passport_number);