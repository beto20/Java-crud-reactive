package com.proyecto.person.business.impl;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import com.proyecto.person.business.IPersonBusiness;
import com.proyecto.person.model.Person;
import com.proyecto.person.repo.IPersonRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonBusinessImpl implements IPersonBusiness{
	
	@Autowired
	private IPersonRepo repo;

	@Override
	public Mono<ResponseEntity<Person>> insert(Person person, final ServerHttpRequest req) {
		Mono<Person> Moperson = repo.insert(person);
		return Moperson.map(p -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(p.getId())))
				.contentType(MediaType.APPLICATION_JSON)
				.body(p));
	}

	@Override
	public Mono<ResponseEntity<Person>> edit(Person person, String id) {
		Mono<Person> MopersonReq = Mono.just(person);
		Mono<Person> MopersonDB = repo.findById(id);
		
		return MopersonDB
				.zipWith(MopersonReq,(db, p) -> {
					db.setId(id);
					db.setName(p.getName());
					db.setLastname(p.getLastname());
					db.setAddress(p.getAddress());
					db.setDni(p.getDni());
					db.setPhoto(p.getPhoto());
					db.setState(p.getState());

					return db;
				})
				.flatMap(p -> repo.save(p))//p -> repo.save(p)
				.map(pe -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(pe))
				.defaultIfEmpty(new ResponseEntity<Person>(HttpStatus.NOT_FOUND));
	}
	
	@Override
	public Mono<ResponseEntity<Flux<Person>>> list() {
		Flux<Person> Fxperson = repo.findAll();
		return Mono.just(ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(Fxperson));
	}
	
	@Override
	public Mono<ResponseEntity<Person>> listById(String id) {
		Mono<Person> Moperson = repo.findById(id);
		return Moperson.map(p -> ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@Override
	public Mono<ResponseEntity<Void>> delete(String id) {
		Mono<Person> Moperson = repo.findById(id);
		return Moperson.flatMap(p -> {
			return repo.deleteById(p.getId())
					.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
		})
				.defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}
	
}
