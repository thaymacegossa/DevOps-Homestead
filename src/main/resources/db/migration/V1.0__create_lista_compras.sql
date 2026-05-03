-- Migration V1.0: Create lista_compras table
CREATE TABLE IF NOT EXISTS lista_compras (
    id SERIAL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    preco_unitario NUMERIC(10, 2) NOT NULL,
    quantidade NUMERIC(10, 2) NOT NULL,
    total NUMERIC(12, 2) NOT NULL,
    data_hora TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índice para melhorar performance em buscas por data
CREATE INDEX IF NOT EXISTS idx_lista_compras_data_hora ON lista_compras(data_hora DESC);

-- Comentários para documentação
COMMENT ON TABLE lista_compras IS 'Tabela de lista de compras';
COMMENT ON COLUMN lista_compras.id IS 'Identificador único';
COMMENT ON COLUMN lista_compras.descricao IS 'Descrição do item';
COMMENT ON COLUMN lista_compras.preco_unitario IS 'Preço unitário do item';
COMMENT ON COLUMN lista_compras.quantidade IS 'Quantidade do item';
COMMENT ON COLUMN lista_compras.total IS 'Total (quantidade * preco_unitario)';
COMMENT ON COLUMN lista_compras.data_hora IS 'Data e hora do item na lista';
