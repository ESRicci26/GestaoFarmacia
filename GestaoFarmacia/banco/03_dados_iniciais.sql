-- Execute conectado ao banco "gestaofarmacia":
--   psql -U postgres -d gestaofarmacia -f banco/03_dados_iniciais.sql
-- Requer a extensão pgcrypto (senhas armazenadas com hash bcrypt).

CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- ---------------------------------------------------------------------
-- Núcleo da aplicação
-- ---------------------------------------------------------------------

INSERT INTO farmacias (nome, localizacao) VALUES
    ('Farmácia 1', 'Centro'),
    ('Farmácia 2', 'Zona Sul');

INSERT INTO lojas (nome, localizacao) VALUES
    ('Loja 1', 'Barisal'),
    ('Loja 2', 'Dhaka');

INSERT INTO medicamentos
    (farmacia_id, nome, tipo, composicao, descricao, quantidade_disponivel, preco, data_fabricacao, data_validade)
VALUES
    (1, 'Crocin',   'Paracetamol', 'Paracetamol', 'Usado para febre',   40, 20.00, '2015-09-10', '2027-05-20'),
    (1, 'Anacin',   'Antiácido',   'Antiácido',   'Usado para acidez',  80, 30.00, '2015-10-10', '2027-10-20'),
    (2, 'Cetaphil', 'Dermatológico','Antiácido',  'Usado para acne',    80, 30.00, '2015-10-10', '2027-10-20');

-- ---------------------------------------------------------------------
-- Tabelas de apoio
-- ---------------------------------------------------------------------

INSERT INTO empresas (nome, endereco, telefone) VALUES
    ('Cipla',      'Mumbai',  '12903'),
    ('Sun Pharma', 'Mysore',  '01289078443'),
    ('Med_City',   'Nellore', '010114367832');

INSERT INTO estoque_produtos
    (codigo_barras, nome, tipo, dose, codigo, preco_custo, preco_venda, situacao_validade,
     empresa_nome, data_producao, data_validade, local_armazenamento, quantidade)
VALUES
    ('fsdgjfihjorodsf', 'Novalo',  'Comprimido', 'normal', '3d00', 2,  3,  'Disponível para uso',
     'Med_City', '2017-03-03', '2019-03-03', 'N-Direita',  40),
    ('ftrkl432432md',   'novafol', 'Comprimido', 'normal', '2xaa', 33, 40, 'Disponível para uso',
     'Med_City', '2016-01-01', '2017-01-01', 'N-Esquerda', 27);

INSERT INTO compras (codigo_barras, nome, tipo, empresa_nome, quantidade, preco, valor_total) VALUES
    ('fsdgjfihjorodsf', 'Novalo', 'Comprimido', 'Med_City', 40, 2, 80);

INSERT INTO historico_vendas
    (usuario_nome, codigo_barras, nome, tipo, dose, quantidade, preco, valor_total, data_venda, hora_venda)
VALUES
    ('admin', 'sgnfsjkfnsdjfkb', 'Breofin',   'Comprimido', 'Uso livre', 2, 6,  12, '2017-02-12', '05:02:06'),
    ('admin', 'nbhdl4978549',    'Morfin',    'Injeção',    '1 (Dia)',   2, 14, 28, '2017-02-13', '01:38:00'),
    ('mark',  'sgnfsjkfnsdjfkb', 'Breofin',   'Comprimido', 'Uso livre', 5, 6,  30, '2017-02-13', '01:59:43'),
    ('admin', 'fsdjkbdfjkffds',  'Declofien', 'Injeção',    'Livre',     5, 14, 70, '2017-02-17', '09:56:11'),
    ('mark',  'AnyBarcodedaf',   'AnyName',   'Bebida',     '2 Dias',    6, 14, 84, '2017-02-18', '11:18:29'),
    ('admin', 'ftrkl432432md',   'novafol',   'Comprimido', 'normal',    6, 40, 240,'2017-04-14', '04:51:01');

INSERT INTO usuarios (nome, data_nascimento, endereco, telefone, salario, senha_hash) VALUES
    ('admin',      '1995-12-23', 'Algum lugar, Índia',                        '9800000000',   50000, crypt('admin', gen_salt('bf'))),
    ('mark',       '1972-02-03', 'Bangalore, Índia',                          '01290789432',   2000, crypt('mark', gen_salt('bf'))),
    ('clark',      '1971-02-03', 'Lugar nenhum, Terra-616',                   '01147893423',   4000, crypt('rootaccess', gen_salt('bf'))),
    ('Tont Stark', '1977-08-07', '10880 Malibu Point, Malibu, Califórnia',    '011804368743',  3000, crypt('rootaccess', gen_salt('bf')));

INSERT INTO historico_acessos (usuario_nome, perfil, data_acesso, hora_acesso) VALUES
    ('admin', 'Administrador', '2017-02-17', '10:30:24'),
    ('mark',  'Funcionário',   '2017-02-17', '10:32:56'),
    ('admin', 'Administrador', '2018-03-24', '08:52:50');

INSERT INTO caixa_entrada (remetente, destinatario, texto) VALUES
    ('admin', 'mark',  'Bem-vindo, mark'),
    ('mark',  'admin', 'Bem-vindo, senhor'),
    ('admin', 'mark',  'mark, por favor atualize o cadastro do medicamento'),
    ('mark',  'admin', 'Ok, farei isso');


Select * from caixa_entrada
Select * from compras;
Select * from empresas;
Select * from estoque_produtos;
Select * from farmacias;
Select * from historico_acessos;
Select * from historico_mensagens;
Select * from historico_vendas;
Select * from itens_pedido;
Select * from lojas;
Select * from medicamentos;
Select * from pedidos;
Select * from usuarios;
Select * from validades_produtos;
Select * from vendas;
