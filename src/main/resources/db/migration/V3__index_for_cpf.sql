-- Create index on cpf for faster lookups
CREATE INDEX IF NOT EXISTS idx_users_cpf ON users(cpf);


