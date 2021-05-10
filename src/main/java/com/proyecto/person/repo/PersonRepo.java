package com.proyecto.person.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.proyecto.person.model.Person;

public interface PersonRepo extends ReactiveMongoRepository<Person, String>{

	
	
	
}
