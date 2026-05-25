CREATE TABLE IF NOT EXISTS lista_compras (
    id SERIAL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    preco_unitario NUMERIC(10, 2) NOT NULL,
    quantidade NUMERIC(10, 2) NOT NULL,
    total NUMERIC(12, 2) NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índice para melhorar performance em buscas por data
CREATE INDEX IF NOT EXISTS idx_lista_compras_criado_em ON lista_compras(criado_em DESC);

-- Comentários para documentação
COMMENT ON TABLE lista_compras IS 'Tabela de lista de compras';
COMMENT ON COLUMN lista_compras.id IS 'Identificador único';
COMMENT ON COLUMN lista_compras.descricao IS 'Descrição do item';
COMMENT ON COLUMN lista_compras.preco_unitario IS 'Preço unitário do item';
COMMENT ON COLUMN lista_compras.quantidade IS 'Quantidade do item';
COMMENT ON COLUMN lista_compras.total IS 'Total (quantidade * preco_unitario)';
COMMENT ON COLUMN lista_compras.criado_em IS 'Data e hora de criação do item';
COMMENT ON COLUMN lista_compras.atualizado_em IS 'Data e hora da última atualização do item';


CREATE TABLE IF NOT EXISTS lista_afazeres (
    id SERIAL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'pendente',
    prazo_conclusao DATE,
    importancia INTEGER DEFAULT 0,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índice para melhorar performance em buscas por data
CREATE INDEX IF NOT EXISTS idx_lista_afazeres_prazo_conclusao ON lista_afazeres(prazo_conclusao DESC);

-- Comentários para documentação
COMMENT ON TABLE lista_afazeres IS 'Tabela de lista de afazeres';
COMMENT ON COLUMN lista_afazeres.id IS 'Identificador único';
COMMENT ON COLUMN lista_afazeres.descricao IS 'Descrição da tarefa';
COMMENT ON COLUMN lista_afazeres."status" IS 'Status da tarefa';
COMMENT ON COLUMN lista_afazeres.prazo_conclusao IS 'Prazo de conclusão da tarefa';
COMMENT ON COLUMN lista_afazeres.importancia IS 'Importância da tarefa, onde 0 é baixa, 1 é média e 2 é alta';
COMMENT ON COLUMN lista_afazeres.criado_em IS 'Data e hora de criação da tarefa';
COMMENT ON COLUMN lista_afazeres.atualizado_em IS 'Data e hora da última atualização da tarefa';
