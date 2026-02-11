-- Initial database schema setup for systeml

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    admin BOOLEAN DEFAULT FALSE,
    password_hash VARCHAR(255) NOT NULL,
    sex VARCHAR(10),
    cpf VARCHAR(14) UNIQUE,
    cep VARCHAR(9),
    street VARCHAR(255),
    number VARCHAR(10),
    city VARCHAR(255),
    state VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create index on email for faster lookups
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);

