# 📖 Manual do Usuário — Gestão Farmácia

Este manual explica, passo a passo e sem termos técnicos, como usar o sistema e quais regras ele aplica em cada operação.

---

## 1. Visão geral

O Gestão Farmácia conecta três "mundos":

| Área | Para que serve | Quem usa |
|------|----------------|----------|
| **Administração do sistema** | Cadastrar as **farmácias** e as **lojas** | Quem organiza o sistema |
| **Área da farmácia** | Manter o **catálogo de medicamentos** (preço, estoque, validade) | Responsável pela farmácia |
| **Área da loja** | Consultar o catálogo, montar o **carrinho** e fazer **pedidos** | Responsável pela loja |

**Fluxo geral:**

```
1. Cadastrar Farmácia  →  2. Cadastrar Medicamentos na farmácia
        ↓
3. Cadastrar Loja  →  4. Loja navega no catálogo e monta o carrinho
        ↓
5. Loja finaliza o pedido  →  6. Estoque do medicamento é reduzido
```

> ℹ️ O sistema **não tem login**. Você escolhe diretamente a farmácia ou a loja com a qual quer trabalhar.

---

## 2. Navegação

- **Menu superior** (em todas as telas): **Administração**, **Farmácias** e **Lojas**. O nome **Gestão Farmácia** (canto esquerdo) volta à página inicial.
- **Página inicial:** três cartões (Administração do sistema, Área da farmácia, Área da loja). Clique no que deseja.
- **Trilha de navegação** (ex.: *Farmácias / Farmácia 1*): aparece no alto das telas internas e permite voltar um nível.
- **Mensagens:** após cada ação aparece uma faixa **verde** (sucesso) ou **vermelha** (erro/regra violada) no topo da tela.
- **Celular:** as tabelas viram listas, com o nome de cada campo ao lado do valor.

| Quero… | Caminho |
|--------|---------|
| Cadastrar/editar/excluir farmácias | **Administração → Gerenciar farmácias** |
| Cadastrar/editar/excluir lojas | **Administração → Gerenciar lojas** |
| Gerenciar medicamentos de uma farmácia | **Farmácias →** clique na farmácia (ou **Farmácias** na lista → botão *Medicamentos*) |
| Fazer um pedido | **Lojas →** clique na loja → **Navegar pelo catálogo** |
| Ver pedidos feitos | **Lojas →** clique na loja → **Ver pedidos** |

---

## 3. Farmácias

**Caminho:** Administração → Gerenciar farmácias.

A lista mostra nome e localização de cada farmácia, com os botões **Medicamentos**, **Editar** e **Excluir**.

### Cadastrar
1. Clique em **Nova farmácia**.
2. Preencha:
   - **Nome** — *obrigatório*, até 100 caracteres.
   - **Localização** — *opcional*, até 150 caracteres.
3. Clique em **Salvar**.

### Editar
Clique em **Editar**, altere os campos e clique em **Salvar**.

### Excluir
Clique em **Excluir** e confirme.

**Regras importantes:**
- Ao excluir uma farmácia, **todos os medicamentos dela também são excluídos**.
- Se **algum medicamento dessa farmácia já tiver sido vendido em um pedido**, a exclusão é **bloqueada** e o sistema avisa. Isso preserva o histórico de pedidos.

---

## 4. Lojas

**Caminho:** Administração → Gerenciar lojas.

### Cadastrar
1. Clique em **Nova loja**.
2. Preencha **Nome** (obrigatório, até 100 caracteres) e **Localização** (obrigatória, até 150 caracteres).
3. Clique em **Salvar**.

### Editar e excluir
Funcionam como nas farmácias. O botão **Abrir** leva ao painel da loja.

**Regra:** ao excluir uma loja, **todos os pedidos dela também são apagados**. Confirme com atenção.

---

## 5. Medicamentos (catálogo da farmácia)

**Caminho:** Farmácias → clique na farmácia desejada.

A tela mostra o catálogo com **código, nome, tipo, estoque, preço e validade**. Medicamentos com validade passada recebem a etiqueta vermelha **Vencido**.

### Cadastrar
1. Clique em **Novo medicamento**.
2. Preencha todos os campos (**todos são obrigatórios**):

| Campo | Regra |
|-------|-------|
| Nome | Até 100 caracteres |
| Tipo | Ex.: Comprimido, Injeção (até 50) |
| Composição | Até 150 caracteres |
| Descrição | Até 255 caracteres |
| Quantidade em estoque | Número inteiro, **0 ou mais** |
| Preço do fornecedor (R$) | **0 ou mais**, com até 2 casas decimais |
| Data de fabricação | Data válida |
| Data de validade | Data válida e **não anterior à fabricação** |

3. Clique em **Salvar**.

Se algum campo estiver errado, a mensagem em vermelho aparece **abaixo do campo**, e nada é salvo até você corrigir.

### Editar
Clique em **Editar**, altere e salve. Use isso, por exemplo, para **repor o estoque** ou ajustar o preço.

### Excluir
Clique em **Excluir** e confirme. Se o medicamento **já consta em algum pedido**, a exclusão é bloqueada. Nesse caso, edite o estoque para 0.

> 💡 O **preço do fornecedor** é o valor mínimo pelo qual a loja pode vender o medicamento (veja a seção 6).

---

## 6. Área da loja — fazendo pedidos

**Caminho:** Lojas → clique na loja. O painel oferece dois caminhos: **Navegar pelo catálogo** e **Ver pedidos**.

### 6.1 Encontrando medicamentos

Na tela **Catálogo de medicamentos**:

- **Filtro por farmácia:** escolha a farmácia na lista e clique em **Filtrar**. Ao abrir a tela, a primeira farmácia já vem selecionada.
- **Pesquisa:** digite no campo de pesquisa e clique em **Filtrar**.
  - Somente números → busca pelo **código** do medicamento.
  - Texto → busca por **parte do nome** (não diferencia maiúsculas de minúsculas).
  - **A pesquisa procura em todas as farmácias** e tem prioridade sobre o filtro de farmácia. Para voltar à listagem por farmácia, deixe a pesquisa vazia.
- Clique no **nome** do medicamento para ver todos os detalhes (farmácia, composição, validade etc.).

### 6.2 Entendendo a coluna "Disponível"

**Disponível = estoque do medicamento − quantidade que já está no seu carrinho.**

Exemplo: o estoque é 40 e você já colocou 10 no carrinho → a tela mostra **30**. Se chegar a 0, aparece **Sem estoque** e o formulário de adicionar some.

> ⚠️ O estoque **só diminui de verdade quando o pedido é finalizado**. Colocar no carrinho apenas "reserva" a quantidade para você, sem tirar de outras lojas.

### 6.3 Adicionando ao carrinho

Em cada linha há dois campos e um botão:

1. **Quantidade** — mínimo 1 e no máximo o valor de "Disponível".
2. **Preço de venda** — já vem preenchido com o preço do fornecedor.
3. Clique em **Adicionar**.

**Regras:**
- O **preço de venda não pode ser menor** que o preço do fornecedor. Pode ser **igual ou maior**.
- A quantidade (somada ao que já está no carrinho) **não pode passar do estoque**.
- Se você adicionar de novo um medicamento que **já está no carrinho**, as **quantidades são somadas** e o **preço de venda do primeiro cadastro é mantido**. Para mudar o preço, remova o item e adicione novamente.
- Medicamentos **vencidos podem ser adicionados**: o sistema apenas sinaliza com a etiqueta **Vencido**. A decisão é sua.

### 6.4 Gerenciando o carrinho

O carrinho aparece ao lado da lista (no celular, abaixo dela) e mostra cada item, o subtotal e o **Total**.

- **Alterar quantidade:** digite a **nova quantidade total** do item e clique em **Alterar**. Ela *substitui* a anterior (não soma). Deve ser de 1 até o estoque.
- **Remover:** clique em **Remover** para tirar o item.
- Cada **loja tem seu próprio carrinho**. O carrinho fica guardado na sessão do navegador: se você fechar o navegador ou a sessão expirar, ele é perdido.

### 6.5 Finalizando o pedido

1. Confira os itens e o total.
2. Clique em **Finalizar pedido**.

O que acontece:
- Um **pedido** é criado com todos os itens e o preço de venda de cada um.
- O **estoque de cada medicamento é reduzido** na quantidade pedida.
- O **carrinho é esvaziado** e você vê o detalhe do pedido com a mensagem de sucesso.

**Regras:**
- Carrinho **vazio** não pode ser finalizado.
- Tudo é feito **de uma vez só**: se algum item não puder ser atendido, **nada é gravado** e o estoque não muda. Exemplo: outra loja comprou antes de você e o estoque acabou. O sistema avisa qual medicamento ficou sem estoque; ajuste o carrinho e finalize de novo.

---

## 7. Consultando pedidos

**Caminho:** Lojas → clique na loja → **Ver pedidos**.

### Lista de pedidos
Mostra, do mais recente para o mais antigo: **número do pedido, data e hora, quantidade de itens e total**. Clique em **Detalhes** para abrir um pedido.

### Detalhe do pedido
Lista cada item com medicamento, **preço de venda**, **quantidade**, **subtotal** e **situação**:

| Situação | Significado |
|----------|-------------|
| 🟢 **Estoque suficiente** | O item foi pedido em quantidade **maior que 10** unidades |
| 🟡 **Reabastecer estoque** | O item foi pedido em quantidade de **10 unidades ou menos**; é um alerta para repor |

O botão **Ver item** mostra os dados do item e leva ao medicamento no catálogo.

> ℹ️ Pedidos finalizados **não podem ser editados nem cancelados** pelas telas. Eles formam o histórico da loja.

---

## 8. Exemplo prático completo

Com os dados iniciais do sistema:

1. Abra **Farmácias → Farmácia 1**. Você verá **Crocin** (código 1, estoque 40, R$ 20,00) e **Anacin** (código 2, estoque 80, R$ 30,00).
2. Vá em **Lojas → Loja 1 → Navegar pelo catálogo**.
3. Na linha do **Crocin**, informe quantidade **12** e preço de venda **R$ 25,00** → **Adicionar**. A coluna "Disponível" passa para **28**.
4. Na linha do **Anacin**, informe quantidade **5** e preço **R$ 30,00** → **Adicionar**.
5. No carrinho: Crocin 12 × R$ 25,00 = R$ 300,00; Anacin 5 × R$ 30,00 = R$ 150,00; **Total R$ 450,00**.
6. Tente adicionar Crocin com preço **R$ 15,00**: o sistema recusa, pois é menor que o preço do fornecedor (R$ 20,00).
7. Clique em **Finalizar pedido**. O pedido é criado e o estoque passa a **Crocin 28** e **Anacin 75**.
8. No detalhe do pedido, o Crocin (12 un.) aparece como **Estoque suficiente** e o Anacin (5 un.) como **Reabastecer estoque**.
9. Volte à **Farmácia 1** e confira o novo estoque. Se quiser repor, use **Editar** no medicamento.

---

## 9. Mensagens comuns e o que fazer

| Mensagem | Causa | O que fazer |
|----------|-------|-------------|
| "O nome … é obrigatório." | Campo obrigatório vazio | Preencha o campo |
| "A data de validade não pode ser anterior à data de fabricação." | Datas invertidas | Corrija as datas |
| "O preço de venda não pode ser menor que o preço do fornecedor." | Preço de venda abaixo do mínimo | Informe um valor igual ou maior |
| "Quantidade solicitada maior que o estoque disponível (N)." | Pedido maior que o estoque | Reduza a quantidade ou reponha o estoque na farmácia |
| "Selecione ao menos 1 unidade." | Quantidade zero ou negativa | Informe 1 ou mais |
| "O carrinho está vazio." | Finalizar sem itens | Adicione itens antes |
| "Estoque insuficiente para o medicamento X. Revise o carrinho." | Estoque acabou durante a compra | Ajuste a quantidade e finalize novamente |
| "Não é possível excluir: … vinculados a pedidos." | Registro já usado em pedidos | Mantenha o registro; para medicamento, zere o estoque |
| Página "Não encontrado" | Registro removido ou endereço incorreto | Volte ao início pelo menu |

---

## 10. Perguntas frequentes

**Posso desfazer um pedido?** Não pelas telas. Para corrigir o estoque, edite o medicamento na farmácia.

**Por que o estoque não mudou quando coloquei no carrinho?** Porque o estoque só é reduzido ao **finalizar** o pedido.

**Duas lojas podem comprar o mesmo medicamento ao mesmo tempo?** Sim, mas o estoque nunca fica negativo: quem finalizar depois, se faltar estoque, recebe o aviso e precisa ajustar o carrinho.

**Perdi meu carrinho. E agora?** O carrinho vive na sessão do navegador. Se a sessão expirou ou o sistema foi reiniciado, monte-o novamente.

**Como cadastro um medicamento sem estoque?** Informe quantidade **0**; ele aparece no catálogo como **Sem estoque** para as lojas.

---

## 11. Glossário

- **Preço do fornecedor:** valor cobrado pela farmácia; é o mínimo de venda da loja.
- **Preço de venda:** valor definido pela loja ao adicionar o item ao carrinho.
- **Disponível:** estoque menos o que já está no seu carrinho.
- **Carrinho:** lista temporária de itens, ainda não confirmados.
- **Pedido:** carrinho confirmado; gera histórico e reduz o estoque.
- **Vencido:** medicamento cuja data de validade já passou.
