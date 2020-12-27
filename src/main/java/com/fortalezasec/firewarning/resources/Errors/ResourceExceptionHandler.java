package com.fortalezasec.firewarning.resources.Errors;

import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.fortalezasec.firewarning.CustomExceptions.CNPJInvalidoException;
import com.fortalezasec.firewarning.CustomExceptions.TypeDoNotExistsException;
import com.fortalezasec.firewarning.CustomExceptions.UriMalFormadaException;
import com.fortalezasec.firewarning.CustomExceptions.UserAlreadyExistsException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.TransactionSystemException;
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

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "CNPJ Inválido",
				e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				"Entidade não encontrada", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<StandardError> userAlreadyExists(UserAlreadyExistsException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.CONFLICT.value(),
				"Email informado já está cadastrado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<StandardError> usernameNotFound(UsernameNotFoundException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				"Email informado já está cadastrado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<StandardError> constraintViolation(ConstraintViolationException e, HttpServletRequest request) {

		ValidationError err = new ValidationError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Erro de validação encontrado", e.getMessage(), request.getRequestURI());
		Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

		constraintViolations.stream().forEach(x -> {
			err.setError(x.getMessage());
		});

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	/**
	 * In some cases constraintValidation exceptions can't be handle because it's
	 * not propagated to that layer of code, so we must capture
	 * TransactionSystemException from a low layer.
	 */
	@ExceptionHandler(TransactionSystemException.class)
	public ResponseEntity<StandardError> transactionViolation(Exception e, HttpServletRequest request) {

		ValidationError err = new ValidationError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Erro de validação encontrado", e.getMessage(), request.getRequestURI());
		Throwable cause = ((TransactionSystemException) e).getRootCause();
		if (cause instanceof ConstraintViolationException) {
			Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) cause)
					.getConstraintViolations();

			constraintViolations.stream().forEach(x -> {
				err.setError(x.getMessage());
			});

		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<StandardError> illegalArgument(IllegalArgumentException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Erro de validação encontrado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(TypeDoNotExistsException.class)
	public ResponseEntity<StandardError> typeDoNotExists(TypeDoNotExistsException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"O tipo solicitado não foi encontrado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(UriMalFormadaException.class)
	public ResponseEntity<StandardError> typeDoNotExists(UriMalFormadaException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"UriMalFormadaException", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
}