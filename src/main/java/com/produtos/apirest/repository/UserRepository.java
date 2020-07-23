package com.produtos.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.google.common.base.Optional;
import com.produtos.apirest.models.Usuario;

public interface UserRepository extends JpaRepository<Usuario, Long>{
	
	Optional<Usuario> findByNome (String nome);

}
