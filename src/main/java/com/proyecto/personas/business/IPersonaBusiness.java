package com.proyecto.personas.business;

import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;

import com.proyecto.personas.model.Persona;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPersonaBusiness {
	public Mono<ResponseEntity<Persona>> insert (Persona persona, final ServerHttpRequest req);
	public Mono<ResponseEntity<Persona>> edit (Persona persona, String id);
	public Mono<ResponseEntity<Flux<Persona>>> list();
	public Mono<ResponseEntity<Persona>> listById(String id);
	public Mono<ResponseEntity<Void>> delete (String id);
}
