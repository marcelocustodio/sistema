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

import br.com.senior.prova.sistema.dto.PedidoAtualizacaoDTO;
import br.com.senior.prova.sistema.dto.PedidoDTO;
import br.com.senior.prova.sistema.exception.PedidoNaoEncontradoException;
import br.com.senior.prova.sistema.modelo.Pedido;
import br.com.senior.prova.sistema.modelo.ProdutoPedido;
import br.com.senior.prova.sistema.repository.PedidoRepository;
import br.com.senior.prova.sistema.repository.ProdutoPedidoRepository;
import br.com.senior.prova.sistema.repository.ProdutoRepository;

@RestController
@RequestMapping("/api/v1")
public class PedidoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ProdutoPedidoRepository produtoPedidoRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@GetMapping("/pedidos")
	public List<Pedido> listarPedidos() {
		return (List<Pedido>) pedidoRepository.findAll();
	}

	@GetMapping("/pedidos/{id}")
	public Pedido procurarPedido(@PathVariable(value = "id") Long id) {

		Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new PedidoNaoEncontradoException(id));

		return pedido;
	}

	@GetMapping("/pedidos/valorTotalAbaixoDe/{valorTotal}")
	public List<Pedido> listarValorTotalAbaixoDe(@PathVariable(value = "valorTotal") double valorTotal) {
		return (List<Pedido>) pedidoRepository.findByValorTotalLessThan(valorTotal);
	}

	@PostMapping("/pedidos")
	public Pedido salvarPedido(@RequestBody PedidoDTO pedidoDTO) {

		Pedido pedido = new Pedido();

		UUID uuid = UUID.randomUUID();
		String uuidGerado = uuid.toString();

		pedido.setUuid(uuidGerado);
		pedido.setCliente(pedidoDTO.getCliente());
		pedido.setSituacao("aberto");

		return pedidoRepository.save(pedido);
	}

	@PutMapping("/pedidos/{id}")
	public Pedido atualizarPedido(@PathVariable(value = "id") Long id,
			@RequestBody PedidoAtualizacaoDTO pedidoAtualizacaoDTO) {

		Optional<Pedido> pedido = pedidoRepository.findById(id);

		pedido.get().setCliente(pedidoAtualizacaoDTO.getCliente());
		pedido.get().setSituacao(pedidoAtualizacaoDTO.getSituacao());
		pedido.get().setValorTotal(pedidoAtualizacaoDTO.getValorTotal());

		return pedidoRepository.save(pedido.get());
	}

	@DeleteMapping("/pedidos/{id}")
	public Map<String, Boolean> excluirPedido(@PathVariable(value = "id") Long id) {

		Optional<Pedido> pedido = pedidoRepository.findById(id);
		Map<String, Boolean> resposta = new HashMap<>();

		List<ProdutoPedido> produtoPedido = produtoPedidoRepository.findByPedido_id(pedido.get().getId());

		produtoPedidoRepository.deleteAll(produtoPedido);

		produtoRepository.deleteById(pedido.get().getId());
		resposta.put("pedidoExcluido", Boolean.TRUE);

		return resposta;
	}

	double novoValorTotal = 0d;

	@GetMapping("/pedidos/aplicarDescontoA/{id}/{desconto}")
	public Pedido aplicarDesconto(@PathVariable(value = "id") Long id,
			@PathVariable(value = "desconto") double desconto) {

		Optional<Pedido> pedido = pedidoRepository.findById(id);
		if (pedido.isEmpty() == false && pedido.get().getSituacao().equals("aberto")) {

			List<ProdutoPedido> produtoPedido = produtoPedidoRepository.findByPedido_id(id);

			produtoPedido.forEach(pp -> System.out.println("Pedido: " + pp.getPedido().getId() + " " + "Situação: "
					+ pp.getPedido().getSituacao() + " " + "Produto: " + pp.getProduto().getTitulo()));

			this.novoValorTotal = 0d;
			produtoPedido.forEach(pp -> {
				if (pp.getProduto().getTipo().equals("produto")) {
					double novoPreco = pp.getProduto().getPreco() * ((100 - desconto) / 100);
					pp.getProduto().setPreco(novoPreco);
					System.out.println(
							"Produto ID: " + pp.getProduto().getId() + " novo preço: " + pp.getProduto().getPreco());
					novoValorTotal += novoPreco;
				} else {
					novoValorTotal += pp.getProduto().getPreco();
				}
			});

			pedido.get().setValorTotal(novoValorTotal);
			pedidoRepository.save(pedido.get());

		}

		return pedido.get();
	}


}