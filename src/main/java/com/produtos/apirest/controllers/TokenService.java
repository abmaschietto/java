package com.produtos.apirest.controllers;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.produtos.apirest.models.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	private String secret = "_8xHJ*@Lx%6O+j*$MRdH@414;%Dq5FLnD7wBCIt/=AE{?qw\"W>ikA29?/<ok5A~";
	
	
	public  String gerarToken(Authentication authenticated) {
		 Date data = new Date();
		 Date expirador = new Date(data.getTime() + 1800000);
		Usuario principal = (Usuario) authenticated.getPrincipal();
		return Jwts.builder()
				.setIssuer("API das Pizzas")
				.setSubject(principal.getId().toString())
				.setIssuedAt(data)
				.setExpiration(expirador)
				.signWith(SignatureAlgorithm.HS256, this.secret)
				.compact();
	}


	public boolean isValido(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
		}
		return false;
	}


	public Long pegaId(String token) {
		Claims body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		Long id = Long.parseLong(body.getSubject());
		return id;
	}

}
