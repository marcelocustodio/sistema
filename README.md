# Sistema

## Configuração ambiente

- criar base "senior" na porta padrão do Postgres
- criar usuário "postgres" com senha "123"
- use o verbo adequado para cada teste a seguir.
- os testes unitários estão na pasta padrão de testes do Spring

## CRUD Produtos

- Criar um produto: 
-- URL: http://localhost:8081/api/v1/produtos
-- Dados:
{
  "titulo":"notebook",
  "tipo":"produto",
  "preco":4500
}

- Listar produtos:
-- URL: http://localhost:8081/api/v1/produtos

- Atualizar produtos:
-- URL: http://localhost:8081/api/v1/produtos/6  (supondo o ID do produto sendo 6)
-- Dados:
{
	"titulo":"notebook Asus",
  "tipo":"produto",
  "preco":6000
}

## CRUD Pedidos

- Criar um pedido:
-- URL: http://localhost:8081/api/v1/pedidos/
-- Dados:
{
	"cliente":"Rodrigo Custodio"
}

- Atualizar pedido:
-- URL: http://localhost:8081/api/v1/pedidos/1  (supondo id do pedido como 1)

- Listar pedidos:
-- URL: http://localhost:8081/api/v1/pedidos/

- Excluir pedidos:
-- URL: http://localhost:8081/api/v1/pedidos/1

Antes de criar um item de pedido, é necessário criar o pedido.

## CRUD ProdutoPedido

- Criar um item:
-- URL: http://localhost:8081/api/v1/itensdevendas
-- Dados:
{
	"produto":3,
	"pedido":1
}

- Listar itens de pedidos:
-- URL: http://localhost:8081/api/v1/itensdevendas

