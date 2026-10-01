-- клиенты
CREATE TABLE IF NOT EXISTS clients (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    passport_number VARCHAR(50) NOT NULL UNIQUE,
    monthly_income NUMERIC(15, 2) NOT NULL,
    current_debt NUMERIC(15, 2) NOT NULL DEFAULT 0.00,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
                             );

-- заявки
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
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             CONSTRAINT fk_loan_client FOREIGN KEY (client_id) REFERENCES clients (id) ON DELETE CASCADE
    );

-- индексы
CREATE INDEX IF NOT EXISTS idx_clients_passport ON clients (passport_number);
CREATE INDEX IF NOT EXISTS idx_loans_client_id ON loan_applications (client_id);
CREATE INDEX IF NOT EXISTS idx_loans_status ON loan_applications (status);

-- данные
INSERT INTO clients (full_name, email, passport_number, monthly_income, current_debt, created_at)
VALUES
    ('Иванов Иван Иванович', 'ivanov@bank.ru', '4510 123456', 120000.00, 15000.00, CURRENT_TIMESTAMP),
    ('Петров Петр Петрович', 'petrov@bank.ru', '4512 654321', 80000.00, 60000.00, CURRENT_TIMESTAMP)
    ON CONFLICT (passport_number) DO NOTHING;