package com.proyecto.person.web;

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

import com.proyecto.person.business.IPersonBusiness;
import com.proyecto.person.model.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/people")
public class PersonController {

	//@Autowired
	private IPersonBusiness business;
	
	public PersonController( final IPersonBusiness business) {
		this.business = business;
	}

	@GetMapping
	public Mono<ResponseEntity<Flux<Person>>> list(){
		return business.list();	
	}
	
	@PostMapping
	public Mono<ResponseEntity<Person>> insert(@RequestBody Person person, final ServerHttpRequest req){
		return business.insert(person, req);
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<Person>> edit(@RequestBody Person person, @PathVariable("id") String id){
		return business.edit(person, id);		
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Person>> listById(@PathVariable("id") String id){
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
