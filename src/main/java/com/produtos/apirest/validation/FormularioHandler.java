package com.produtos.apirest.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FormularioHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErrosFormulario> handler(MethodArgumentNotValidException exception){
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		List<ErrosFormulario> listaErros = new ArrayList<>();
		fieldErrors.forEach(erros ->{
			String message = messageSource.getMessage(erros, LocaleContextHolder.getLocale());
			ErrosFormulario erro = new ErrosFormulario(erros.getField(), message);
			listaErros.add(erro);
		});
		
		return listaErros;
		
	}

}
