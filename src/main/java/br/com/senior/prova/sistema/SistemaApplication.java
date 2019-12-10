package br.com.senior.prova.sistema;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.senior.prova.sistema.modelo.Produto;
import br.com.senior.prova.sistema.repository.PedidoRepository;
import br.com.senior.prova.sistema.repository.ProdutoPedidoRepository;
import br.com.senior.prova.sistema.repository.ProdutoRepository;

@SpringBootApplication
public class SistemaApplication implements CommandLineRunner {
	
	@Autowired
	ProdutoRepository produtoRepository;

	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired
	ProdutoPedidoRepository produtoPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(SistemaApplication.class, args);
	}
	
	public String gerarUUID() {
		
		UUID uuid = UUID.randomUUID();
		String uuidGerado = uuid.toString();
		
		return uuidGerado;
	}
	
	@Override
	public void run(String... args) throws Exception {

		// criar produtos
		Produto produto1 = new Produto();
		produto1.setTitulo("cadeira plastica");
		produto1.setPreco(40.0d);
		produto1.setAtivo(true);
		produto1.setUuid(gerarUUID());
		produto1.setTipo("produto");

		Produto produto2 = new Produto();
		produto2.setTitulo("mesa");
		produto2.setPreco(35.0d);
		produto2.setAtivo(true);
		produto2.setUuid(gerarUUID());
		produto2.setTipo("produto");
		
		Produto produto3 = new Produto();
		produto3.setTitulo("mesa madeira");
		produto3.setPreco(73.0d);
		produto3.setAtivo(true);
		produto3.setUuid(gerarUUID());
		produto3.setTipo("produto");
		
		Produto produto4 = new Produto();
		produto4.setTitulo("ferro de passar");
		produto4.setPreco(95.2d);
		produto4.setAtivo(true);
		produto4.setUuid(gerarUUID());
		produto4.setTipo("produto");
		
		Produto produto5 = new Produto();
		produto5.setTitulo("engraxar sapato");
		produto5.setPreco(15d);
		produto5.setAtivo(true);
		produto5.setUuid(gerarUUID());
		produto5.setTipo("servico");

		this.produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3, produto4, produto5));

		this.produtoRepository.findByPrecoLessThan(39.0d)
				.forEach(p -> System.out.println("Preços abaixo de R$ 39,00: " + p.getTitulo()));

		
	}

}
