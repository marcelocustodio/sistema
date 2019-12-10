package br.com.senior.prova.sistema.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.senior.prova.sistema.modelo.ProdutoPedido;

public interface ProdutoPedidoRepository extends CrudRepository<ProdutoPedido, Long> {

	List<ProdutoPedido> findByProduto_id(Long produto_id);
	
	List<ProdutoPedido> findByPedido_id(Long pedido_id);
}