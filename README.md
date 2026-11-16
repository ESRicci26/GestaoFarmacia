# 💊 Gestão Farmácia

Aplicação web para gestão de farmácias, lojas, medicamentos e pedidos. Permite cadastrar farmácias e seus catálogos de medicamentos, e que lojas naveguem pelo catálogo, montem um carrinho com preço de venda próprio e finalizem pedidos com baixa automática de estoque.
Para aprender a usar as telas, consulte o manual.md na raiz do projeto.
---

## 📌 Funcionalidades

### 👑 Administração do sistema
- **Farmácias:** cadastro, edição, exclusão e listagem.
- **Lojas:** cadastro, edição, exclusão e listagem.

### 🏥 Área da farmácia
- **Catálogo de medicamentos:** cadastro, edição, exclusão e listagem com tipo, composição, descrição, estoque, preço do fornecedor, datas de fabricação e validade (com sinalização de itens vencidos).

### 🏪 Área da loja
- **Catálogo:** filtro por farmácia e pesquisa por código ou nome.
- **Carrinho:** adicionar itens, alterar quantidade e remover. O preço de venda não pode ser menor que o preço do fornecedor, e a quantidade não pode exceder o estoque.
- **Pedidos:** finalização transacional (itens + baixa de estoque), consulta de pedidos e itens, com indicação de reposição de estoque.

---

## 🛠️ Tecnologias

| Item | Tecnologia |
|------|-----------|
| Linguagem | Java 21 |
| Framework | Spring Boot 3.3 (Web, Validation) |
| Acesso a dados | Spring JDBC — `JdbcTemplate` (sem JPA) |
| Banco de dados | PostgreSQL |
| Interface | Thymeleaf + CSS responsivo (sem frameworks externos) |
| Build | Maven |
| Testes | JUnit 5 + Mockito |

> O projeto **não utiliza Lombok**.

---

## 🧱 Arquitetura

Organizada em camadas inspiradas em Arquitetura Limpa / Hexagonal. As dependências sempre apontam para dentro: `apresentacao` e `infraestrutura` conhecem a `aplicacao`, que conhece o `dominio`.

```
br.com.javaricci
├── dominio                     # Regras e entidades puras (sem Spring)
│   ├── modelo                  # Farmacia, Loja, Medicamento, Pedido, ItemPedido, Carrinho...
│   └── excecao                 # RegraNegocioException, RecursoNaoEncontradoException
├── aplicacao
│   ├── porta.entrada           # Casos de uso (interfaces consumidas pela web)
│   ├── porta.saida             # Repositórios (interfaces implementadas pela infraestrutura)
│   ├── servico                 # Implementação dos casos de uso
│   └── validacao               # Regras de validação de negócio
├── infraestrutura
│   └── persistencia            # Repositórios JDBC (JdbcTemplate + PostgreSQL)
└── apresentacao
    ├── web                     # Controllers Spring MVC e tratamento global de erros
    ├── formulario              # Objetos de formulário com Bean Validation
    └── sessao                  # Carrinho mantido na sessão HTTP (um por loja)
```

Princípios aplicados:
- **S**: cada classe tem uma responsabilidade (controller, serviço, repositório, validador).
- **O/L**: novos repositórios ou casos de uso podem ser adicionados sem alterar os existentes.
- **I**: portas pequenas e específicas por agregado.
- **D**: serviços dependem de interfaces (`porta.saida`), nunca de implementações JDBC.

Cada repositório expõe métodos separados de **inserir**, **atualizar**, **excluir** e **listar**.

---

## 🗄️ Banco de dados (PostgreSQL)

Os scripts estão na pasta `banco/`:

| Arquivo | Conteúdo |
|---------|----------|
| `01_criar_banco.sql` | Cria o banco `gestaofarmacia` |
| `02_criar_tabelas.sql` | Cria todas as tabelas, chaves e índices |
| `03_dados_iniciais.sql` | Carga inicial de exemplo (requer extensão `pgcrypto`) |
Se utilizar o DUMP na raiz do projeto "gestaofarmacia.sql" não precisa executar os script acima, exceto a criação do database.

Execução:

```bash
psql -U postgres -f banco/01_criar_banco.sql
psql -U postgres -d gestaofarmacia -f banco/02_criar_tabelas.sql
psql -U postgres -d gestaofarmacia -f banco/03_dados_iniciais.sql
```

Tabelas utilizadas pela aplicação: `farmacias`, `lojas`, `medicamentos`, `pedidos`, `itens_pedido`.
Tabelas de apoio (cadastros e históricos operacionais, ainda sem tela): `empresas`, `estoque_produtos`, `compras`, `vendas`, `historico_vendas`, `validades_produtos`, `usuarios`, `historico_acessos`, `caixa_entrada`, `historico_mensagens`.

---

## 🚀 Como executar

### Pré-requisitos
- JDK 21
- Maven 3.9+
- PostgreSQL 13+

### Configuração
A conexão é definida em `src/main/resources/application.properties` e pode ser sobrescrita por variáveis de ambiente:

| Variável | Padrão |
|----------|--------|
| `BANCO_URL` | `jdbc:postgresql://localhost:5432/gestaofarmacia` |
| `BANCO_USUARIO` | `postgres` |
| `BANCO_SENHA` | `postgres` |

### Execução

```bash
mvn spring-boot:run
```

Acesse: <http://localhost:8080>

### Testes e empacotamento

```bash
mvn test
mvn clean package
java -jar target/gestaofarmacia-1.0.0.jar
```

---

## 🧭 Principais rotas

| Rota | Descrição |
|------|-----------|
| `/` | Página inicial |
| `/admin` | Painel de administração |
| `/farmacias` | CRUD de farmácias |
| `/farmacias/{id}/medicamentos` | Catálogo da farmácia (CRUD de medicamentos) |
| `/lojas` | CRUD de lojas |
| `/lojas/{id}/painel` | Painel da loja |
| `/lojas/{id}/catalogo` | Catálogo e carrinho |
| `/lojas/{id}/pedidos` | Pedidos da loja |

---

## 📝 Observações
- A aplicação não possui autenticação; a escolha de farmácia ou loja é feita por seleção direta.
- A exclusão de farmácia ou medicamento já presente em pedidos é bloqueada para preservar o histórico.
