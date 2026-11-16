--
-- PostgreSQL database dump
--

-- Dumped from database version 13.3
-- Dumped by pg_dump version 13.3

-- Started on 2026-11-16 15:37:03

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2 (class 3079 OID 75248)
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


--
-- TOC entry 3181 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 225 (class 1259 OID 75236)
-- Name: caixa_entrada; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.caixa_entrada (
    id bigint NOT NULL,
    remetente character varying(20) NOT NULL,
    destinatario character varying(20) NOT NULL,
    texto character varying(200) NOT NULL
);


ALTER TABLE public.caixa_entrada OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 75234)
-- Name: caixa_entrada_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.caixa_entrada ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.caixa_entrada_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 213 (class 1259 OID 75183)
-- Name: compras; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.compras (
    codigo_barras character varying(20) NOT NULL,
    nome character varying(50) NOT NULL,
    tipo character varying(20) NOT NULL,
    empresa_nome character varying(50) NOT NULL,
    quantidade integer NOT NULL,
    preco numeric(12,2) NOT NULL,
    valor_total numeric(12,2) NOT NULL,
    CONSTRAINT compras_quantidade_check CHECK ((quantidade > 0))
);


ALTER TABLE public.compras OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 75167)
-- Name: empresas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.empresas (
    nome character varying(50) NOT NULL,
    endereco character varying(50) NOT NULL,
    telefone character varying(20) NOT NULL
);


ALTER TABLE public.empresas OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 75172)
-- Name: estoque_produtos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.estoque_produtos (
    codigo_barras character varying(20) NOT NULL,
    nome character varying(50) NOT NULL,
    tipo character varying(20) NOT NULL,
    dose character varying(10) NOT NULL,
    codigo character varying(10) NOT NULL,
    preco_custo numeric(12,2) NOT NULL,
    preco_venda numeric(12,2) NOT NULL,
    situacao_validade character varying(20) NOT NULL,
    empresa_nome character varying(50) NOT NULL,
    data_producao date NOT NULL,
    data_validade date NOT NULL,
    local_armazenamento character varying(20) NOT NULL,
    quantidade integer NOT NULL,
    CONSTRAINT estoque_produtos_quantidade_check CHECK ((quantidade >= 0))
);


ALTER TABLE public.estoque_produtos OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 75101)
-- Name: farmacias; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.farmacias (
    id bigint NOT NULL,
    nome character varying(100) NOT NULL,
    localizacao character varying(150)
);


ALTER TABLE public.farmacias OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 75099)
-- Name: farmacias_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.farmacias ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.farmacias_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 223 (class 1259 OID 75229)
-- Name: historico_acessos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.historico_acessos (
    id bigint NOT NULL,
    usuario_nome character varying(50) NOT NULL,
    perfil character varying(20) NOT NULL,
    data_acesso date NOT NULL,
    hora_acesso time without time zone NOT NULL
);


ALTER TABLE public.historico_acessos OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 75227)
-- Name: historico_acessos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.historico_acessos ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.historico_acessos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 227 (class 1259 OID 75243)
-- Name: historico_mensagens; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.historico_mensagens (
    id bigint NOT NULL,
    remetente character varying(20) NOT NULL,
    destinatario character varying(20) NOT NULL,
    texto character varying(200) NOT NULL
);


ALTER TABLE public.historico_mensagens OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 75241)
-- Name: historico_mensagens_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.historico_mensagens ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.historico_mensagens_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 217 (class 1259 OID 75204)
-- Name: historico_vendas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.historico_vendas (
    id bigint NOT NULL,
    usuario_nome character varying(20) NOT NULL,
    codigo_barras character varying(20) NOT NULL,
    nome character varying(50) NOT NULL,
    tipo character varying(20) NOT NULL,
    dose character varying(20) NOT NULL,
    quantidade integer NOT NULL,
    preco numeric(12,2) NOT NULL,
    valor_total numeric(12,2) NOT NULL,
    data_venda date NOT NULL,
    hora_venda time without time zone NOT NULL,
    CONSTRAINT historico_vendas_quantidade_check CHECK ((quantidade > 0))
);


ALTER TABLE public.historico_vendas OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 75202)
-- Name: historico_vendas_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.historico_vendas ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.historico_vendas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 210 (class 1259 OID 75149)
-- Name: itens_pedido; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.itens_pedido (
    id bigint NOT NULL,
    pedido_id bigint NOT NULL,
    medicamento_id bigint NOT NULL,
    quantidade integer NOT NULL,
    preco_venda numeric(12,2) NOT NULL,
    CONSTRAINT itens_pedido_preco_venda_check CHECK ((preco_venda >= (0)::numeric)),
    CONSTRAINT itens_pedido_quantidade_check CHECK ((quantidade > 0))
);


ALTER TABLE public.itens_pedido OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 75147)
-- Name: itens_pedido_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.itens_pedido ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.itens_pedido_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 204 (class 1259 OID 75108)
-- Name: lojas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lojas (
    id bigint NOT NULL,
    nome character varying(100) NOT NULL,
    localizacao character varying(150) NOT NULL
);


ALTER TABLE public.lojas OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 75106)
-- Name: lojas_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.lojas ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.lojas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 206 (class 1259 OID 75115)
-- Name: medicamentos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.medicamentos (
    id bigint NOT NULL,
    farmacia_id bigint NOT NULL,
    nome character varying(100) NOT NULL,
    tipo character varying(50) NOT NULL,
    composicao character varying(150) NOT NULL,
    descricao character varying(255) NOT NULL,
    quantidade_disponivel integer NOT NULL,
    preco numeric(12,2) NOT NULL,
    data_fabricacao date NOT NULL,
    data_validade date NOT NULL,
    CONSTRAINT ck_medicamentos_datas CHECK ((data_validade >= data_fabricacao)),
    CONSTRAINT medicamentos_preco_check CHECK ((preco >= (0)::numeric)),
    CONSTRAINT medicamentos_quantidade_disponivel_check CHECK ((quantidade_disponivel >= 0))
);


ALTER TABLE public.medicamentos OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 75113)
-- Name: medicamentos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.medicamentos ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.medicamentos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 208 (class 1259 OID 75135)
-- Name: pedidos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pedidos (
    id bigint NOT NULL,
    loja_id bigint NOT NULL,
    criado_em timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.pedidos OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 75133)
-- Name: pedidos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.pedidos ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.pedidos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 221 (class 1259 OID 75220)
-- Name: usuarios; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuarios (
    id bigint NOT NULL,
    nome character varying(50) NOT NULL,
    data_nascimento date NOT NULL,
    endereco character varying(100) NOT NULL,
    telefone character varying(20) NOT NULL,
    salario numeric(12,2) NOT NULL,
    senha_hash character varying(100) NOT NULL
);


ALTER TABLE public.usuarios OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 75218)
-- Name: usuarios_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.usuarios ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.usuarios_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 219 (class 1259 OID 75212)
-- Name: validades_produtos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.validades_produtos (
    id bigint NOT NULL,
    nome_produto character varying(50) NOT NULL,
    codigo_produto character varying(20) NOT NULL,
    data_validade date NOT NULL,
    quantidade_restante integer NOT NULL,
    CONSTRAINT validades_produtos_quantidade_restante_check CHECK ((quantidade_restante >= 0))
);


ALTER TABLE public.validades_produtos OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 75210)
-- Name: validades_produtos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.validades_produtos ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.validades_produtos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 215 (class 1259 OID 75196)
-- Name: vendas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vendas (
    id bigint NOT NULL,
    codigo_barras character varying(20) NOT NULL,
    nome character varying(50) NOT NULL,
    tipo character varying(20) NOT NULL,
    dose character varying(20) NOT NULL,
    quantidade integer NOT NULL,
    preco numeric(12,2) NOT NULL,
    valor_total numeric(12,2) NOT NULL,
    data_venda date NOT NULL,
    CONSTRAINT vendas_quantidade_check CHECK ((quantidade > 0))
);


ALTER TABLE public.vendas OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 75194)
-- Name: vendas_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.vendas ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.vendas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 3173 (class 0 OID 75236)
-- Dependencies: 225
-- Data for Name: caixa_entrada; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.caixa_entrada (id, remetente, destinatario, texto) FROM stdin;
1	admin	mark	Bem-vindo, mark
2	mark	admin	Bem-vindo, senhor
3	admin	mark	mark, por favor atualize o cadastro do medicamento
4	mark	admin	Ok, farei isso
\.


--
-- TOC entry 3161 (class 0 OID 75183)
-- Dependencies: 213
-- Data for Name: compras; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.compras (codigo_barras, nome, tipo, empresa_nome, quantidade, preco, valor_total) FROM stdin;
fsdgjfihjorodsf	Novalo	Comprimido	Med_City	40	2.00	80.00
\.


--
-- TOC entry 3159 (class 0 OID 75167)
-- Dependencies: 211
-- Data for Name: empresas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.empresas (nome, endereco, telefone) FROM stdin;
Cipla	Mumbai	12903
Sun Pharma	Mysore	01289078443
Med_City	Nellore	010114367832
\.


--
-- TOC entry 3160 (class 0 OID 75172)
-- Dependencies: 212
-- Data for Name: estoque_produtos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.estoque_produtos (codigo_barras, nome, tipo, dose, codigo, preco_custo, preco_venda, situacao_validade, empresa_nome, data_producao, data_validade, local_armazenamento, quantidade) FROM stdin;
fsdgjfihjorodsf	Novalo	Comprimido	normal	3d00	2.00	3.00	Disponível para uso	Med_City	2017-03-03	2019-03-03	N-Direita	40
ftrkl432432md	novafol	Comprimido	normal	2xaa	33.00	40.00	Disponível para uso	Med_City	2016-01-01	2017-01-01	N-Esquerda	27
\.


--
-- TOC entry 3150 (class 0 OID 75101)
-- Dependencies: 202
-- Data for Name: farmacias; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.farmacias (id, nome, localizacao) FROM stdin;
3	Drogaria São Paulo	15 de Novembro
4	Drogaria São Paulo	Largo 13 de Maio
1	Drogasil	Centro
2	Drogasil	Zona Sul
\.


--
-- TOC entry 3171 (class 0 OID 75229)
-- Dependencies: 223
-- Data for Name: historico_acessos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.historico_acessos (id, usuario_nome, perfil, data_acesso, hora_acesso) FROM stdin;
1	admin	Administrador	2017-02-17	10:30:24
2	mark	Funcionário	2017-02-17	10:32:56
3	admin	Administrador	2018-03-24	08:52:50
\.


--
-- TOC entry 3175 (class 0 OID 75243)
-- Dependencies: 227
-- Data for Name: historico_mensagens; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.historico_mensagens (id, remetente, destinatario, texto) FROM stdin;
\.


--
-- TOC entry 3165 (class 0 OID 75204)
-- Dependencies: 217
-- Data for Name: historico_vendas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.historico_vendas (id, usuario_nome, codigo_barras, nome, tipo, dose, quantidade, preco, valor_total, data_venda, hora_venda) FROM stdin;
1	admin	sgnfsjkfnsdjfkb	Breofin	Comprimido	Uso livre	2	6.00	12.00	2017-02-12	05:02:06
2	admin	nbhdl4978549	Morfin	Injeção	1 (Dia)	2	14.00	28.00	2017-02-13	01:38:00
3	mark	sgnfsjkfnsdjfkb	Breofin	Comprimido	Uso livre	5	6.00	30.00	2017-02-13	01:59:43
4	admin	fsdjkbdfjkffds	Declofien	Injeção	Livre	5	14.00	70.00	2017-02-17	09:56:11
5	mark	AnyBarcodedaf	AnyName	Bebida	2 Dias	6	14.00	84.00	2017-02-18	11:18:29
6	admin	ftrkl432432md	novafol	Comprimido	normal	6	40.00	240.00	2017-04-14	04:51:01
\.


--
-- TOC entry 3158 (class 0 OID 75149)
-- Dependencies: 210
-- Data for Name: itens_pedido; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.itens_pedido (id, pedido_id, medicamento_id, quantidade, preco_venda) FROM stdin;
1	1	4	1	45.78
\.


--
-- TOC entry 3152 (class 0 OID 75108)
-- Dependencies: 204
-- Data for Name: lojas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.lojas (id, nome, localizacao) FROM stdin;
1	Loja 1	Barisal
2	Loja 2	Dhaka
\.


--
-- TOC entry 3154 (class 0 OID 75115)
-- Dependencies: 206
-- Data for Name: medicamentos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.medicamentos (id, farmacia_id, nome, tipo, composicao, descricao, quantidade_disponivel, preco, data_fabricacao, data_validade) FROM stdin;
1	1	Crocin	Paracetamol	Paracetamol	Usado para febre	40	20.00	2015-09-10	2027-05-20
2	1	Anacin	Antiácido	Antiácido	Usado para acidez	80	30.00	2015-10-10	2027-10-20
3	2	Cetaphil	Dermatológico	Antiácido	Usado para acne	80	30.00	2015-10-10	2027-10-20
5	4	Colírio Lácrima PLUS	Colírio	Colírio	Usado para lubrificar o olho	85	25.62	2023-11-15	2031-11-15
4	3	Melatonina Fast	Melatonina	Melatonina	Usado para dormir	14	45.78	2024-11-12	2030-11-12
\.


--
-- TOC entry 3156 (class 0 OID 75135)
-- Dependencies: 208
-- Data for Name: pedidos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pedidos (id, loja_id, criado_em) FROM stdin;
1	1	2026-11-12 22:45:01.634684
\.


--
-- TOC entry 3169 (class 0 OID 75220)
-- Dependencies: 221
-- Data for Name: usuarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuarios (id, nome, data_nascimento, endereco, telefone, salario, senha_hash) FROM stdin;
1	admin	1995-12-23	Algum lugar, Índia	9800000000	50000.00	$2a$06$K8V27tQwPGr6X.4MYzNwfuxabHUsopqJop/scX3uzJaPr0QfngkUK
2	mark	1972-02-03	Bangalore, Índia	01290789432	2000.00	$2a$06$zYYmCMfQ5wmYAqFb2jXtAeLQfbU9T7szCnuwKl6ofgFeF/TCcGRqa
3	clark	1971-02-03	Lugar nenhum, Terra-616	01147893423	4000.00	$2a$06$Cf5yMv2alNiUelE2ou32UeDqFrTESSuegUtB.909d13lGQTNUJ/Di
4	Tont Stark	1977-08-07	10880 Malibu Point, Malibu, Califórnia	011804368743	3000.00	$2a$06$M5CEZYA6P/E.EO2GiUjxy.jMJiGF7bF5iOmq9HAIsk1rZ8oQbjlsi
\.


--
-- TOC entry 3167 (class 0 OID 75212)
-- Dependencies: 219
-- Data for Name: validades_produtos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.validades_produtos (id, nome_produto, codigo_produto, data_validade, quantidade_restante) FROM stdin;
\.


--
-- TOC entry 3163 (class 0 OID 75196)
-- Dependencies: 215
-- Data for Name: vendas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vendas (id, codigo_barras, nome, tipo, dose, quantidade, preco, valor_total, data_venda) FROM stdin;
\.


--
-- TOC entry 3182 (class 0 OID 0)
-- Dependencies: 224
-- Name: caixa_entrada_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.caixa_entrada_id_seq', 4, true);


--
-- TOC entry 3183 (class 0 OID 0)
-- Dependencies: 201
-- Name: farmacias_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.farmacias_id_seq', 4, true);


--
-- TOC entry 3184 (class 0 OID 0)
-- Dependencies: 222
-- Name: historico_acessos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.historico_acessos_id_seq', 3, true);


--
-- TOC entry 3185 (class 0 OID 0)
-- Dependencies: 226
-- Name: historico_mensagens_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.historico_mensagens_id_seq', 1, false);


--
-- TOC entry 3186 (class 0 OID 0)
-- Dependencies: 216
-- Name: historico_vendas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.historico_vendas_id_seq', 6, true);


--
-- TOC entry 3187 (class 0 OID 0)
-- Dependencies: 209
-- Name: itens_pedido_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.itens_pedido_id_seq', 1, true);


--
-- TOC entry 3188 (class 0 OID 0)
-- Dependencies: 203
-- Name: lojas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.lojas_id_seq', 2, true);


--
-- TOC entry 3189 (class 0 OID 0)
-- Dependencies: 205
-- Name: medicamentos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.medicamentos_id_seq', 5, true);


--
-- TOC entry 3190 (class 0 OID 0)
-- Dependencies: 207
-- Name: pedidos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pedidos_id_seq', 1, true);


--
-- TOC entry 3191 (class 0 OID 0)
-- Dependencies: 220
-- Name: usuarios_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usuarios_id_seq', 4, true);


--
-- TOC entry 3192 (class 0 OID 0)
-- Dependencies: 218
-- Name: validades_produtos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.validades_produtos_id_seq', 1, false);


--
-- TOC entry 3193 (class 0 OID 0)
-- Dependencies: 214
-- Name: vendas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vendas_id_seq', 1, false);


--
-- TOC entry 3010 (class 2606 OID 75240)
-- Name: caixa_entrada caixa_entrada_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.caixa_entrada
    ADD CONSTRAINT caixa_entrada_pkey PRIMARY KEY (id);


--
-- TOC entry 2996 (class 2606 OID 75188)
-- Name: compras compras_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.compras
    ADD CONSTRAINT compras_pkey PRIMARY KEY (codigo_barras);


--
-- TOC entry 2992 (class 2606 OID 75171)
-- Name: empresas empresas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresas
    ADD CONSTRAINT empresas_pkey PRIMARY KEY (nome);


--
-- TOC entry 2994 (class 2606 OID 75177)
-- Name: estoque_produtos estoque_produtos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estoque_produtos
    ADD CONSTRAINT estoque_produtos_pkey PRIMARY KEY (codigo_barras);


--
-- TOC entry 2978 (class 2606 OID 75105)
-- Name: farmacias farmacias_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.farmacias
    ADD CONSTRAINT farmacias_pkey PRIMARY KEY (id);


--
-- TOC entry 3008 (class 2606 OID 75233)
-- Name: historico_acessos historico_acessos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.historico_acessos
    ADD CONSTRAINT historico_acessos_pkey PRIMARY KEY (id);


--
-- TOC entry 3012 (class 2606 OID 75247)
-- Name: historico_mensagens historico_mensagens_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.historico_mensagens
    ADD CONSTRAINT historico_mensagens_pkey PRIMARY KEY (id);


--
-- TOC entry 3000 (class 2606 OID 75209)
-- Name: historico_vendas historico_vendas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.historico_vendas
    ADD CONSTRAINT historico_vendas_pkey PRIMARY KEY (id);


--
-- TOC entry 2990 (class 2606 OID 75155)
-- Name: itens_pedido itens_pedido_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itens_pedido
    ADD CONSTRAINT itens_pedido_pkey PRIMARY KEY (id);


--
-- TOC entry 2980 (class 2606 OID 75112)
-- Name: lojas lojas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lojas
    ADD CONSTRAINT lojas_pkey PRIMARY KEY (id);


--
-- TOC entry 2984 (class 2606 OID 75125)
-- Name: medicamentos medicamentos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medicamentos
    ADD CONSTRAINT medicamentos_pkey PRIMARY KEY (id);


--
-- TOC entry 2987 (class 2606 OID 75140)
-- Name: pedidos pedidos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedidos
    ADD CONSTRAINT pedidos_pkey PRIMARY KEY (id);


--
-- TOC entry 3004 (class 2606 OID 75226)
-- Name: usuarios usuarios_nome_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_nome_key UNIQUE (nome);


--
-- TOC entry 3006 (class 2606 OID 75224)
-- Name: usuarios usuarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (id);


--
-- TOC entry 3002 (class 2606 OID 75217)
-- Name: validades_produtos validades_produtos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.validades_produtos
    ADD CONSTRAINT validades_produtos_pkey PRIMARY KEY (id);


--
-- TOC entry 2998 (class 2606 OID 75201)
-- Name: vendas vendas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vendas
    ADD CONSTRAINT vendas_pkey PRIMARY KEY (id);


--
-- TOC entry 2988 (class 1259 OID 75166)
-- Name: idx_itens_pedido_pedido; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_itens_pedido_pedido ON public.itens_pedido USING btree (pedido_id);


--
-- TOC entry 2981 (class 1259 OID 75131)
-- Name: idx_medicamentos_farmacia; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_medicamentos_farmacia ON public.medicamentos USING btree (farmacia_id);


--
-- TOC entry 2982 (class 1259 OID 75132)
-- Name: idx_medicamentos_nome; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_medicamentos_nome ON public.medicamentos USING btree (lower((nome)::text));


--
-- TOC entry 2985 (class 1259 OID 75146)
-- Name: idx_pedidos_loja; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_pedidos_loja ON public.pedidos USING btree (loja_id);


--
-- TOC entry 3018 (class 2606 OID 75189)
-- Name: compras compras_empresa_nome_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.compras
    ADD CONSTRAINT compras_empresa_nome_fkey FOREIGN KEY (empresa_nome) REFERENCES public.empresas(nome) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3017 (class 2606 OID 75178)
-- Name: estoque_produtos estoque_produtos_empresa_nome_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estoque_produtos
    ADD CONSTRAINT estoque_produtos_empresa_nome_fkey FOREIGN KEY (empresa_nome) REFERENCES public.empresas(nome) ON UPDATE CASCADE;


--
-- TOC entry 3016 (class 2606 OID 75161)
-- Name: itens_pedido itens_pedido_medicamento_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itens_pedido
    ADD CONSTRAINT itens_pedido_medicamento_id_fkey FOREIGN KEY (medicamento_id) REFERENCES public.medicamentos(id) ON DELETE RESTRICT;


--
-- TOC entry 3015 (class 2606 OID 75156)
-- Name: itens_pedido itens_pedido_pedido_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itens_pedido
    ADD CONSTRAINT itens_pedido_pedido_id_fkey FOREIGN KEY (pedido_id) REFERENCES public.pedidos(id) ON DELETE CASCADE;


--
-- TOC entry 3013 (class 2606 OID 75126)
-- Name: medicamentos medicamentos_farmacia_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medicamentos
    ADD CONSTRAINT medicamentos_farmacia_id_fkey FOREIGN KEY (farmacia_id) REFERENCES public.farmacias(id) ON DELETE CASCADE;


--
-- TOC entry 3014 (class 2606 OID 75141)
-- Name: pedidos pedidos_loja_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedidos
    ADD CONSTRAINT pedidos_loja_id_fkey FOREIGN KEY (loja_id) REFERENCES public.lojas(id) ON DELETE CASCADE;


-- Completed on 2026-11-16 15:37:03

--
-- PostgreSQL database dump complete
--

