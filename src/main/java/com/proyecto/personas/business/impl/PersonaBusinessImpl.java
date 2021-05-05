package com.proyecto.personas.business.impl;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import com.proyecto.personas.business.IPersonaBusiness;
import com.proyecto.personas.model.Persona;
import com.proyecto.personas.repo.IPersonaRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonaBusinessImpl implements IPersonaBusiness{
	
	@Autowired
	private IPersonaRepo repo;

	@Override
	public Mono<ResponseEntity<Persona>> insert(Persona persona, final ServerHttpRequest req) {
		Mono<Persona> MoPersona = repo.insert(persona);
		return MoPersona.map(p -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(p.getId())))
				.contentType(MediaType.APPLICATION_JSON)
				.body(p));
	}

	@Override
	public Mono<ResponseEntity<Persona>> edit(Persona persona, String id) {
		Mono<Persona> MoPersonaReq = Mono.just(persona);
		Mono<Persona> MoPersonaDB = repo.findById(id);
		
		return MoPersonaDB
				.zipWith(MoPersonaReq,(db, p) -> {
					db.setId(id);
					db.setNombre(p.getNombre());
					db.setApellido(p.getApellido());
					db.setDireccion(p.getDireccion());
					db.setDni(p.getDni());
					db.setFoto(p.getFoto());
					db.setEstado(p.getEstado());

					return db;
				})
				.flatMap(p -> repo.save(p))//p -> repo.save(p)
				.map(pe -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(pe))
				.defaultIfEmpty(new ResponseEntity<Persona>(HttpStatus.NOT_FOUND));
	}
	
	@Override
	public Mono<ResponseEntity<Flux<Persona>>> list() {
		Flux<Persona> FxPersona = repo.findAll();
		return Mono.just(ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(FxPersona));
	}
	
	@Override
	public Mono<ResponseEntity<Persona>> listById(String id) {
		Mono<Persona> MoPersona = repo.findById(id);
		return MoPersona.map(p -> ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@Override
	public Mono<ResponseEntity<Void>> delete(String id) {
		Mono<Persona> MoPersona = repo.findById(id);
		return MoPersona.flatMap(p -> {
			return repo.deleteById(p.getId())
					.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
		})
				.defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}
	
}
