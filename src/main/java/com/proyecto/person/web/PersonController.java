package com.proyecto.person.web;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.person.business.PersonBusiness;
import com.proyecto.person.dto.PersonDTO;
import com.proyecto.person.model.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class PersonController {

	private PersonBusiness business;

	@GetMapping	
	public Mono<ResponseEntity<Flux<Person>>> list(){
		return business.list();	
	}
	
	@PostMapping
	public Mono<ResponseEntity<Person>> insert(@Valid @RequestBody PersonDTO persondto, final ServerHttpRequest req){
		return business.insert(persondto, req);
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<Person>> edit(@Valid @RequestBody PersonDTO persondto, @PathVariable("id") String id){
		return business.edit(persondto, id);
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Person>> listById( @PathVariable("id") String id){
		return business.listById(id);
	}

	@ResponseStatus(code = HttpStatus.NO_CONTENT,reason = "NO HAY CONTENIDO")
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> eliminar(@PathVariable("id") String id){
		return business.delete(id);
	}
	
}
