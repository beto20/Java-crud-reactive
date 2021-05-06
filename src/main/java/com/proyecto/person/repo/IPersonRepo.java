package com.proyecto.person.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.proyecto.person.model.Person;

public interface IPersonRepo extends ReactiveMongoRepository<Person, String>{

	
	
	
}
