package com.proyecto.person.business;

import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;

import com.proyecto.person.dto.PersonDTO;
import com.proyecto.person.model.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonBusiness {
	Mono<ResponseEntity<Person>> insert (PersonDTO persondto, final ServerHttpRequest req);
	Mono<ResponseEntity<Person>> edit (PersonDTO persondto, String id);
	Mono<ResponseEntity<Flux<Person>>> list();
	Mono<ResponseEntity<Person>> listById(String id);
	Mono<ResponseEntity<Void>> delete (String id);
}
