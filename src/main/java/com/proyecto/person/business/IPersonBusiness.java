package com.proyecto.person.business;

import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;

import com.proyecto.person.model.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPersonBusiness {
	public Mono<ResponseEntity<Person>> insert (Person person, final ServerHttpRequest req);
	public Mono<ResponseEntity<Person>> edit (Person person, String id);
	public Mono<ResponseEntity<Flux<Person>>> list();
	public Mono<ResponseEntity<Person>> listById(String id);
	public Mono<ResponseEntity<Void>> delete (String id);
}
