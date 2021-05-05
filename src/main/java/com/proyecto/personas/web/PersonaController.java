package com.proyecto.personas.web;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import com.proyecto.personas.business.IPersonaBusiness;
import com.proyecto.personas.model.Persona;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/personas")
public class PersonaController {

	//@Autowired
	private IPersonaBusiness business;
	
	public PersonaController( final IPersonaBusiness business) {
		this.business = business;
	}

	@GetMapping
	public Mono<ResponseEntity<Flux<Persona>>> list(){
		return business.list();	
	}
	
	@PostMapping
	public Mono<ResponseEntity<Persona>> insert(@RequestBody Persona persona, final ServerHttpRequest req){
		return business.insert(persona, req);
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<Persona>> edit(@RequestBody Persona persona, @PathVariable("id") String id){
		return business.edit(persona, id);		
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Persona>> listById(@PathVariable("id") String id){
		return business.listById(id);
	}
	
	/*
	@PatchMapping("eliminar/{id}")
	public Mono<ResponseEntity<Persona>> eliminarLogico(@RequestBody Persona persona, @PathVariable("id") String id){
		Mono<Persona> monoPersona = Mono.just(persona);
		Mono<Persona> monoDB = service.listById(id);
		
		return monoDB
				.zipWith(monoPersona, (db, p) -> {
					db.setEstado("Inactivo");
					return db;
				})
				.flatMap(p -> service.edit(p))
				.map(pe -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(pe))
				.defaultIfEmpty(new ResponseEntity<Persona>(HttpStatus.NOT_FOUND));
	}
	*/
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> eliminar(@PathVariable("id") String id){
		return business.delete(id);
	}
	
}
