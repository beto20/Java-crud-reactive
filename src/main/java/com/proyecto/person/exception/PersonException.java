package com.proyecto.person.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice(annotations = RestController.class)
public class PersonException {
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> notFoundException(Exception e, WebRequest req){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> badRequestException(Exception e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	@ExceptionHandler(NoContentException.class)
	public ResponseEntity<?> noContentException(Exception e){
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
	}

	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<?> conflictException(Exception e){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}
	
}