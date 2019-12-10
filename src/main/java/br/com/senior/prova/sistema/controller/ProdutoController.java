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

import br.com.senior.prova.sistema.dto.ProdutoDTO;
import br.com.senior.prova.sistema.exception.ProdutoNaoEncontradoException;
import br.com.senior.prova.sistema.modelo.Produto;
import br.com.senior.prova.sistema.modelo.ProdutoPedido;
import br.com.senior.prova.sistema.repository.ProdutoPedidoRepository;
import br.com.senior.prova.sistema.repository.ProdutoRepository;

@RestController
@RequestMapping("/api/v1")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ProdutoPedidoRepository produtoPedidoRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@GetMapping("/produtos")
	public List<Produto> listarProdutos() {
		return (List<Produto>) produtoRepository.findAll();
	}

	@GetMapping("/produtos/{id}")
	public Produto procurarProduto(@PathVariable(value = "id") Long id) {
		
		Produto produto = produtoRepository.findById(id)
				.orElseThrow(() -> new ProdutoNaoEncontradoException(id));
		
		return produto;
		
	}

	@GetMapping("/produtos/listarMesas")
	public List<Produto> listarMesas() {
		return (List<Produto>) produtoRepository.findByTituloContaining("mesa");
	}

	@GetMapping("/produtos/abaixoDe/{preco}")
	public List<Produto> listarPrecosAbaixoDe(@PathVariable(value = "preco") double preco) {
		return (List<Produto>) produtoRepository.findByPrecoLessThan(preco);
	}

	@PostMapping("/produtos")
	public Produto salvarProduto(@RequestBody ProdutoDTO produtoDTO) {

		Produto produto = new Produto();

		UUID uuid = UUID.randomUUID();
		String uuidGerado = uuid.toString();

		produto.setUuid(uuidGerado);
		produto.setPreco(produtoDTO.getPreco());
		produto.setTitulo(produtoDTO.getTitulo());
		produto.setTipo(produtoDTO.getTipo());
		produto.setAtivo(true);

		return produtoRepository.save(produto);
	}

	@PutMapping("/produtos/{id}")
	public Produto atualizarProduto(@PathVariable(value = "id") Long id, @RequestBody Produto produtoAtualizado) {

		Optional<Produto> produto = produtoRepository.findById(id);
		produto.get().setAtivo(produtoAtualizado.isAtivo());
		produto.get().setPreco(produtoAtualizado.getPreco());
		produto.get().setTitulo(produtoAtualizado.getTitulo());
		produto.get().setTipo(produtoAtualizado.getTipo());

		return produtoRepository.save(produto.get());

	}

	@GetMapping("/produtos/desativar/{id}")
	public Produto desativarProduto(@PathVariable(value = "id") Long id) {

		Optional<Produto> produto = produtoRepository.findById(id);
		produto.get().setAtivo(false);

		return produtoRepository.save(produto.get());
		
	}

	@DeleteMapping("/produtos/{id}")
	public Map<String, Boolean> excluirProduto(@PathVariable(value = "id") Long id) {

		Optional<Produto> produto = produtoRepository.findById(id);
		Map<String, Boolean> resposta = new HashMap<>();

		List<ProdutoPedido> produtoPedido = produtoPedidoRepository.findByProduto_id(produto.get().getId());
		
		if (produtoPedido != null && produtoPedido.size()>0 ) {
			resposta.put("produtoExcluido", Boolean.FALSE);
		} else {
			produtoRepository.delete(produto.get());
			resposta.put("produtoExcluido", Boolean.TRUE);
		}

		return resposta;
	}

}