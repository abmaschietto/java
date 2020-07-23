package com.produtos.apirest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.produtos.apirest.models.Userform;
import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.repository.UserRepository;
import com.produtos.apirest.security.TokenDTO;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	TokenService tokenService;
	
	@PostMapping(value = "/user")
	public ResponseEntity<?> cadastrar(@RequestBody Userform user){
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String senha = encoder.encode(user.getSenha());
		Usuario usuario = new Usuario(user.getNome(), senha);
		 userRepo.save(usuario);
		return ResponseEntity.ok("Ok");
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody Userform user){
		UsernamePasswordAuthenticationToken dadosLogin = user.converter();
		
			try {
				Authentication authenticated = authManager.authenticate(dadosLogin);
				String token = tokenService.gerarToken(authenticated);
				return ResponseEntity.ok(new TokenDTO(token, "Bearer"));	
			} catch (Exception e) {}
		return ResponseEntity.notFound().build();
		
	}

}
