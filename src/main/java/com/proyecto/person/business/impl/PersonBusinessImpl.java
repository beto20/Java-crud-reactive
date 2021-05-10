package com.proyecto.person.business.impl;

import java.net.URI;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import com.proyecto.person.business.PersonBusiness;
import com.proyecto.person.dto.PersonDTO;
import com.proyecto.person.exception.NotFoundException;
import com.proyecto.person.model.Person;
import com.proyecto.person.repo.PersonRepo;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class PersonBusinessImpl implements PersonBusiness {

	private PersonRepo repo;

	@Override
	public Mono<ResponseEntity<Person>> insert(PersonDTO persondto, final ServerHttpRequest req) {
		Person personModel = new Person();
		personModel.setName(persondto.getName());
		personModel.setLastname(persondto.getLastname());
		personModel.setDni(persondto.getDni());
		personModel.setAddress(persondto.getAddress());
		personModel.setPhoto(persondto.getPhoto());
		personModel.setState(persondto.getState());
		Mono<Person> MoPerson = repo.insert(personModel);
		return MoPerson.map(p -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(p.getId())))
				.contentType(MediaType.APPLICATION_JSON)
				.body(p))
				.onErrorMap(error -> new NotFoundException(String.format("error", persondto)));
				//.onErrorReturn(Mono.error(new NotFoundException(String.format("error"))))
				//.switchIfEmpty(Mono.error(new NotFoundException(String.format("error", persondto))));
				
	}


	@Override
	public Mono<ResponseEntity<Person>> edit(PersonDTO persondto, String id) {

		Mono<PersonDTO> MoPersonReq = Mono.just(persondto);
		Mono<Person> MoPersonDB = repo.findById(id);
		return MoPersonDB
				.zipWith(MoPersonReq,(db, p) -> {
					db.setId(id);
					db.setName(p.getName());
					db.setLastname(p.getLastname());
					db.setAddress(p.getAddress());
					db.setDni(p.getDni());
					db.setPhoto(p.getPhoto());
					db.setState(p.getState());

					return db;
				})
				.flatMap(p -> repo.save(p))
				.map(pe -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(pe))
				//.onErrorMap(error -> new NotFoundException(String.format("ERRORsdasd")).printStackTrace());
			.defaultIfEmpty(new ResponseEntity<Person>(HttpStatus.NOT_FOUND));
			//.switchIfEmpty(Mono.error(new NotFoundException(String.format("Not found, check your spelling, 404", id))));
	}
	
	
	@Override
	public Mono<ResponseEntity<Flux<Person>>> list() {
		Flux<Person> FxPerson = repo.findAll();
		return Mono.just(ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(FxPerson));	
	}
	

	@Override
	public Mono<ResponseEntity<Person>> listById(String id) {
		Mono<Person> MoPerson = repo.findById(id);
		return MoPerson.map(p -> ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(p))
				//.doOnError(error -> new NotFoundException(String.format("ERROR")).printStackTrace());
				//.onErrorMap(error -> new NotFoundException(String.format("ERROR")));
				.switchIfEmpty(Mono.error(new NotFoundException(String.format("error", id))));
	}

	@Override
	public Mono<ResponseEntity<Void>> delete(String id) {
		Mono<Person> MoPerson = repo.findById(id);
		return MoPerson.flatMap(p -> repo.deleteById(p.getId())
				.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
				.switchIfEmpty(Mono.error(new NotFoundException(String.format("error"))));
	}

}
