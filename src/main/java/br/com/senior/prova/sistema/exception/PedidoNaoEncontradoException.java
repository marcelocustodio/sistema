package br.com.senior.prova.sistema.exception;

public class PedidoNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PedidoNaoEncontradoException(Long id) {
        super("Pedido nao encontrado - ID : " + id);
    }

}