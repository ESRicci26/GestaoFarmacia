-- Execute conectado ao banco "gestaofarmacia":
--   psql -U postgres -d gestaofarmacia -f banco/02_criar_tabelas.sql

-- =====================================================================
-- Núcleo da aplicação: farmácias, lojas, medicamentos e pedidos
-- =====================================================================

CREATE TABLE farmacias (
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome         VARCHAR(100) NOT NULL,
    localizacao  VARCHAR(150)
);

CREATE TABLE lojas (
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome         VARCHAR(100) NOT NULL,
    localizacao  VARCHAR(150) NOT NULL
);

CREATE TABLE medicamentos (
    id                    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    farmacia_id           BIGINT        NOT NULL REFERENCES farmacias (id) ON DELETE CASCADE,
    nome                  VARCHAR(100)  NOT NULL,
    tipo                  VARCHAR(50)   NOT NULL,
    composicao            VARCHAR(150)  NOT NULL,
    descricao             VARCHAR(255)  NOT NULL,
    quantidade_disponivel INTEGER       NOT NULL CHECK (quantidade_disponivel >= 0),
    preco                 NUMERIC(12,2) NOT NULL CHECK (preco >= 0),
    data_fabricacao       DATE          NOT NULL,
    data_validade         DATE          NOT NULL,
    CONSTRAINT ck_medicamentos_datas CHECK (data_validade >= data_fabricacao)
);
CREATE INDEX idx_medicamentos_farmacia ON medicamentos (farmacia_id);
CREATE INDEX idx_medicamentos_nome ON medicamentos (LOWER(nome));

CREATE TABLE pedidos (
    id        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    loja_id   BIGINT    NOT NULL REFERENCES lojas (id) ON DELETE CASCADE,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_pedidos_loja ON pedidos (loja_id);

CREATE TABLE itens_pedido (
    id             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pedido_id      BIGINT        NOT NULL REFERENCES pedidos (id) ON DELETE CASCADE,
    medicamento_id BIGINT        NOT NULL REFERENCES medicamentos (id) ON DELETE RESTRICT,
    quantidade     INTEGER       NOT NULL CHECK (quantidade > 0),
    preco_venda    NUMERIC(12,2) NOT NULL CHECK (preco_venda >= 0)
);
CREATE INDEX idx_itens_pedido_pedido ON itens_pedido (pedido_id);

-- =====================================================================
-- Tabelas de apoio (cadastros e históricos operacionais)
-- =====================================================================

CREATE TABLE empresas (
    nome      VARCHAR(50) PRIMARY KEY,
    endereco  VARCHAR(50) NOT NULL,
    telefone  VARCHAR(20) NOT NULL
);

CREATE TABLE estoque_produtos (
    codigo_barras       VARCHAR(20) PRIMARY KEY,
    nome                VARCHAR(50)   NOT NULL,
    tipo                VARCHAR(20)   NOT NULL,
    dose                VARCHAR(10)   NOT NULL,
    codigo              VARCHAR(10)   NOT NULL,
    preco_custo         NUMERIC(12,2) NOT NULL,
    preco_venda         NUMERIC(12,2) NOT NULL,
    situacao_validade   VARCHAR(20)   NOT NULL,
    empresa_nome        VARCHAR(50)   NOT NULL REFERENCES empresas (nome) ON UPDATE CASCADE,
    data_producao       DATE          NOT NULL,
    data_validade       DATE          NOT NULL,
    local_armazenamento VARCHAR(20)   NOT NULL,
    quantidade          INTEGER       NOT NULL CHECK (quantidade >= 0)
);

CREATE TABLE compras (
    codigo_barras VARCHAR(20) PRIMARY KEY,
    nome          VARCHAR(50)   NOT NULL,
    tipo          VARCHAR(20)   NOT NULL,
    empresa_nome  VARCHAR(50)   NOT NULL REFERENCES empresas (nome) ON UPDATE CASCADE ON DELETE CASCADE,
    quantidade    INTEGER       NOT NULL CHECK (quantidade > 0),
    preco         NUMERIC(12,2) NOT NULL,
    valor_total   NUMERIC(12,2) NOT NULL
);

CREATE TABLE vendas (
    id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codigo_barras VARCHAR(20)   NOT NULL,
    nome          VARCHAR(50)   NOT NULL,
    tipo          VARCHAR(20)   NOT NULL,
    dose          VARCHAR(20)   NOT NULL,
    quantidade    INTEGER       NOT NULL CHECK (quantidade > 0),
    preco         NUMERIC(12,2) NOT NULL,
    valor_total   NUMERIC(12,2) NOT NULL,
    data_venda    DATE          NOT NULL
);

CREATE TABLE historico_vendas (
    id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    usuario_nome  VARCHAR(20)   NOT NULL,
    codigo_barras VARCHAR(20)   NOT NULL,
    nome          VARCHAR(50)   NOT NULL,
    tipo          VARCHAR(20)   NOT NULL,
    dose          VARCHAR(20)   NOT NULL,
    quantidade    INTEGER       NOT NULL CHECK (quantidade > 0),
    preco         NUMERIC(12,2) NOT NULL,
    valor_total   NUMERIC(12,2) NOT NULL,
    data_venda    DATE          NOT NULL,
    hora_venda    TIME          NOT NULL
);

CREATE TABLE validades_produtos (
    id                   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome_produto         VARCHAR(50) NOT NULL,
    codigo_produto       VARCHAR(20) NOT NULL,
    data_validade        DATE        NOT NULL,
    quantidade_restante  INTEGER     NOT NULL CHECK (quantidade_restante >= 0)
);

CREATE TABLE usuarios (
    id               BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome             VARCHAR(50)   NOT NULL UNIQUE,
    data_nascimento  DATE          NOT NULL,
    endereco         VARCHAR(100)  NOT NULL,
    telefone         VARCHAR(20)   NOT NULL,
    salario          NUMERIC(12,2) NOT NULL,
    senha_hash       VARCHAR(100)  NOT NULL
);

CREATE TABLE historico_acessos (
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    usuario_nome VARCHAR(50) NOT NULL,
    perfil       VARCHAR(20) NOT NULL,
    data_acesso  DATE        NOT NULL,
    hora_acesso  TIME        NOT NULL
);

CREATE TABLE caixa_entrada (
    id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    remetente     VARCHAR(20)  NOT NULL,
    destinatario  VARCHAR(20)  NOT NULL,
    texto         VARCHAR(200) NOT NULL
);

CREATE TABLE historico_mensagens (
    id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    remetente     VARCHAR(20)  NOT NULL,
    destinatario  VARCHAR(20)  NOT NULL,
    texto         VARCHAR(200) NOT NULL
);
