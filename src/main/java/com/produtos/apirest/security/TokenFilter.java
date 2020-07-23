package com.produtos.apirest.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.produtos.apirest.controllers.TokenService;
import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.repository.UserRepository;

public class TokenFilter extends OncePerRequestFilter{
	
	private TokenService tokenService;
	private UserRepository userRepo;
	
	
	public TokenFilter() {
	}
	

	public TokenFilter(TokenService tokenService, UserRepository userRepo) {
		this.tokenService = tokenService;
		this.userRepo = userRepo;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = pegaToken(request);
		boolean valid = tokenService.isValido(token);
		if(valid) {
			Long id = tokenService.pegaId(token);
			Usuario usuario = userRepo.findById(id).get();
			UsernamePasswordAuthenticationToken dados = new UsernamePasswordAuthenticationToken(usuario.getNome(), null, usuario.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(dados);
		}
		
		filterChain.doFilter(request, response);
	}

	private String pegaToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}else {
			
			return token.substring(7, token.length());
		}
	}
	
	

}
