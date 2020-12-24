package com.fortalezasec.firewarning.resources.Errors;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import com.fortalezasec.firewarning.services.Errors.CNPJInvalidoException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	// @ExceptionHandler(DataIntegrityException.class)
	// public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {

	// 	StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
	// 			"Integridade de dados", e.getMessage(), request.getRequestURI());
	// 	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	// }

}