package br.com.senior.prova.sistema;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.senior.prova.sistema.modelo.Produto;
import br.com.senior.prova.sistema.repository.ProdutoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SistemaApplicationTests {

	@Autowired
	ProdutoRepository produtoRepository;
	
	@Test
	public void contextLoads() {
		
		
		 List<Produto> produtos = (List<Produto>) produtoRepository.findAll(); 
         assertThat(produtos).isNotNull().isNotEmpty();
		
	}
	
}
