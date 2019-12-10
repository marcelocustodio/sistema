package br.com.senior.prova.sistema.dto;

public class ProdutoPedidoDTO {

    private Long produto;
    private Long pedido;

    public Long getProduto() {
		return produto;
	}
	public void setProduto(Long produto) {
		this.produto = produto;
	}
	public Long getPedido() {
		return pedido;
	}
	public void setPedido(Long pedido) {
		this.pedido = pedido;
	}
    
}