package br.com.senior.prova.sistema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.senior.prova.sistema.modelo.Pedido;

public class PedidosTest extends AbstractTest {
	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void listarPedidos() throws Exception {
		String uri = "/api/v1/pedidos";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String conteudo = mvcResult.getResponse().getContentAsString();
		Pedido[] listaDePedidos = super.mapFromJson(conteudo, Pedido[].class);
		assertTrue(listaDePedidos.length > 0);
	}

	@Test
	public void criarPedido() throws Exception {
		UUID uuid = UUID.randomUUID();
		String uuidGerado = uuid.toString();
		String uri = "/api/v1/pedidos";
		Pedido pedido = new Pedido();
		pedido.setCliente("Marcelo Monteiro");
		pedido.setSituacao("aberto");
		pedido.setUuid(uuidGerado);
		String inputJson = super.mapToJson(pedido);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

	}

}