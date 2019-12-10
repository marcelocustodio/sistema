package br.com.senior.prova.sistema.exception;

public class ProdutoNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProdutoNaoEncontradoException(Long id) {
        super("Produto nao encontrado - ID : " + id);
    }

}