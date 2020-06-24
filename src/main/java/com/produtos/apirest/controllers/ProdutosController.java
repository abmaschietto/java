package com.produtos.apirest.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.produtos.apirest.models.Produtos;
import com.produtos.apirest.repository.ProdutoRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
@Api(value = "Api Rest de Pizzas")
@CrossOrigin(origins = "*")
public class ProdutosController {

	@Autowired
	ProdutoRepository produtoRepo;

	@GetMapping(value = "/produtos{id}")
	@ApiOperation(value = "Retorna uma lista de produtos, se for passado uma id retorna um produto específico")
	public ResponseEntity<List<Produtos>> produtoUnico(@PathVariable(name = "id", required = false) Long id)
			throws Exception {
		if (id == null) {
			List<Produtos> findAll = produtoRepo.findAll();
			return ResponseEntity.ok(findAll);
		} else {
			Optional<Produtos> produto = produtoRepo.findById(id);
			if (produto.isPresent()) {
				Produtos produtos = produto.get();
				List<Produtos> lista = new ArrayList<>();
				lista.add(produtos);
				return ResponseEntity.ok(lista);
			} else {
				return ResponseEntity.notFound().build();
			}
		}
	}

	@GetMapping(value = "/busca{nome}")
	@ApiOperation(value = "Retorna uma pizza por nome")
	public ResponseEntity<Produtos> produtoUnicoNome(@PathVariable(name = "nome", required = true) String nome) {
		Produtos produto = produtoRepo.findByNome(nome);
		return ResponseEntity.ok(produto);
	}

	@PostMapping(value = "/produtos")
	@ApiOperation(value = "Cadastra uma Pizza")
	public Produtos gravaProduto(@RequestBody Produtos produto) {
		return produtoRepo.save(produto);
	}

	@DeleteMapping(value = "/produtos/{id}")
	@ApiOperation(value = "Deleta uma Pizza passando o id desejado a ser deletado")
	public ResponseEntity<List<Produtos>> deletar(@PathVariable(name = "id", required = true) Long id) {
		Optional<Produtos> mocap = produtoRepo.findById(id);
		if (mocap.isPresent()) {
			produtoRepo.deleteById(id);
			List<Produtos> findAll = produtoRepo.findAll();
			return ResponseEntity.ok(findAll);
		} else
			return ResponseEntity.notFound().build();
	}

	@PutMapping(value = "/produtos")
	@ApiOperation(value = "Atualiza uma pizza")
	public ResponseEntity<Produtos> atualiza(@RequestBody Produtos produto) {
		Optional<Produtos> findById = produtoRepo.findById(produto.getId());
		if (findById.isPresent()) {
			Produtos save = produtoRepo.save(produto);
			return ResponseEntity.ok(save);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

}
