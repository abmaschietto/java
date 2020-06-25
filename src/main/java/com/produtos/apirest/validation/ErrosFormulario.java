package com.produtos.apirest.validation;

public class ErrosFormulario {

	private String nome;
	private String mensagem;
	public ErrosFormulario(String nome, String mensagem) {
		this.nome = nome;
		this.mensagem = mensagem;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	
	
}
