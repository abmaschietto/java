package com.produtos.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.produtos.apirest.models.Produtos;

public interface ProdutoRepository extends JpaRepository<Produtos, Long>{
	
	Produtos findByNome(String nome);

}
