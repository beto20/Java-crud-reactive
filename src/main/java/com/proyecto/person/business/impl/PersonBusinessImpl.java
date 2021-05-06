package com.proyecto.person.business.impl;

import java.net.URI;

<<<<<<< HEAD

=======
>>>>>>> 0960e85427dbd31ad804b96fcafc0765f981fdb4
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

<<<<<<< HEAD

import com.proyecto.person.business.IPersonBusiness;
import com.proyecto.person.exception.BadRequestException;
import com.proyecto.person.exception.NoContentException;
import com.proyecto.person.exception.NotFoundException;
import com.proyecto.person.model.Person;
import com.proyecto.person.repo.IPersonRepo;


=======
import com.proyecto.person.business.IPersonBusiness;
import com.proyecto.person.model.Person;
import com.proyecto.person.repo.IPersonRepo;

>>>>>>> 0960e85427dbd31ad804b96fcafc0765f981fdb4
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonBusinessImpl implements IPersonBusiness{
	
	@Autowired
	private IPersonRepo repo;

	@Override
	public Mono<ResponseEntity<Person>> insert(Person person, final ServerHttpRequest req) {
<<<<<<< HEAD
		Mono<Person> MoPerson = repo.insert(person);
		return MoPerson.map(p -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(p.getId())))
				.contentType(MediaType.APPLICATION_JSON)
				.body(p))
				//.error(new BadRequestException(String.format("Conflict with server, 409", person)));
				.switchIfEmpty(Mono.error(new BadRequestException(String.format("Conflict with server, 409", person))));
	}
	
	@Override
	public Mono<ResponseEntity<Person>> edit(Person person, String id) {
		Mono<Person> MoPersonReq = Mono.just(person);
		Mono<Person> MoPersonDB = repo.findById(id);
		
		return MoPersonDB
				.zipWith(MoPersonReq,(db, p) -> {
=======
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
>>>>>>> 0960e85427dbd31ad804b96fcafc0765f981fdb4
					db.setId(id);
					db.setName(p.getName());
					db.setLastname(p.getLastname());
					db.setAddress(p.getAddress());
					db.setDni(p.getDni());
					db.setPhoto(p.getPhoto());
					db.setState(p.getState());

					return db;
				})
<<<<<<< HEAD
				.flatMap(p -> repo.save(p))
				.map(pe -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(pe))
				.switchIfEmpty(Mono.error(new NotFoundException(String.format("Not found, check your spelling, 404", id))));
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
				.switchIfEmpty(Mono.error(new NotFoundException(String.format("Not found, check your spelling, 404", id))));
	}


	@Override
	public Mono<ResponseEntity<Void>> delete(String id) {
		Mono<Person> MoPerson = repo.findById(id);
		return MoPerson.flatMap(p -> {
			return repo.deleteById(p.getId())
					.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
		});
=======
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
>>>>>>> 0960e85427dbd31ad804b96fcafc0765f981fdb4
	}
	
}
