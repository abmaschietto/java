package com.produtos.apirest.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
	
	@GetMapping(value = "/hello")
	public String hello() {
		return "Olá teste";
	}
	
	@GetMapping(value = "/produtos")
	@ApiOperation(value = "Retorna a lista de todas pizzas cadastradas")
	public ResponseEntity<List<Produtos>> listaPizzas(){
		List<Produtos> findAll = produtoRepo.findAll();
		return ResponseEntity.ok(findAll);
	}

	@GetMapping(value = "/produtos/{id}")
	@ApiOperation(value = "Se for passado uma id retorna um produto específico")
	@Cacheable(value = "Busca por id")
	public ResponseEntity<List<Produtos>> produtoUnico(@PathVariable(name = "id", required = true) Long id)
			throws Exception {
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

	@GetMapping(value = "/busca/{nome}")
	@ApiOperation(value = "Retorna uma pizza por nome")
	@Cacheable(value = "Busca por nome")
	public ResponseEntity<Produtos> produtoUnicoNome(@PathVariable(name = "nome", required = true) String nome) {
		Produtos produto = produtoRepo.findByNome(nome);
		return ResponseEntity.ok(produto);
	}

	@PostMapping(value = "/produtos")
	@ApiOperation(value = "Cadastra uma Pizza, não passe um id ele é gerado automático")
	public Produtos gravaProduto(@RequestBody @Valid Produtos produto) {
		return produtoRepo.save(produto);
	}

	@DeleteMapping(value = "/produtos/{id}")
	@ApiOperation(value = "Deleta uma Pizza passando o id desejado a ser deletado")
	@CacheEvict(value = {"Busca por nome", "Busca por id"}, allEntries = true)
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
	@CacheEvict(value = {"Busca por nome", "Busca por id"}, allEntries = true)
	public ResponseEntity<Produtos> atualiza(@RequestBody @Valid Produtos produto) {
		Optional<Produtos> findById = produtoRepo.findById(produto.getId());
		if (findById.isPresent()) {
			Produtos save = produtoRepo.save(produto);
			return ResponseEntity.ok(save);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

}
