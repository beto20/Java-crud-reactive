package com.proyecto.personas.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.proyecto.personas.model.Persona;

public interface IPersonaRepo extends ReactiveMongoRepository<Persona, String>{

	
	
	
}
