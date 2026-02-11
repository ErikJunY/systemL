-- Altering users table to add telephone columns
ALTER TABLE users ADD COLUMN IF NOT EXISTS telephone VARCHAR(20);
ALTER TABLE users ADD COLUMN IF NOT EXISTS uf VARCHAR(2);


