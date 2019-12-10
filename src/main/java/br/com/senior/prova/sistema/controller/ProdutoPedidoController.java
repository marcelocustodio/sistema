package br.com.senior.prova.sistema.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.senior.prova.sistema.dto.ProdutoPedidoDTO;
import br.com.senior.prova.sistema.modelo.Pedido;
import br.com.senior.prova.sistema.modelo.Produto;
import br.com.senior.prova.sistema.modelo.ProdutoPedido;
import br.com.senior.prova.sistema.repository.PedidoRepository;
import br.com.senior.prova.sistema.repository.ProdutoPedidoRepository;
import br.com.senior.prova.sistema.repository.ProdutoRepository;

@RestController
@RequestMapping("/api/v1")
public class ProdutoPedidoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ProdutoPedidoRepository produtoPedidoRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@GetMapping("/itensdevendas")
	public List<ProdutoPedido> listarItensDeVendas() {
		return (List<ProdutoPedido>) produtoPedidoRepository.findAll();
	}

	@PostMapping("/itensdevendas")
	public ProdutoPedido salvarProdutoPedido(@RequestBody ProdutoPedidoDTO produtoPedidoDTO) {

	    ProdutoPedido prodPedido = new ProdutoPedido();

		UUID uuid = UUID.randomUUID();
		String uuidGerado = uuid.toString();
		prodPedido.setUuid(uuidGerado);
	    
	    System.out.println("Primeiro ID: " + produtoPedidoDTO.getProduto());
	    Produto produto = new Produto();
	    produto = produtoRepository.findById(produtoPedidoDTO.getProduto()).get();
	    
	    if (produto.isAtivo() == false) return null;
	    
	    prodPedido.setProduto(produto);
	    
	    System.out.println("Segundo ID: " + produtoPedidoDTO.getPedido());
	    Pedido pedido = new Pedido();
	    pedido = pedidoRepository.findById(produtoPedidoDTO.getPedido()).get();
	    prodPedido.setPedido(pedido);
	    
	    double novoValor = pedido.getValorTotal();
	    novoValor += produto.getPreco();
	    pedido.setValorTotal(novoValor);
	    pedidoRepository.save(pedido);
	    
	    return produtoPedidoRepository.save(prodPedido);
	}
	
	@PutMapping("/itensdevendas/{id}")
	public ProdutoPedido atualizarProdutoPedido(
			@PathVariable(value = "id") Long id, 
			@RequestBody ProdutoPedidoDTO produtoPedidoDTO) {

		Optional<ProdutoPedido> produtoPedido = produtoPedidoRepository.findById(id);

		produtoPedido.get().setProduto(produtoPedido.get().getProduto());
		produtoPedido.get().setPedido(produtoPedido.get().getPedido());

		return produtoPedidoRepository.save(produtoPedido.get());
	}
	
	@DeleteMapping("/itensdevendas/{id}")
	public Map<String, Boolean> excluirProdutoPedido(@PathVariable(value = "id") Long id) {

		Optional<ProdutoPedido> produtoPedido = produtoPedidoRepository.findById(id);
		Map<String, Boolean> resposta = new HashMap<>();

		if (produtoPedido.isEmpty() == false ) {
			resposta.put("produtoPedidoExcluido", Boolean.FALSE);
		} else {
			produtoRepository.deleteById(produtoPedido.get().getId());
			resposta.put("produtoPedidoExcluido", Boolean.TRUE);
		}

		return resposta;
	}
	

}