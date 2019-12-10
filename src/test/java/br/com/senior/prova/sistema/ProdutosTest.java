package br.com.senior.prova.sistema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.senior.prova.sistema.modelo.Produto;

public class ProdutosTest extends AbstractTest {
	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void listarProdutos() throws Exception {
		String uri = "/api/v1/produtos";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String conteudo = mvcResult.getResponse().getContentAsString();
		Produto[] listaDeProdutos = super.mapFromJson(conteudo, Produto[].class);
		assertTrue(listaDeProdutos.length > 0);
	}

	@Test
	public void criarProduto() throws Exception {
		UUID uuid = UUID.randomUUID();
		String uuidGerado = uuid.toString();
		String uri = "/api/v1/produtos";
		Produto produto = new Produto();
		produto.setAtivo(true);
		produto.setUuid(uuidGerado);
		produto.setPreco(100d);
		produto.setTipo("servico");
		produto.setTitulo("Engraxar sapatos");
		String inputJson = super.mapToJson(produto);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

	}

	/*
	@Test
	public void updateProduct() throws Exception {
		String uri = "/api/v1/produtos/2";
		Produto produto = new Produto();
		produto.setTitulo("Limao");
		String inputJson = super.mapToJson(produto);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Produto is updated successsfully");
	}*/

	@Test
	public void excluirProduto() throws Exception {
		String uri = "/api/v1/produtos/2";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "{\"produtoExcluido\":true}");
	}

}