CREATE TABLE IF NOT EXISTS livro (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255),
    disponivel BOOLEAN
);
-- Adicione outras tabelas conforme necessário