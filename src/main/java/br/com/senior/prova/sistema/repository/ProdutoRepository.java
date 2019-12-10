package br.com.senior.prova.sistema.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.senior.prova.sistema.modelo.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Long> {

    List<Produto> findByTituloContaining(String titulo);

    List<Produto> findByPrecoLessThan(double preco);
    
    List<Produto> findByuuid(String uuid);
    
}