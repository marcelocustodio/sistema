package br.com.senior.prova.sistema.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.senior.prova.sistema.modelo.Pedido;

public interface PedidoRepository extends CrudRepository<Pedido, Long> {

	List<Pedido> findByValorTotalLessThan(double valorTotal);
	
	Pedido findByuuid(String uuid);
	
}