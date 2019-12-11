# Sistema (Prova Nível 2)

## Tecnologias usadas:
PostgreSQL, Java 8+, Maven, Spring, JPA, REST com JSON

## Configuração ambiente

- criar base "senior" na porta padrão do Postgres
- criar usuário "postgres" com senha "123"
- use o verbo adequado para cada teste a seguir.
- a porta usada foi a 8081, conforme application.properties
- execução:
mvc clean install
- de início, são criados 5 produtos.

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

- Excluir produtos:
-- URL: http://localhost:8081/api/v1/produtos/6

- Desativar produto (mudará o atributo de ativo para false). Verbo usado: GET.
-- URL: http://localhost:8081/api/v1/produtos/desativar/1


## CRUD Pedidos

- Criar um pedido:
-- URL: http://localhost:8081/api/v1/pedidos/
-- Dados:
{
	"cliente":"Rodrigo Custodio"
}

- Atualizar pedido:
-- URL: http://localhost:8081/api/v1/pedidos/1  (supondo id do pedido como 1)
-- Dados:
{
         "cliente":"Marcelo",
	 "situacao":"fechado",
	 "valorTotal":45
}

- Listar pedidos:
-- URL: http://localhost:8081/api/v1/pedidos/

- Excluir pedidos:
-- URL: http://localhost:8081/api/v1/pedidos/1

- Para aplicar um desconto de 10% a um pedido de id igual a 1:
URL: http://localhost:8081/api/v1/pedidos/aplicarDescontoA/1/10
aplicará 10% de desconto nos itens do tipo "produto" do pedido 1. Porém, antes é necessário criar-se os itens de pedido para o pedido igual a 1 como demonstrado na próxima seção.

Antes de criar um item de pedido (ProdutoPedido), é necessário criar o pedido.

## CRUD ProdutoPedido

- Criar um item:
-- URL: http://localhost:8081/api/v1/itensdevendas
-- Dados:
{
	"produto":3,
	"pedido":1
}

Quando se cria um produto-pedido (item de pedido), automaticamente o campo valorTotal do Pedido é atualizado com o preço do produto adicionado.

- Listar itens de pedidos:
-- URL: http://localhost:8081/api/v1/itensdevendas

- Atualizar um item:
-- URL: http://localhost:8081/api/v1/itensdevendas
-- Dados:
{
	"produto":2,
	"pedido":1
}

- Excluir um item:
-- URL: http://localhost:8081/api/v1/itensdevendas/1

## Os testes unitários estão na pasta padrão de testes do Spring

## Extras (não foram solicitados mas contemplei):

- Foi implementado um ControllerAdvice para para produto e pedido não encontrados
- bean validation
- filtros especiais de métodos customizados dos repositórios como:
** Para listar preços de produtos abaixo de R$ 39,00: http://localhost:8081/api/v1/produtos/abaixoDe/39
** Para listar mesas: http://localhost:8081/api/v1/produtos/listarMesas
 
