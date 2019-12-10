# Sistema

## Configuração ambiente

- criar base "senior" na porta padrão do Postgres
- criar usuário "postgres" com senha "123"

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
