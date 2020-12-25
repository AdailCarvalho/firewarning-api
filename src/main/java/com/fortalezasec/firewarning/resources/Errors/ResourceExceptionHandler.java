package com.fortalezasec.firewarning.resources.Errors;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import com.fortalezasec.firewarning.services.Errors.CNPJInvalidoException;
import com.fortalezasec.firewarning.services.Errors.UserAlreadyExistsException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * ResourceExceptionHandler
 */
@ControllerAdvice
public class ResourceExceptionHandler {

	// Overriding the default exception
	@ExceptionHandler(CNPJInvalidoException.class)
	public ResponseEntity<StandardError> objectNotFound(CNPJInvalidoException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				"CNPJ Inválido", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				"Entidade não encontrada", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<StandardError> entityNotFound(UserAlreadyExistsException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.CONFLICT.value(),
				"Email informado já está cadastrado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(UsernameNotFoundException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				"Email informado já está cadastrado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<StandardError> entityNotFound(ConstraintViolationException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Erro de validação encontrado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<StandardError> entityNotFound(IllegalArgumentException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Erro de validação encontrado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

}