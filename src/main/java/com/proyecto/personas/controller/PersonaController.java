package com.proyecto.personas.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.personas.model.Persona;
import com.proyecto.personas.service.IPersonaService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/personas")
public class PersonaController {

	@Autowired
	private IPersonaService service;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<Persona>>> listar(){
		Flux<Persona> fluxPersona = service.listar();
		
		return Mono.just(ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(fluxPersona));
	}
	
	@PostMapping
	public Mono<ResponseEntity<Persona>> registrar(@RequestBody Persona plato, final ServerHttpRequest req){

		return service.registrar(plato)
				.map(p -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(p.getId())))
					.contentType(MediaType.APPLICATION_JSON)
					.body(p)
					);
	}

	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<Persona>> modificar(@RequestBody Persona persona, @PathVariable("id") String id){
		Mono<Persona> monoPersona = Mono.just(persona);
		Mono<Persona> monoDB = service.listarPorId(id);
		
		return monoDB
			.zipWith(monoPersona, (db, p) -> {
				db.setId(id);
				db.setNombre(p.getNombre());
				db.setApellido(p.getApellido());
				db.setDni(p.getDni());
				db.setDireccion(p.getDireccion());
				db.setFoto(p.getFoto());
				db.setEstado(p.getEstado());
				return db;
			})
			.flatMap(service::modificar)
			.map(pe -> ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(pe))
			.defaultIfEmpty(new ResponseEntity<Persona>(HttpStatus.NOT_FOUND));
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Persona>> listarPorId(@PathVariable("id") String id){
		return service.listarPorId(id)
				.map(p -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(p)
						)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	
	@DeleteMapping("/id")
	public Mono<ResponseEntity<Void>> eliminar(@PathVariable("id") String id){
		return service.listarPorId(id)
				.flatMap(p -> {
					return service.eliminar(p.getId())
							.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NOT_FOUND)));
				})
				.defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}
	
}
